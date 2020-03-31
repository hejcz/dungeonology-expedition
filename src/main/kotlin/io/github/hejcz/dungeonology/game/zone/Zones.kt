package io.github.hejcz.dungeonology.game.zone

import io.github.hejcz.dungeonology.game.Floor

interface Zones {
    fun draw(floor: Floor): Pair<Zone?, Zones>
}
