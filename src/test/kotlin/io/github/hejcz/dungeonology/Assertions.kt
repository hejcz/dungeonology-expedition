package io.github.hejcz.dungeonology

import io.github.hejcz.dungeonology.game.*
import io.github.hejcz.dungeonology.game.card.CardId
import io.github.hejcz.dungeonology.game.zone.ZoneId
import org.assertj.core.api.Assertions

fun Game.hasNewHandContaining(playerId: PlayerId, vararg ids: Int) {
    Assertions.assertThat(this.events.getValue(playerId))
        .contains(NewHand(ids.toList().map { CardId(it) }))
}

fun Game.hasNewZoneEvent(playerId: PlayerId, zoneId: ZoneId) {
    Assertions.assertThat(this.events.getValue(playerId))
        .contains(NewZone(zoneId))
}

fun Game.hasEvent(playerId: PlayerId, event: Event) {
    Assertions.assertThat(this.events.getValue(playerId)).contains(event)
}