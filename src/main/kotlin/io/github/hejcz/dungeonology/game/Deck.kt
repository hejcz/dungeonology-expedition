package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.card.Card

interface Deck {
    fun draw(n: Int): Pair<List<Card>, Deck>

    companion object {
        fun fromShuffled(cards: List<Card>): Deck = SimpleDeck(cards.shuffled())
        fun from(cards: List<Card>): Deck = SimpleDeck(cards)
    }
}

private data class SimpleDeck(private val cards: List<Card>) : Deck {
    override fun draw(n: Int): Pair<List<Card>, Deck> = cards.take(n) to SimpleDeck(cards.drop(n))
}
