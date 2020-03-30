package io.github.hejcz.dungeonology.game.zone

import io.github.hejcz.dungeonology.game.Cubes
import io.github.hejcz.dungeonology.game.Floor

object NoZone : Zone {
    override val id: ZoneId
        get() = throw RuntimeException("No zone accessed")
    override val alarm: Int
        get() = throw RuntimeException("No zone accessed")
    override val floor: Floor
        get() = throw RuntimeException("No zone accessed")
    override val cubes: Cubes
        get() = throw RuntimeException("No zone accessed")
    override val zoneEffects: ZoneEffects
        get() = throw RuntimeException("No zone accessed")
    override val exits: Set<Exit>
        get() = throw RuntimeException("No zone accessed")
}
