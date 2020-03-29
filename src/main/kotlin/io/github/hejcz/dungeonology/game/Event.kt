package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.card.CardId

interface Event

data class ErrorEvent(val errorCode: ErrorCode) : Event

data class NewHand(val cards: List<CardId>) : Event