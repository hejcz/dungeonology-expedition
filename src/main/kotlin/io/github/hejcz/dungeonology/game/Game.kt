package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.action.*
import io.github.hejcz.dungeonology.game.card.CardInProgress
import io.github.hejcz.dungeonology.game.card.NoCardInProgress
import io.github.hejcz.dungeonology.game.zone.*
import java.lang.RuntimeException
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
    val board: Map<Point, Zone> = mapOf(Point(0, 0) to StartingZone())
) {

    fun apply(playerId: PlayerId, action: Action): Game {
        validate(action)?.let {
            return copy(events = mapOf(playerId to setOf(ErrorEvent(it))))
        }
        return when (action) {
            Start -> start()
            Finish -> finish(playerId)
            is MoveScholar -> move(playerId)
            is PlaceZone -> placeZone(playerId, action)
            is SubmitThesis -> this
            is Rest -> this
            is PlayCard -> this
        }
    }

    private fun placeZone(playerId: PlayerId, action: PlaceZone): Game {
        val newBoard = board + (action.p to currentZone)
        return copy(currentZone = NoZone, board = newBoard, events = players.map { it.id to
                setOf(PlayerUpdated(playerId, action.p), BoardUpdated(newBoard)) }.toMap())
    }

    private fun move(playerId: PlayerId): Game {
        val (zone, newZones) = zones.draw(Floor.FIRST)
        return copy(zones = newZones, currentZone = zone, events = mapOf(playerId to setOf(NewZone(zone.id))))
    }

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
            .fold(ReducedPlayers(deck, emptyList())) { acc, (pIdx, n) ->  dealNCards(acc, n, pIdx) }

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

}