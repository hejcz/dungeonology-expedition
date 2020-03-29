package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.zone.Cube

interface Randomizer {
    fun cube(): Cube
    fun scholarColor(): Color
}
