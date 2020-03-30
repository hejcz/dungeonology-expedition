package io.github.hejcz.dungeonology.game.zone

object ZonePlacementValidator {

    fun isValid(zone: Zone, point: Point, presentZones: Map<Point, Zone>): Boolean {
        return point.surrounding().none { it in presentZones }
                && zone.exits.any { passageExists(it, point, presentZones) }
    }

    private fun passageExists(exit: Exit, p: Point, presentZones: Map<Point, Zone>): Boolean = when (exit) {
        Exit.TOP_LEFT -> presentZones[Point(p.x, p.y + 2)]?.exits?.contains(Exit.BOT_RIGHT) ?: false
                || presentZones[Point(p.x - 1, p.y + 2)]?.exits?.contains(Exit.BOT_LEFT) ?: false
        Exit.TOP_RIGHT -> presentZones[Point(p.x, p.y + 2)]?.exits?.contains(Exit.BOT_LEFT) ?: false
                || presentZones[Point(p.x + 1, p.y + 2)]?.exits?.contains(Exit.BOT_RIGHT) ?: false
        Exit.BOT_LEFT -> presentZones[Point(p.x, p.y - 2)]?.exits?.contains(Exit.TOP_RIGHT) ?: false
                || presentZones[Point(p.x + 1, p.y - 2)]?.exits?.contains(Exit.TOP_LEFT) ?: false
        Exit.BOT_RIGHT -> presentZones[Point(p.x, p.y - 2)]?.exits?.contains(Exit.TOP_LEFT) ?: false
                || presentZones[Point(p.x - 1, p.y - 2)]?.exits?.contains(Exit.TOP_RIGHT) ?: false
        Exit.LEFT_LEFT -> presentZones[Point(p.x - 2, p.y)]?.exits?.contains(Exit.RIGHT_RIGHT) ?: false
                || presentZones[Point(p.x - 2, p.y - 1)]?.exits?.contains(Exit.RIGHT_LEFT) ?: false
        Exit.LEFT_RIGHT -> presentZones[Point(p.x - 2, p.y)]?.exits?.contains(Exit.RIGHT_LEFT) ?: false
                || presentZones[Point(p.x - 2, p.y + 1)]?.exits?.contains(Exit.RIGHT_RIGHT) ?: false
        Exit.RIGHT_LEFT -> presentZones[Point(p.x + 2, p.y)]?.exits?.contains(Exit.LEFT_RIGHT) ?: false
                || presentZones[Point(p.x + 2, p.y + 1)]?.exits?.contains(Exit.LEFT_LEFT) ?: false
        Exit.RIGHT_RIGHT -> presentZones[Point(p.x + 2, p.y)]?.exits?.contains(Exit.LEFT_LEFT) ?: false
                || presentZones[Point(p.x + 2, p.y - 1)]?.exits?.contains(Exit.LEFT_RIGHT) ?: false
    }

}