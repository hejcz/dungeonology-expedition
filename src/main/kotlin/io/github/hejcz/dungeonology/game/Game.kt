package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.action.Action

data class Game(
    private val players: Map<PlayerId, Player>,
    private val events: Map<PlayerId, Set<Event>>
) {

    fun apply(playerId: PlayerId, action: Action): Game {
        validate(action)?.let {
            return copy(events = mapOf(playerId to setOf(ErrorEvent(it))))
        }
        return this
    }

    private fun validate(action: Action): ErrorCode? {
        return null
    }

}