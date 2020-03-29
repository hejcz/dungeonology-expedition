package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.action.*
import io.github.hejcz.dungeonology.game.card.CardInProgress
import io.github.hejcz.dungeonology.game.zone.Cube
import java.lang.RuntimeException

data class Game(
    val players: List<Player>,
    val events: Map<PlayerId, Set<Event>>,
    val cardInProgress: CardInProgress,
    val studyInProgress: StudyInProgress,
    val randomizer: Randomizer,
    val studentsOnBonfire: Int = 0,
    val deck: Deck
) {

    fun apply(playerId: PlayerId, action: Action): Game {
        validate(action)?.let {
            return copy(events = mapOf(playerId to setOf(ErrorEvent(it))))
        }
        return when (action) {
            is SubmitThesis -> this
            is Rest -> this
            is MoveScholar -> this
            is PlayCard -> this
            Finish -> processFinish(playerId)
            Start -> processStart()
        }
    }

    private fun validate(action: Action): ErrorCode? {
        return null
    }

    private fun processFinish(playerId: PlayerId): Game {
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

    private fun processStart(): Game {
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
            .zip(generateSequence(1, Int::inc))
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