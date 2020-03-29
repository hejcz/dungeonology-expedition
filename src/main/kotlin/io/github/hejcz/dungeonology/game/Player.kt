package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.card.Cards

data class Player(
    private val scholar: Scholar,
    private val cards: Cards,
    private val cubes: Cubes,
    private val interns: Set<Intern>,
    private val jinxes: Set<Jinx>,
    private val students: Int,
    private val stunTokens: Int,
    private val hasKnowledgeToken: Boolean,
    private val usedMainAction: Boolean
)

inline class PlayerId(val id: Int)