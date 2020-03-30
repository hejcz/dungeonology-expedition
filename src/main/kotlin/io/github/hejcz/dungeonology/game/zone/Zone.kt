package io.github.hejcz.dungeonology.game.zone

import io.github.hejcz.dungeonology.game.Cubes
import io.github.hejcz.dungeonology.game.Floor

interface Zone {
    val id: ZoneId
    val alarm: Int
    val floor: Floor
    val cubes: Cubes
    val zoneEffects: ZoneEffects
    val exits: Set<Exit>
}

data class StartingZone(
    override val id: ZoneId = ZoneId(1),
    override val alarm: Int = 0,
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(),
    override val zoneEffects: ZoneEffects = ZoneEffects(),
    override val exits: Set<Exit> = Exit.values().toSet()
) : Zone

data class HunterBivouac(
    override val id: ZoneId = ZoneId(11),
    override val alarm: Int = 2,
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(Cube.GREEN, Cube.GREEN, Cube.RED),
    override val zoneEffects: ZoneEffects = ZoneEffects(Stairs, Boss),
    override val exits: Set<Exit> =
        setOf(Exit.BOT_LEFT, Exit.BOT_RIGHT, Exit.LEFT_RIGHT, Exit.RIGHT_RIGHT, Exit.RIGHT_LEFT)
) : Zone

data class GrandBacchanal(
    override val id: ZoneId = ZoneId(12),
    override val alarm: Int = 2,
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(Cube.GREEN, Cube.GREEN, Cube.GREEN),
    override val zoneEffects: ZoneEffects = ZoneEffects(Stairs, SecretPassage(PassageSign.B), Boss),
    override val exits: Set<Exit> =
        setOf(Exit.BOT_LEFT, Exit.BOT_RIGHT, Exit.RIGHT_LEFT, Exit.LEFT_LEFT)
) : Zone

data class ShepherdsSanctuary(
    override val id: ZoneId = ZoneId(13),
    override val alarm: Int = 3,
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(Cube.GREEN, Cube.BLUE),
    override val zoneEffects: ZoneEffects = ZoneEffects(Danger(2), GoodLuck(1)),
    override val exits: Set<Exit> = setOf(Exit.BOT_LEFT, Exit.RIGHT_LEFT, Exit.TOP_LEFT, Exit.LEFT_LEFT)
) : Zone

data class OldOnesRepose(
    override val id: ZoneId = ZoneId(14),
    override val alarm: Int = 3,
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(Cube.GREEN, Cube.RED, Cube.BLUE),
    override val zoneEffects: ZoneEffects = ZoneEffects(),
    override val exits: Set<Exit> = setOf(Exit.BOT_RIGHT, Exit.LEFT_LEFT, Exit.RIGHT_LEFT, Exit.TOP_RIGHT)
) : Zone

data class WorkersConvivium(
    override val id: ZoneId = ZoneId(15),
    override val alarm: Int = 4,
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(Cube.GREEN, Cube.RED),
    override val zoneEffects: ZoneEffects = ZoneEffects(SecretPassage(PassageSign.A), Bivouac(Cube.GREEN), BadLuck(1)),
    override val exits: Set<Exit> = setOf(Exit.BOT_LEFT, Exit.RIGHT_LEFT, Exit.TOP_LEFT, Exit.LEFT_LEFT)
) : Zone

data class DancingBladesArena(
    override val id: ZoneId = ZoneId(16),
    override val alarm: Int = 2,
    override val floor: Floor = Floor.FIRST,
    override val cubes: Cubes = Cubes(Cube.RED, Cube.RED),
    override val zoneEffects: ZoneEffects = ZoneEffects(Ambush(1)),
    override val exits: Set<Exit> =
        setOf(Exit.BOT_LEFT, Exit.RIGHT_LEFT, Exit.TOP_LEFT, Exit.LEFT_LEFT, Exit.LEFT_RIGHT, Exit.RIGHT_RIGHT)
) : Zone
