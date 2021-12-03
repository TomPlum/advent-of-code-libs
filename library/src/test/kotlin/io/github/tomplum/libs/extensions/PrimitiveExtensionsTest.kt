package io.github.tomplum.libs.extensions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.PI

class PrimitiveExtensionsTest {
    @Nested
    inner class IntToBinary {
        @Test
        fun decimalTo36bit() {
            val binary = 11.toBinary(36)
            val expected = IntArray(36)
            expected[35] = 1
            expected[34] = 1
            expected[32] = 1
            assertThat(binary).isEqualTo(expected)
        }
    }

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

