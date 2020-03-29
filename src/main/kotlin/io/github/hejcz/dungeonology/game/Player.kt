package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.card.Card

data class Player(
    val id: PlayerId,
    val scholar: Scholar,
    val color: Color,
    val cards: List<Card> = emptyList(),
    private val cubes: Cubes = Cubes(),
    private val interns: Set<Intern> = emptySet(),
    private val jinxes: Set<Jinx> = emptySet(),
    private val students: Int = scholar.students(),
    private val stunTokens: Int = 0,
    private val hasKnowledgeToken: Boolean = false,
    private val usedMainAction: Boolean = false
) {
    fun cardsIds() = cards.map { it.id }
}

inline class PlayerId(val id: Int)