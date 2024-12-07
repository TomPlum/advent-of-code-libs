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
    inner class AdjacentTiles {
        @Test
        fun hasAdjacentTiles() {
            val map = TestAdventMap()
            map.addTileExample(Point2D(0, 0), TestMapTile(12))
            map.addTileExample(Point2D(0, 1), TestMapTile(12))
            map.addTileExample(Point2D(0, 2), TestMapTile(12))
            map.addTileExample(Point2D(1, 0), TestMapTile(12))
            map.addTileExample(Point2D(1, 1), TestMapTile(12))
            map.addTileExample(Point2D(1, 2), TestMapTile(12))
            map.addTileExample(Point2D(2, 0), TestMapTile(12))
            map.addTileExample(Point2D(2, 1), TestMapTile(12))
            map.addTileExample(Point2D(2, 2), TestMapTile(12))

            val expectedAdjacent = mapOf(
                Pair(Point2D(1, 2), TestMapTile(12)), Pair(Point2D(2, 1), TestMapTile(12)),
                Pair(Point2D(1, 0), TestMapTile(12)), Pair(Point2D(0, 1), TestMapTile(12)),
                Pair(Point2D(0, 0), TestMapTile(12)), Pair(Point2D(2, 0), TestMapTile(12)),
                Pair(Point2D(2, 2), TestMapTile(12)), Pair(Point2D(0, 2), TestMapTile(12)),
            )
            val adjacentTiles = map.getAdjacentTiles(setOf(Point2D(1, 1)))
            assertThat(adjacentTiles).isEqualTo(expectedAdjacent)
        }
    }

    @Nested
    inner class AdjacentTilesDefault {
        @Test
        fun setOfTiles_singleTile_hasAdjacentTiles() {
            val map = TestAdventMap()
            map.addTileExample(Point2D(0, 0), TestMapTile(12))
            map.addTileExample(Point2D(0, 1), TestMapTile(12))
            map.addTileExample(Point2D(0, 2), TestMapTile(12))
            map.addTileExample(Point2D(1, 0), TestMapTile(12))
            map.addTileExample(Point2D(1, 1), TestMapTile(12))
            map.addTileExample(Point2D(1, 2), TestMapTile(12))
            map.addTileExample(Point2D(2, 0), TestMapTile(12))

            val expectedAdjacent = mapOf(
                Pair(Point2D(1, 2), TestMapTile(12)), Pair(Point2D(2, 1), null),
                Pair(Point2D(1, 0), TestMapTile(12)), Pair(Point2D(0, 1), TestMapTile(12)),
                Pair(Point2D(0, 0), TestMapTile(12)), Pair(Point2D(2, 0), TestMapTile(12)),
                Pair(Point2D(2, 2), null), Pair(Point2D(0, 2), TestMapTile(12)),
            )
            val adjacentTiles = map.getAdjacentTiles(setOf(Point2D(1, 1)), null)
            assertThat(adjacentTiles).isEqualTo(expectedAdjacent)
        }

        @Test
        fun setOfTiles_multipleTiles_hasAdjacentTiles() {
            val map = TestAdventMap()
            map.addTileExample(Point2D(0, 0), TestMapTile(12))
            map.addTileExample(Point2D(0, 1), TestMapTile(12))
            map.addTileExample(Point2D(0, 2), TestMapTile(12))
            map.addTileExample(Point2D(1, 0), TestMapTile(12))
            map.addTileExample(Point2D(1, 1), TestMapTile(12))
            map.addTileExample(Point2D(1, 2), TestMapTile(12))
            map.addTileExample(Point2D(2, 0), TestMapTile(12))

            val expectedAdjacent = mapOf(
                Pair(Point2D(1, 2), TestMapTile(12)), Pair(Point2D(2, 1), null),
                Pair(Point2D(1, 0), TestMapTile(12)), Pair(Point2D(0, 1), TestMapTile(12)),
                Pair(Point2D(0, 0), TestMapTile(12)), Pair(Point2D(2, 0), TestMapTile(12)),
                Pair(Point2D(2, 2), null), Pair(Point2D(0, 2), TestMapTile(12)),
                Pair(Point2D(5, 6), null), Pair(Point2D(6, 5), null),
                Pair(Point2D(5, 4), null), Pair(Point2D(4, 5), null),
                Pair(Point2D(4, 4), null), Pair(Point2D(6, 4), null),
                Pair(Point2D(6, 6), null), Pair(Point2D(4, 6), null),
            )
            val adjacentTiles = map.getAdjacentTiles(setOf(Point2D(1, 1), Point2D(5, 5)), null)
            assertThat(adjacentTiles).isEqualTo(expectedAdjacent)
        }

        @Test
        fun singleTile_hasAdjacentTiles() {
            val map = TestAdventMap()
            map.addTileExample(Point2D(0, 0), TestMapTile(12))
            map.addTileExample(Point2D(0, 1), TestMapTile(12))
            map.addTileExample(Point2D(0, 2), TestMapTile(12))
            map.addTileExample(Point2D(1, 0), TestMapTile(12))
            map.addTileExample(Point2D(1, 1), TestMapTile(12))
            map.addTileExample(Point2D(1, 2), TestMapTile(12))
            map.addTileExample(Point2D(2, 0), TestMapTile(12))

            val expectedAdjacent = mapOf(
                Pair(Point2D(1, 2), TestMapTile(12)), Pair(Point2D(2, 1), null),
                Pair(Point2D(1, 0), TestMapTile(12)), Pair(Point2D(0, 1), TestMapTile(12)),
                Pair(Point2D(0, 0), TestMapTile(12)), Pair(Point2D(2, 0), TestMapTile(12)),
                Pair(Point2D(2, 2), null), Pair(Point2D(0, 2), TestMapTile(12)),
            )
            val adjacentTiles = map.getAdjacentTiles(Point2D(1, 1), null)
            assertThat(adjacentTiles).isEqualTo(expectedAdjacent)
        }
    }
    
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

    @Nested
    inner class RemoveTile {
        @Test
        fun tileExists() {
            val map = TestAdventMap()
            val positionToRemove = Point2D(1, 5)
            val tile = TestMapTile(12)
            map.addTileExample(positionToRemove, tile)

            val result = map.removeTileExample(positionToRemove)

            assertThat(result).isEqualTo(tile)
        }

        @Test
        fun tileDoesNotExist() {
            val map = TestAdventMap()
            val positionToRemove = Point2D(1, 5)
            val tile = TestMapTile(12)
            map.addTileExample(positionToRemove, tile)

            val result = map.removeTileExample(Point2D(-6, 12))

            assertThat(result).isNull()
        }
    }

    @Nested
    inner class OverwriteData {
        @Test
        fun overwriteWithNewDataSet() {
            val map = TestAdventMap()
            map.addTileExample(Point2D(1, 5), TestMapTile(12))
            assertThat(map.data).isEqualTo(mapOf(Point2D(1, 5) to TestMapTile(12)))

            map.overwriteDataExample(mutableMapOf(Point2D(0, 13) to TestMapTile(5)))
            assertThat(map.data).isEqualTo(mapOf(Point2D(0, 13) to TestMapTile(5)))
        }
    }

    private inner class TestAdventMap : AdventMap<Point2D, TestMapTile>() {
        fun findTileExample(predicate: (tile: TestMapTile) -> Boolean) = findTile(predicate)
        fun addTileExample(pos: Point2D, tile: TestMapTile) = addTile(pos, tile)
        fun removeTileExample(pos: Point2D) = removeTile(pos)
        fun overwriteDataExample(data: MutableMap<Point2D, TestMapTile>) = overwriteData(data)
        fun getAdjacentTiles(positions: Set<Point2D>) = adjacentTiles(positions)
        fun getAdjacentTiles(position: Point2D, default: TestMapTile?) = adjacentTiles(position, default)
        fun getAdjacentTiles(positions: Set<Point2D>, default: TestMapTile?) = adjacentTiles(positions, default)
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