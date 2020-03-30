package io.github.hejcz.dungeonology.game.card

import io.github.hejcz.dungeonology.game.Game
import io.github.hejcz.dungeonology.game.PlayerId
import io.github.hejcz.dungeonology.game.zone.Cube

data class CardContext(
    val playerId: PlayerId,
    val game: Game,
    val data: Map<String, Any>
)

interface CardEffect {
    fun apply(ctx: CardContext): Game
    fun advance(ctx: CardContext): Game = ctx.game
    fun stages(ctx: CardContext): Int = 1
    fun isInstant() = false
}

interface InstantCardEffect : CardEffect {
    override fun isInstant() = true
}

object NoEffect : CardEffect {
    override fun apply(ctx: CardContext): Game = ctx.game
}

object LovePotionMainEffect : CardEffect {
    override fun apply(ctx: CardContext): Game =
        ctx.game.killStudents(2).copy(cardInProgress = SomeCardInProgress(this, 1))

    override fun advance(ctx: CardContext): Game {
        val stage = (ctx.game.cardInProgress as SomeCardInProgress).stage
        val otherPlayers = ctx.game.otherPlayersWithSomeCubes()
        val passedCube = Cube.valueOf(ctx.data["cube"] as String)
        val isLastPlayerToPassCube = stage == otherPlayers.size - 1
        return when {
            isLastPlayerToPassCube -> ctx.game.addCube(passedCube).copy(cardInProgress = NoCardInProgress)
            else -> ctx.game.addCube(passedCube).copy(cardInProgress = SomeCardInProgress(this, stage + 1))
        }
    }

    override fun stages(ctx: CardContext): Int =
        if (ctx.game.isCubeInZone()) 1 else 0 + ctx.game.otherPlayersWithSomeCubes().size
}

object LovePotionAuxiliaryEffect : InstantCardEffect {
    override fun apply(ctx: CardContext): Game =
        ctx.game.drawStudentsFromUniversity(1).performDivination(2)
}

object BackStabMainEffect : CardEffect {
    override fun apply(ctx: CardContext): Game =
        ctx.game.killStudents(1).drawStudentsFromUniversity(2)
            .takeStunToken(1)
            .stealCubeFrom(PlayerId(ctx.data["targetId"] as Int), Cube.valueOf(ctx.data["cube"] as String))
}

data class IncreaseStealthEffect(val change: Int, val studentsToDraw: Int = 0) : InstantCardEffect {
    override fun apply(ctx: CardContext): Game =
        ctx.game.drawStudentsFromUniversity(studentsToDraw).increaseStealthBy(change)
}


class IncreaseAlertEffect(private val change: Int, private val studentsToDraw: Int = 0) : InstantCardEffect {
    override fun apply(ctx: CardContext): Game =
        ctx.game.drawStudentsFromUniversity(studentsToDraw).increaseAlertBy(change)
}