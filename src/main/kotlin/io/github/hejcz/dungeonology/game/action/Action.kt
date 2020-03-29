package io.github.hejcz.dungeonology.game.action

import io.github.hejcz.dungeonology.game.card.AuxiliaryEffectId
import io.github.hejcz.dungeonology.game.card.SelectedCardEffect
import io.github.hejcz.dungeonology.game.card.CardId
import io.github.hejcz.dungeonology.game.jinx.JinxId
import io.github.hejcz.dungeonology.game.zone.ZoneId

interface Action

object SubmitThesis

data class Rest(val cardsToDrop: Set<CardId>, val jinxToDrop: JinxId)

data class MoveScholar(val target: ZoneId)

data class PlayCard(val id: CardId, val effect: SelectedCardEffect, val auxiliaryEffectId: AuxiliaryEffectId)