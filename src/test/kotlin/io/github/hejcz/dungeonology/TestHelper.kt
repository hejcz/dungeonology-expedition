package io.github.hejcz.dungeonology

import io.github.hejcz.dungeonology.game.*

fun twoPlayers(): List<Player> {
    return listOf(
        Player(PlayerId(1), Rebeca, Color.BLUE),
        Player(PlayerId(2), Vincenzo, Color.YELLOW)
    )
}