package io.github.hejcz.dungeonology.game.action

import io.github.hejcz.dungeonology.game.card.AuxiliaryEffectId
import io.github.hejcz.dungeonology.game.card.CardId
import io.github.hejcz.dungeonology.game.card.SelectedCardEffect
import io.github.hejcz.dungeonology.game.jinx.JinxId
import io.github.hejcz.dungeonology.game.zone.Point

sealed class Action

object SubmitThesis : Action()

data class Rest(val cardsToDrop: Set<CardId>, val jinxToDrop: JinxId) : Action()

object DiscoverZone : Action()

data class MoveScholar(val p: Point) : Action()

data class PlaceZone(val p: Point) : Action()

data class PlayCard(val id: CardId, val effect: SelectedCardEffect, val auxiliaryEffectId: AuxiliaryEffectId) : Action()

object Finish : Action()

object Start : Action()