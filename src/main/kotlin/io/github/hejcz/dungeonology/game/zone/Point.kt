package io.github.hejcz.dungeonology.game.zone

data class Point(val x: Int, val y: Int) {
    fun surrounding() = setOf(
        Point(x, y + 1),
        Point(x, y - 1),
        Point(x + 1, y + 1),
        Point(x + 1, y - 1),
        Point(x - 1, y + 1),
        Point(x - 1, y - 1),
        Point(x + 1, y),
        Point(x - 1, y)
    )
}