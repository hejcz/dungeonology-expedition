package io.github.hejcz.dungeonology.game.zone

data class ZoneEffects(val effects: List<ZoneEffect>) {
    constructor(vararg effects: ZoneEffect) : this(effects.toList())
}

interface ZoneEffect {
    fun resolveOnEnter(): Boolean = false
}

data class Danger(val n: Int) : ZoneEffect

data class GoodLuck(val n: Int) : ZoneEffect

data class Ambush(val n: Int) : ZoneEffect {
    override fun resolveOnEnter(): Boolean = true
}