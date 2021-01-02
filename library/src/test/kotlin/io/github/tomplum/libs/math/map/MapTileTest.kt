package io.github.tomplum.libs.math.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MapTileTest {
    @Nested
    inner class Equality {
        @Test
        fun sameValue() {
            assertThat(MapTile("Test")).isEqualTo(MapTile("Test"))
        }

        @Test
        fun differentValue() {
            assertThat(MapTile("Right")).isNotEqualTo(MapTile("Wrong"))
        }

        @Test
        fun differentType() {
            assertThat(MapTile('#')).isNotEqualTo(listOf<Char>())
        }
    }

    @Nested
    inner class HashCode {
        @Test
        fun sameValue() {
            assertThat(MapTile("Test").hashCode()).isEqualTo(MapTile("Test").hashCode())
        }

        @Test
        fun differentValue() {
            assertThat(MapTile("Right").hashCode()).isNotEqualTo(MapTile("Wrong").hashCode())
        }
    }

    @Nested
    inner class ToStringTest {
        @Test
        fun integerValue() {
            assertThat(MapTile(15).toString()).isEqualTo("15")
        }

        @Test
        fun stringValue() {
            assertThat(MapTile("Hello").toString()).isEqualTo("Hello")
        }
    }
}