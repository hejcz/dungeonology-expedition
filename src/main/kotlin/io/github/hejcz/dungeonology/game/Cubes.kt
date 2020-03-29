package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.zone.Cube

data class Cubes(val initial: List<Cube>, val left: List<Cube>) {
    constructor(vararg initial: Cube) : this(initial.toList(), initial.toList())
}
