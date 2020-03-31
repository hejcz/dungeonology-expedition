package io.github.hejcz.dungeonology

import io.github.hejcz.dungeonology.game.Floor
import io.github.hejcz.dungeonology.game.zone.Zone
import io.github.hejcz.dungeonology.game.zone.Zones

class TestZones(private val zones: Map<Floor, List<Zone>> = emptyMap()) : Zones {
    override fun draw(floor: Floor): Pair<Zone?, Zones> {
        val zonesFromFloor = zones[floor]
        return when {
            zonesFromFloor == null || zonesFromFloor.isEmpty() -> null to this
            else -> zonesFromFloor.first() to
                    TestZones(zones.mapValues { if (it.key == floor) it.value.drop(1) else it.value })
        }
    }

    override fun count(floor: Floor): Int = zones[floor]?.size ?: 0

    override fun append(floor: Floor, zonesToAppend: List<Zone>): Zones =
        TestZones(zones.mapValues { if (it.key == floor) it.value + zonesToAppend else it.value })
}