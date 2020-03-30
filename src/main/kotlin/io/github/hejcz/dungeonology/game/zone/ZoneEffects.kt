package io.github.hejcz.dungeonology.game.zone

data class ZoneEffects(val effects: List<ZoneEffect>) {
    constructor(vararg effects: ZoneEffect) : this(effects.toList())
}

interface ZoneEffect {
    fun resolveOnEnter(): Boolean = false
}

data class Danger(val n: Int) : ZoneEffect

data class GoodLuck(val n: Int) : ZoneEffect

data class BadLuck(val n: Int) : ZoneEffect

object Stairs : ZoneEffect

object Boss : ZoneEffect

data class SecretPassage(val sign: PassageSign) : ZoneEffect

enum class PassageSign {
    A, B, X
}

data class Bivouac(val requiredCube: Cube) : ZoneEffect

data class Ambush(val n: Int) : ZoneEffect {
    override fun resolveOnEnter(): Boolean = true
}