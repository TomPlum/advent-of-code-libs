package io.github.tomplum.libs.math.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import io.github.tomplum.libs.math.point.Point2D
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class AdventMapTest {
    @Nested
    inner class FindTile {
        @Test
        fun oneMatchingTile() {
            val map = TestAdventMap()
            map.addTileExample(Point2D(1, 5), TestMapTile(12))

            val result = map.findTileExample { tile -> tile.isMyTestValue() }

            assertThat(result).isEqualTo(Point2D(1, 5) to TestMapTile(12))
        }

        @Test
        fun multipleMatchesShouldPickFirst() {
            val map = TestAdventMap()
            map.addTileExample(Point2D(1, 5), TestMapTile(12))
            map.addTileExample(Point2D(2, 8), TestMapTile(12))

            val result = map.findTileExample { tile -> tile.isMyTestValue() }

            assertThat(result).isEqualTo(Point2D(1, 5) to TestMapTile(12))
        }

        @Test
        fun noMatches() {
            val map = TestAdventMap()
            map.addTileExample(Point2D(1, 5), TestMapTile(12))

            val result = map.findTileExample { tile -> tile.value == 893 }

            assertThat(result).isNull()
        }
    }

    private inner class TestAdventMap : AdventMap<Point2D, TestMapTile>() {
        fun findTileExample(predicate: (tile: TestMapTile) -> Boolean) = findTile(predicate)
        fun addTileExample(pos: Point2D, tile: TestMapTile) = addTile(pos, tile)
    }

    private inner class TestMapTile(private val data: Int) : MapTile<Int>(data) {
        fun isMyTestValue() = data == 12

        override fun equals(other: Any?): Boolean {
            if (other !is TestMapTile) return false
            return data == other.data
        }

        override fun hashCode(): Int {
            return Objects.hashCode(data)
        }
    }
}