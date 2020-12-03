package io.github.tomplum.libs.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.Test

class MapTileTest {
    @Test
    fun equalityPositive() {
        assertThat(MapTile("Test")).isEqualTo(MapTile("Test"))
    }

    @Test
    fun equalityTestNegative() {
        assertThat(MapTile("Right")).isNotEqualTo(MapTile("Wrong"))
    }

    @Test
    fun toStringTest() {
        assertThat(MapTile(15).toString()).isEqualTo("15")
    }
}