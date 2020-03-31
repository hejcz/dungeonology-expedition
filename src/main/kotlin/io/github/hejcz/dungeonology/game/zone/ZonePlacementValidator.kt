package io.github.hejcz.dungeonology.game.zone

object ZonePlacementValidator {

    fun isValid(scholarPoint: Point, newZone: Zone, newZonePoint: Point, presentZones: Map<Point, Zone>): Boolean {
        return newZonePoint !in presentZones // overlapping
                && newZonePoint in scholarPoint.furtherSurrounding() // out of scholar reach
                && newZonePoint.surrounding().none { it in presentZones } // overlapping
                && haveMatchingExit(scholarPoint, newZonePoint, presentZones.getValue(scholarPoint), newZone)
    }

    private fun haveMatchingExit(scholarPoint: Point, newZonePoint: Point, scholarZone: Zone, newZone: Zone): Boolean {
        return if (scholarPoint.x == newZonePoint.x && scholarPoint.y == newZonePoint.y + 2) {
            (Exit.TOP_RIGHT in newZone.exits && Exit.BOT_LEFT in scholarZone.exits
                    || Exit.TOP_LEFT in newZone.exits && Exit.BOT_RIGHT in scholarZone.exits)
        } else if (scholarPoint.x == newZonePoint.x && scholarPoint.y == newZonePoint.y - 2) {
            (Exit.BOT_LEFT in newZone.exits && Exit.TOP_RIGHT in scholarZone.exits
                    || Exit.BOT_RIGHT in newZone.exits && Exit.TOP_LEFT in scholarZone.exits)
        } else if (scholarPoint.y == newZonePoint.y && scholarPoint.x == newZonePoint.x + 2) {
            Exit.RIGHT_LEFT in newZone.exits && Exit.LEFT_RIGHT in scholarZone.exits
                    || Exit.RIGHT_RIGHT in newZone.exits && Exit.LEFT_LEFT in scholarZone.exits
        } else if (scholarPoint.y == newZonePoint.y && scholarPoint.x == newZonePoint.x - 2) {
            Exit.LEFT_RIGHT in newZone.exits && Exit.RIGHT_LEFT in scholarZone.exits
                    || Exit.LEFT_LEFT in newZone.exits && Exit.RIGHT_RIGHT in scholarZone.exits
        } else if (newZonePoint.x + 1 == scholarPoint.x && newZonePoint.y + 2 == scholarPoint.y) {
            Exit.TOP_RIGHT in newZone.exits && Exit.BOT_RIGHT in scholarZone.exits
        } else if (newZonePoint.x + 2 == scholarPoint.x && newZonePoint.y + 1 == scholarPoint.y) {
            Exit.RIGHT_LEFT in newZone.exits && Exit.LEFT_LEFT in scholarZone.exits
        } else if (newZonePoint.x + 2 == scholarPoint.x && newZonePoint.y - 1 == scholarPoint.y) {
            Exit.RIGHT_RIGHT in newZone.exits && Exit.LEFT_RIGHT in scholarZone.exits
        } else if (newZonePoint.x + 1 == scholarPoint.x && newZonePoint.y - 2 == scholarPoint.y) {
            Exit.BOT_LEFT in newZone.exits && Exit.TOP_LEFT in scholarZone.exits
        } else if (newZonePoint.x - 1 == scholarPoint.x && newZonePoint.y + 2 == scholarPoint.y) {
            Exit.TOP_LEFT in newZone.exits && Exit.BOT_LEFT in scholarZone.exits
        } else if (newZonePoint.x - 2 == scholarPoint.x && newZonePoint.y + 1 == scholarPoint.y) {
            Exit.LEFT_RIGHT in newZone.exits && Exit.RIGHT_RIGHT in scholarZone.exits
        } else if (newZonePoint.x - 2 == scholarPoint.x && newZonePoint.y - 1 == scholarPoint.y) {
            Exit.LEFT_LEFT in newZone.exits && Exit.RIGHT_LEFT in scholarZone.exits
        } else if (newZonePoint.x - 1 == scholarPoint.x && newZonePoint.y - 2 == scholarPoint.y) {
            Exit.BOT_RIGHT in newZone.exits && Exit.TOP_RIGHT in scholarZone.exits
        } else {
            false
        }
    }

}