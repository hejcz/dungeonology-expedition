package io.github.hejcz.dungeonology

import io.github.hejcz.dungeonology.game.Color
import io.github.hejcz.dungeonology.game.Randomizer
import io.github.hejcz.dungeonology.game.zone.Cube

class TestRandomizer : Randomizer {
    override fun cube(): Cube =
        Cube.GREEN
    override fun scholarColor(): Color =
        Color.BLUE
}