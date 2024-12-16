package io.github.tomplum.libs.math

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.github.tomplum.libs.math.Direction.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class DirectionTest {
    @Nested
    inner class FromCharStaticFactoryConstructor {
        @Test
        fun up() {
            assertThat(Direction.fromChar('^')).isEqualTo(UP)
        }

        @Test
        fun right() {
            assertThat(Direction.fromChar('>')).isEqualTo(RIGHT)
        }

        @Test
        fun down() {
            assertThat(Direction.fromChar('v')).isEqualTo(DOWN)
        }

        @Test
        fun left() {
            assertThat(Direction.fromChar('<')).isEqualTo(LEFT)
        }

        @Test
        fun invalidChar() {
            val e = assertThrows<IllegalArgumentException> {
                Direction.fromChar('A')
            }
            assertThat(e.message).isEqualTo("Invalid Direction String: A")
        }
    }

    @Nested
    inner class Rotate {
        @Nested
        inner class Clockwise90 {
            @Test
            fun rotateClockwiseStartingUp() = assertThat(UP.rotate(90)).isEqualTo(RIGHT)

            @Test
            fun rotateClockwiseStartingRight() = assertThat(RIGHT.rotate(90)).isEqualTo(DOWN)

            @Test
            fun rotateClockwiseStartingDown() = assertThat(DOWN.rotate(90)).isEqualTo(LEFT)

            @Test
            fun rotateClockwiseStartingLeft() = assertThat(LEFT.rotate(90)).isEqualTo(UP)

            @Test
            fun rotateClockwiseStartingTopRight() = assertThat(TOP_RIGHT.rotate(90)).isEqualTo(BOTTOM_RIGHT)

            @Test
            fun rotateClockwiseStartingBottomRight() = assertThat(BOTTOM_RIGHT.rotate(90)).isEqualTo(BOTTOM_LEFT)

            @Test
            fun rotateClockwiseStartingBottomLeft() = assertThat(BOTTOM_LEFT.rotate(90)).isEqualTo(TOP_LEFT)

            @Test
            fun rotateClockwiseStartingTopLeft() = assertThat(TOP_LEFT.rotate(90)).isEqualTo(TOP_RIGHT)
        }

        @Nested
        inner class AntiClockwise90 {
            @Test
            fun rotateAntiClockwiseStartingUp() = assertThat(UP.rotate(-90)).isEqualTo(LEFT)

            @Test
            fun rotateAntiClockwiseStartingRight() = assertThat(RIGHT.rotate(-90)).isEqualTo(UP)

            @Test
            fun rotateAntiClockwiseStartingDown() = assertThat(DOWN.rotate(-90)).isEqualTo(RIGHT)

            @Test
            fun rotateAntiClockwiseStartingLeft() = assertThat(LEFT.rotate(-90)).isEqualTo(DOWN)

            @Test
            fun rotateAntiClockwiseStartingTopLeft() = assertThat(TOP_LEFT.rotate(-90)).isEqualTo(BOTTOM_LEFT)

            @Test
            fun rotateAntiClockwiseStartingBottomLeft() = assertThat(BOTTOM_LEFT.rotate(-90)).isEqualTo(BOTTOM_RIGHT)

            @Test
            fun rotateAntiClockwiseStartingBottomRight() = assertThat(BOTTOM_RIGHT.rotate(-90)).isEqualTo(TOP_RIGHT)

            @Test
            fun rotateAntiClockwiseStartingTopRight() = assertThat(TOP_RIGHT.rotate(-90)).isEqualTo(TOP_LEFT)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 95, 182, 280, 359, 400, -55])
        fun invalidAngle(angle: Int) {
            val e = assertThrows<IllegalArgumentException> { UP.rotate(angle) }
            assertThat(e.message).isEqualTo("Invalid Angle $angle")
        }
    }

    @Nested
    inner class Opposite {
        @ParameterizedTest(name = "{0} is opposite of {1}")
        @CsvSource(
            "UP, DOWN",
            "DOWN, UP",
            "LEFT, RIGHT",
            "RIGHT, LEFT",
            "TOP_RIGHT, BOTTOM_LEFT",
            "BOTTOM_LEFT, TOP_RIGHT",
            "BOTTOM_RIGHT, TOP_LEFT",
            "TOP_LEFT, BOTTOM_RIGHT"
        )
        fun `direction is opposite of expected`(direction: Direction, opposite: Direction) {
            assertThat(direction.isOpposite(opposite)).isTrue()
        }

        @ParameterizedTest(name = "{0} is not opposite of {1}")
        @CsvSource(
            "UP, UP",
            "DOWN, DOWN",
            "LEFT, LEFT",
            "RIGHT, RIGHT",
            "TOP_RIGHT, TOP_RIGHT",
            "BOTTOM_LEFT, BOTTOM_LEFT",
            "BOTTOM_RIGHT, BOTTOM_RIGHT",
            "TOP_LEFT, TOP_LEFT"
        )
        fun `direction is not opposite of itself`(direction: Direction, same: Direction) {
            assertThat(direction.isOpposite(same)).isFalse()
        }

        @ParameterizedTest(name = "{0} is not opposite of unrelated direction {1}")
        @CsvSource(
            "UP, RIGHT",
            "UP, TOP_RIGHT",
            "DOWN, LEFT",
            "LEFT, BOTTOM_LEFT",
            "RIGHT, TOP_LEFT",
            "TOP_RIGHT, TOP_LEFT",
            "BOTTOM_LEFT, BOTTOM_RIGHT",
            "BOTTOM_RIGHT, TOP_RIGHT",
            "TOP_LEFT, BOTTOM_LEFT"
        )
        fun `direction is not opposite of unrelated`(direction: Direction, unrelated: Direction) {
            assertThat(direction.isOpposite(unrelated)).isFalse()
        }
    }
}