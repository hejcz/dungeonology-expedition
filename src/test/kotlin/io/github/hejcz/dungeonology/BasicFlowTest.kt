package io.github.hejcz.dungeonology

import io.github.hejcz.dungeonology.game.*
import io.github.hejcz.dungeonology.game.action.Finish
import io.github.hejcz.dungeonology.game.action.MoveDirection
import io.github.hejcz.dungeonology.game.action.MoveScholar
import io.github.hejcz.dungeonology.game.action.Start
import io.github.hejcz.dungeonology.game.card.BackStab
import io.github.hejcz.dungeonology.game.card.Card4
import io.github.hejcz.dungeonology.game.card.CardId
import io.github.hejcz.dungeonology.game.card.NoCardInProgress
import io.github.hejcz.dungeonology.game.zone.Cube
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TestRandomizer() : Randomizer {
    override fun cube(): Cube = Cube.GREEN
    override fun scholarColor(): Color = Color.BLUE
}

class BasicFlowTest {

    @Test
    fun `initial number of cards 1`() {
        val game = Game(
            players = listOf(
                Player(PlayerId(1), Rebeca, Color.BLUE),
                Player(PlayerId(2), Vincenzo, Color.YELLOW)
            ),
            events = emptyMap(),
            cardInProgress = NoCardInProgress,
            studyInProgress = NoStudyInProgress,
            randomizer = TestRandomizer(),
            deck = Deck.from(listOf(Card4, BackStab, Card4))
        )
        game.apply(PlayerId(1), Start)
            .apply { hasNewHandContaining(PlayerId(1), 1) }
            .apply { hasNewHandContaining(PlayerId(2), 3, 1) }
    }

    @Test
    fun `initial number of cards 2`() {
        val game = Game(
            players = listOf(
                Player(PlayerId(1), Rebeca, Color.YELLOW),
                Player(PlayerId(2), Vincenzo, Color.BLUE)
            ),
            events = emptyMap(),
            cardInProgress = NoCardInProgress,
            studyInProgress = NoStudyInProgress,
            randomizer = TestRandomizer(),
            deck = Deck.from(listOf(BackStab, Card4, Card4))
        )
        game.apply(PlayerId(1), Start)
            .apply { hasNewHandContaining(PlayerId(2), 3) }
            .apply { hasNewHandContaining(PlayerId(1), 1, 1) }
    }

    @Test
    fun `cards should be drawn to full hand on the end of turn`() {
        val game = Game(
            players = listOf(
                Player(PlayerId(1), Rebeca, Color.BLUE),
                Player(PlayerId(2), Vincenzo, Color.YELLOW)
            ),
            events = emptyMap(),
            cardInProgress = NoCardInProgress,
            studyInProgress = NoStudyInProgress,
            randomizer = TestRandomizer(),
            deck = Deck.from(listOf(BackStab, Card4, Card4, BackStab, Card4, Card4, BackStab, Card4))
        )
        game.apply(PlayerId(1), Start)
            .apply(PlayerId(1), Finish)
            .apply { hasNewHandContaining(PlayerId(1), 3, 3, 1, 1) }
            .apply(PlayerId(2), Finish)
            .apply { hasNewHandContaining(PlayerId(2), 1, 1, 3, 1) }
    }

}

private fun Game.hasNewHandContaining(playerId: PlayerId, vararg ids: Int) {
    Assertions.assertThat(this.events.getValue(playerId))
        .contains(NewHand(ids.toList().map { CardId(it) }))
}
