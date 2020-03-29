package io.github.hejcz.dungeonology.game.card

import java.lang.RuntimeException

class Power(power: Int) {
    init {
        if (power < 1 || power > 5) {
            throw RuntimeException("invalid power")
        }
    }
}