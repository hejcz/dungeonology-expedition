package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.action.Action
import io.github.hejcz.dungeonology.game.card.CardInProgress
import io.github.hejcz.dungeonology.game.zone.Cube

data class Game(
    val players: Map<PlayerId, Player>,
    val events: Map<PlayerId, Set<Event>>,
    val cardInProgress: CardInProgress
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

    fun drawStudentsFromUniversity(n: Int): Game = this

    fun increaseAlertBy(n: Int): Game = this

    fun increaseStealthBy(n: Int): Game = this

    fun performDivination(n: Int): Game = this

    fun killStudents(n: Int) = this

    fun isCubeInZone(): Boolean = false

    fun otherPlayersWithSomeCubes(): List<Player> = emptyList()

    fun addCube(cube: Cube): Game = this

    fun takeStunToken(n: Int): Game = this

    fun stealCubeFrom(target: PlayerId, cube: Cube): Game = this

}