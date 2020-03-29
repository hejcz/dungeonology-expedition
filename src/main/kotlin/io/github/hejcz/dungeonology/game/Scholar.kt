package io.github.hejcz.dungeonology.game

interface Scholar {
    fun students(): Int
    fun cards(): Int
}

object Rebeca : Scholar {
    override fun students(): Int = 3
    override fun cards(): Int = 4
}

object Vincenzo : Scholar {
    override fun students(): Int = 4
    override fun cards(): Int = 4
}
