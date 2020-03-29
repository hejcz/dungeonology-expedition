package io.github.hejcz.dungeonology.game.zone

import io.github.hejcz.dungeonology.game.Cubes
import io.github.hejcz.dungeonology.game.Floor

interface Zone {
    val id: ZoneId
    val floor: Floor
    val cubes: Cubes
    val zoneEffects: ZoneEffects
}

data class StartingZone(
    override val id: ZoneId = ZoneId(1),
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(),
    override val zoneEffects: ZoneEffects = ZoneEffects()
) : Zone

data class ShepherdsSanctuary(
    override val id: ZoneId = ZoneId(13),
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(Cube.GREEN, Cube.BLUE),
    override val zoneEffects: ZoneEffects = ZoneEffects(Danger(2), GoodLuck(1))
) : Zone

data class DancingBladesArena(
    override val id: ZoneId = ZoneId(16),
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(Cube.RED, Cube.RED),
    override val zoneEffects: ZoneEffects = ZoneEffects(Ambush(1))
) : Zone
