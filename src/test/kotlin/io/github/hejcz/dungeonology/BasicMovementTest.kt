package io.github.hejcz.dungeonology

import io.github.hejcz.dungeonology.game.*
import io.github.hejcz.dungeonology.game.action.MoveScholar
import io.github.hejcz.dungeonology.game.action.PlaceZone
import io.github.hejcz.dungeonology.game.action.Start
import io.github.hejcz.dungeonology.game.card.Card4
import io.github.hejcz.dungeonology.game.zone.Point
import io.github.hejcz.dungeonology.game.zone.ShepherdsSanctuary
import io.github.hejcz.dungeonology.game.zone.StartingZone
import io.github.hejcz.dungeonology.game.zone.ZoneId
import org.junit.jupiter.api.Test

class BasicMovementTest {

    @Test
    fun `leave starting zone`() {
        val game = Game(
            players = twoPlayers(),
            randomizer = TestRandomizer(),
            deck = Deck.from(List(20) { Card4 }),
            zones = TestZones(mapOf(Floor.FIRST to listOf(ShepherdsSanctuary())))
        )
        game.apply(PlayerId(1), Start)
            .apply(PlayerId(1), MoveScholar)
            .apply { hasNewZoneEvent(PlayerId(1) , ZoneId(13)) }
            .apply(PlayerId(1), PlaceZone(Point(-1, 2)))
            .apply { hasEvent(PlayerId(1), PlayerUpdated(PlayerId(1), position = Point(-1, 2))) }
            .apply { hasEvent(PlayerId(2), PlayerUpdated(PlayerId(1), position = Point(-1, 2))) }
            .apply { hasEvent(PlayerId(1), BoardUpdated(mapOf(Point(0, 0) to StartingZone(), Point(-1, 2) to ShepherdsSanctuary()))) }
            .apply { hasEvent(PlayerId(2), BoardUpdated(mapOf(Point(0, 0) to StartingZone(), Point(-1, 2) to ShepherdsSanctuary()))) }
    }
}