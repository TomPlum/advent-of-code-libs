package io.github.tomplum.libs.math

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.libs.math.Direction.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DirectionTest {
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
}