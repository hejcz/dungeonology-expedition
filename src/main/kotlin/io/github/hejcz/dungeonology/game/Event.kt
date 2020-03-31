package io.github.hejcz.dungeonology.game

import io.github.hejcz.dungeonology.game.card.CardId
import io.github.hejcz.dungeonology.game.zone.Point
import io.github.hejcz.dungeonology.game.zone.Zone
import io.github.hejcz.dungeonology.game.zone.ZoneId

interface Event

data class ErrorEvent(val errorCode: ErrorCode) : Event

data class NewHand(val cards: List<CardId>) : Event

data class NewZone(val id: ZoneId) : Event

object CantDiscoverNewZone : Event

data class BoardUpdated(val zones: Map<Point, Zone>) : Event

data class PlayerUpdated(val id: PlayerId, val position: Point) : Event