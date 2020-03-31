package io.github.hejcz.dungeonology.game.zone

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ZonePlacementValidatorTest {

    @ParameterizedTest
    @MethodSource("io.github.hejcz.dungeonology.game.zone.ZonePlacementValidatorTestKt#zones")
    fun zonesPlacementValidation(expectation: Boolean, zone: Zone, point: Point, presentZones: Map<Point, Zone>) {
        if (expectation) {
            Assertions.assertTrue(ZonePlacementValidator.isValid(Point(0, 0), zone, point, presentZones))
        } else {
            Assertions.assertFalse(ZonePlacementValidator.isValid(Point(0, 0), zone, point, presentZones))
        }
    }

}

private fun zones() = Stream.of(
    arguments(true, HunterBivouac(), Point(0, 2), mapOf(Point(0, 0) to StartingZone())),
    arguments(true, HunterBivouac(), Point(1, 2), mapOf(Point(0, 0) to StartingZone())),
    arguments(true, HunterBivouac(), Point(-1, 2), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(0, -2), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(1, -2), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(-1, -2), mapOf(Point(0, 0) to StartingZone())),
    arguments(true, HunterBivouac(), Point(2, 0), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(2, 1), mapOf(Point(0, 0) to StartingZone())),
    arguments(true, HunterBivouac(), Point(2, -1), mapOf(Point(0, 0) to StartingZone())),
    arguments(true, HunterBivouac(), Point(-2, 0), mapOf(Point(0, 0) to StartingZone())),
    arguments(true, HunterBivouac(), Point(-2, 1), mapOf(Point(0, 0) to StartingZone())),
    arguments(true, HunterBivouac(), Point(-2, -1), mapOf(Point(0, 0) to StartingZone())),
    // overlapping
    arguments(false, HunterBivouac(), Point(0, 1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(1, 1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(-1, 1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(0, -1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(1, -1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(-1, -1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(1, 0), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(1, 1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(1, -1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(-1, 0), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(-1, 1), mapOf(Point(0, 0) to StartingZone())),
    arguments(false, HunterBivouac(), Point(-1, -1), mapOf(Point(0, 0) to StartingZone()))
)