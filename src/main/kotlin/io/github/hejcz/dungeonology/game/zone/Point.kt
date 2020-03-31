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

    fun furtherSurrounding() = setOf(
        Point(x, y + 2),
        Point(x, y - 2),
        Point(x + 2, y),
        Point(x - 2, y),
        Point(x + 1, y + 2),
        Point(x - 1, y + 2),
        Point(x + 1, y - 2),
        Point(x - 1, y - 2),
        Point(x + 2, y + 1),
        Point(x - 2, y + 1),
        Point(x + 2, y - 1),
        Point(x - 2, y - 1)
    )
}