package io.github.hejcz.dungeonology

import io.github.hejcz.dungeonology.game.*
import io.github.hejcz.dungeonology.game.action.*
import io.github.hejcz.dungeonology.game.card.Card4
import io.github.hejcz.dungeonology.game.zone.*
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
            .apply(PlayerId(1), DiscoverZone)
            .apply { hasNewZoneEvent(PlayerId(1) , ZoneId(13)) }
            .apply(PlayerId(1), PlaceZone(Point(-1, 2)))
            .apply { hasEvent(PlayerId(1), PlayerUpdated(PlayerId(1), position = Point(-1, 2))) }
            .apply { hasEvent(PlayerId(2), PlayerUpdated(PlayerId(1), position = Point(-1, 2))) }
            .apply { hasEvent(PlayerId(1), BoardUpdated(mapOf(Point(0, 0) to StartingZone(), Point(-1, 2) to ShepherdsSanctuary()))) }
            .apply { hasEvent(PlayerId(2), BoardUpdated(mapOf(Point(0, 0) to StartingZone(), Point(-1, 2) to ShepherdsSanctuary()))) }
    }

    @Test
    fun `zone can't be added if no more left`() {
        val game = Game(
            players = twoPlayers(),
            randomizer = TestRandomizer(),
            deck = Deck.from(List(20) { Card4 }),
            zones = TestZones(mapOf(Floor.FIRST to listOf(ShepherdsSanctuary())))
        )
        game.apply(PlayerId(1), Start)
             .apply(PlayerId(1), DiscoverZone)
            .apply { hasNewZoneEvent(PlayerId(1) , ZoneId(13)) }
            .apply(PlayerId(1), PlaceZone(Point(-1, 2)))
            .apply(PlayerId(1), DiscoverZone)
            .apply { hasEvent(PlayerId(1) , NoZonesLeft) }
    }

    @Test
    fun `zone can't be added if this is last one and does not match`() {
        val game = Game(
            players = twoPlayers(),
            randomizer = TestRandomizer(),
            deck = Deck.from(List(20) { Card4 }),
            zones = TestZones(mapOf(Floor.FIRST to listOf(OldOnesRepose(), WorkersConvivium(), GrandBacchanal())))
        )
        game.apply(PlayerId(1), Start)
            .apply(PlayerId(1), DiscoverZone)
            .apply(PlayerId(1), PlaceZone(Point(2, 1)))
            .apply(PlayerId(1), DiscoverZone)
            .apply(PlayerId(1), PlaceZone(Point(3, 3)))
            .apply(PlayerId(1), Finish)
            .apply(PlayerId(2), Finish)
            .apply(PlayerId(1), MoveScholar(Point(2, 1)))
            .apply { hasEvent(PlayerId(1), PlayerUpdated(PlayerId(1), Point(2, 1))) }
            .apply { hasEvent(PlayerId(2), PlayerUpdated(PlayerId(1), Point(2, 1))) }
            .apply(PlayerId(1), DiscoverZone)
            .apply { hasEvent(PlayerId(1) , CantDiscoverNewZone(GrandBacchanal())) }
    }

    @Test
    fun `zone should be skipped and added to the end if any other left`() {
        val game = Game(
            players = twoPlayers(),
            randomizer = TestRandomizer(),
            deck = Deck.from(List(20) { Card4 }),
            zones = TestZones(mapOf(Floor.FIRST to listOf(OldOnesRepose(), WorkersConvivium(), GrandBacchanal(), ShepherdsSanctuary())))
        )
        game.apply(PlayerId(1), Start)
            .apply(PlayerId(1), DiscoverZone)
            .apply(PlayerId(1), PlaceZone(Point(2, 1)))
            .apply(PlayerId(1), DiscoverZone)
            .apply(PlayerId(1), PlaceZone(Point(3, 3)))
            .apply(PlayerId(1), Finish)
            .apply(PlayerId(2), Finish)
            .apply(PlayerId(1), MoveScholar(Point(2, 1)))
            .apply { hasEvent(PlayerId(1), PlayerUpdated(PlayerId(1), Point(2, 1))) }
            .apply { hasEvent(PlayerId(2), PlayerUpdated(PlayerId(1), Point(2, 1))) }
            .apply(PlayerId(1), DiscoverZone)
            .apply { hasNewZoneEvent(PlayerId(1), ZoneId(13)) } // shepherds instead of grand bacchanal
            .apply(PlayerId(1), PlaceZone(Point(0, 4)))
            .apply(PlayerId(1), DiscoverZone)
            .apply { hasNewZoneEvent(PlayerId(1), ZoneId(12)) } // rewinded grand bacchanal
    }
}