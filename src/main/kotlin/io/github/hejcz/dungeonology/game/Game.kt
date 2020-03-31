package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.action.*
import io.github.hejcz.dungeonology.game.card.CardInProgress
import io.github.hejcz.dungeonology.game.card.NoCardInProgress
import io.github.hejcz.dungeonology.game.zone.*
import kotlin.math.min

data class Game(
    val players: List<Player>,
    val events: Map<PlayerId, Set<Event>> = emptyMap(),
    val cardInProgress: CardInProgress = NoCardInProgress,
    val studyInProgress: StudyInProgress = NoStudyInProgress,
    val randomizer: Randomizer,
    val studentsOnBonfire: Int = 0,
    val deck: Deck,
    val zones: Zones,
    val currentZone: Zone = NoZone,
    val board: Map<Point, Zone> = mapOf(Point(0, 0) to StartingZone()),
    val playersPositions: Map<PlayerId, Point> = players.map { it.id to Point(0, 0) }.toMap()
) {

    fun apply(playerId: PlayerId, action: Action): Game {
        validate(action)?.let {
            return copy(events = mapOf(playerId to setOf(ErrorEvent(it))))
        }
        return when (action) {
            Start -> start()
            Finish -> finish(playerId)
            is DiscoverZone -> discoverZone(playerId)
            is PlaceZone -> placeZone(playerId, action)
            is SubmitThesis -> this
            is Rest -> this
            is PlayCard -> this
            is MoveScholar -> move(playerId, action)
        }
    }

    private fun placeZone(playerId: PlayerId, action: PlaceZone): Game {
        val newBoard = board + (action.p to currentZone)
        return copy(
            currentZone = NoZone, board = newBoard,
            events = players.map { it.id to setOf(PlayerUpdated(playerId, action.p), BoardUpdated(newBoard)) }.toMap(),
            playersPositions = playersPositions - playerId + (playerId to action.p)
        )
    }

    private fun discoverZone(playerId: PlayerId): Game {
        val (zone, newZones) = zones.draw(Floor.FIRST)
        return when {
            zone == null ->
                copy(events = mapOf(playerId to setOf(NoZonesLeft)))
            drawnZoneCantBePlaced(playerId, zone) -> when {
                newZones.count(Floor.FIRST) == 0 -> copy(events = mapOf(playerId to setOf(CantDiscoverNewZone(zone))))
                else -> tryToRewindZones(playerId, newZones, zone)
            }
            else -> copy(zones = newZones, currentZone = zone, events = mapOf(playerId to setOf(NewZone(zone.id))))
        }
    }

    private fun tryToRewindZones(playerId: PlayerId, otherZones: Zones, invalidZone: Zone): Game {
        val (rewindedZone, rewindedZones) = rewindZones(playerId, otherZones, invalidZone)
        return if (rewindedZone == invalidZone) {
            copy(events = mapOf(playerId to setOf(CantDiscoverNewZone(invalidZone))))
        } else {
            copy(
                zones = rewindedZones,
                currentZone = rewindedZone,
                events = mapOf(playerId to setOf(NewZone(rewindedZone.id)))
            )
        }
    }

    private fun rewindZones(playerId: PlayerId, zonesLeft: Zones, checkedZone: Zone): Pair<Zone, Zones> {
        val res = generateSequence(RewindingZones(zonesLeft, checkedZone, emptyList())) { last ->
            val (zone, newZones) = last.zones.draw(Floor.FIRST)
            RewindingZones(newZones, zone, last.poppedZones + last.current!!)
        }.first { (_, current, _) -> current == null || !drawnZoneCantBePlaced(playerId, current) }
        return if (res.current == null) {
            checkedZone to zonesLeft
        } else {
            res.current to res.zones.append(Floor.FIRST, res.poppedZones)
        }
    }

    private data class RewindingZones(val zones: Zones, val current: Zone?, val poppedZones: List<Zone>)

    private fun drawnZoneCantBePlaced(playerId: PlayerId, zone: Zone) =
        playersPositions.getValue(playerId).furtherSurrounding()
            .none { ZonePlacementValidator.isValid(playersPositions.getValue(playerId), zone, it, board) }

    private fun validate(action: Action): ErrorCode? {
        return null
    }

    private fun finish(playerId: PlayerId): Game {
        val player = getPlayer(playerId)
        val (cards, updatedDeck) = deck.draw(player.scholar.cards() - player.cards.size)
        val updatedPlayer = player.copy(cards = player.cards + cards)
        return copy(
            players = players - player + updatedPlayer,
            deck = updatedDeck,
            events = mapOf(player.id to setOf(NewHand(updatedPlayer.cardsIds())))
        )
    }

    companion object {
        data class ReducedPlayers(
            val deck: Deck,
            val players: List<Player> = emptyList()
        )
    }

    private fun start(): Game {
        val firstPlayerColor = randomizer.scholarColor()
        val firstScholarIndex = players.indexOfFirst { it.color == firstPlayerColor }
        val (updatedDeck, updatedPlayers) = dealStartingCards(firstScholarIndex)
        return copy(
            deck = updatedDeck,
            players = updatedPlayers,
            events = updatedPlayers.map { it.id to setOf(NewHand(it.cardsIds())) }.toMap()
        )
    }

    private fun dealStartingCards(firstScholarIndex: Int): ReducedPlayers =
        generateSequence(firstScholarIndex) { it.inc() % players.size }
            .take(players.size)
            .zip(generateSequence(1) { min(4, it.inc()) })
            .fold(ReducedPlayers(deck, emptyList())) { acc, (pIdx, n) -> dealNCards(acc, n, pIdx) }

    private fun dealNCards(acc: ReducedPlayers, numberOfCardsToDrawn: Int, playerIndex: Int): ReducedPlayers {
        val (cards, newDeck) = acc.deck.draw(numberOfCardsToDrawn)
        return ReducedPlayers(newDeck, acc.players + players[playerIndex].copy(cards = cards))
    }

    private fun getPlayer(playerId: PlayerId) =
        players.find { it.id == playerId } ?: throw RuntimeException("Player not found $playerId")

    fun drawStudentsFromUniversity(n: Int): Game = this

    fun increaseAlertBy(n: Int): Game = this

    fun increaseStealthBy(n: Int): Game = this

    fun performDivination(n: Int): Game = this

    fun killStudents(n: Int) = this

    fun isCubeInZone(): Boolean = false

    fun otherPlayersWithSomeCubes(): List<Player> = emptyList()

    fun addCube(cube: Cube): Game = this

    fun takeStunToken(n: Int): Game = this

    fun stealCubeFrom(from: PlayerId, cube: Cube): Game = this

    private fun move(playerId: PlayerId, action: MoveScholar) =
        copy(
            playersPositions = playersPositions - playerId + (playerId to action.p),
            events = players.map { it.id to setOf(PlayerUpdated(playerId, action.p)) }.toMap()
        )

}