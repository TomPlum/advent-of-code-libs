package io.github.tomplum.libs.math

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.PI

class ExtensionsTest {
    @Nested
    inner class ToRadians {
        @Test
        fun zero() {
            assertThat(0.0.toRadians()).isEqualTo(0.0)
        }

        @Test
        fun ninety() {
            assertThat(90.0.toRadians()).isEqualTo(PI / 2)
        }

        @Test
        fun oneHundredAndEighty() {
            assertThat(180.0.toRadians()).isEqualTo(PI)
        }

        @Test
        fun twoHundredAndSeventy() {
            assertThat(270.0.toRadians()).isEqualTo(1.5 * PI)
        }

        @Test
        fun threeHundredAndSixty() {
            assertThat(360.0.toRadians()).isEqualTo(2 * PI)
        }
    }
}