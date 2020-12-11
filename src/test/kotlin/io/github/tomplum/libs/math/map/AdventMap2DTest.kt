package io.github.tomplum.libs.math.map

import assertk.assertThat
import assertk.assertions.*
import io.github.tomplum.libs.math.Point2D
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class AdventMap2DTest {
    @Nested
    inner class AddTile {
        @Test
        fun addTile() {
            val map = TestAdventMap2D()
            val tile = TestMapTile(6)
            map.addExampleTile(Point2D(3, 4), tile)
            assertThat(map.getExampleTile(Point2D(3, 4), TestMapTile(0))).isEqualTo(tile)
        }

        @Test
        fun addTileAtExistingPointShouldUpdate() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(3, 4), TestMapTile(6))
            map.addExampleTile(Point2D(3, 4), TestMapTile(23))
            assertThat(map.getExampleTile(Point2D(3, 4)).value).isEqualTo(23)
        }
    }

    @Nested
    inner class GetTile {
        @Test
        fun getTileThatDoesntExist() {
            val e = assertThrows<IllegalArgumentException> { TestAdventMap2D().getExampleTile(Point2D(0, 0)) }
            assertThat(e.message).isEqualTo("Map does not contain tile at (0, 0)")
        }
    }

    @Nested
    inner class GetTileWithDefault {
        @Test
        @DisplayName("Given a Map with some tiles, when getting a tile that exists, then it should return that tile")
        fun tileExists() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(3, 4), TestMapTile(4))
            map.addExampleTile(Point2D(2, 12), TestMapTile(1))
            assertThat(map.getExampleTile(Point2D(2, 12), TestMapTile(0)).toString()).isEqualTo("1")
        }

        @Test
        @DisplayName("Given a Map with some tiles, when getting a tile that doesn't exist, then it should return the default")
        fun tileDoesNotExistShouldReturnDefault() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(0, 5), TestMapTile(1))
            assertThat(map.getExampleTile(Point2D(2, 12), TestMapTile(0)).toString()).isEqualTo("0")
        }

        @Test
        fun nullValue() {
            assertThat(TestAdventMap2D().getExampleTile(Point2D(1,1), null)).isNull()
        }
    }

    @Nested
    inner class HasRecorded {
        @Test
        fun hasRecordedPositive() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(3, 4), TestMapTile(4))
            assertThat(map.hasRecordedExample(Point2D(3, 4))).isTrue()
        }

        @Test
        fun hasRecordedNegative() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(3, 4), TestMapTile(4))
            assertThat(map.hasRecordedExample(Point2D(5, 8))).isFalse()
        }
    }

    @Nested
    inner class HasTile {
        @Test
        fun hasTilePositive() {
            val map = TestAdventMap2D()
            val tile = TestMapTile(4)
            map.addExampleTile(Point2D(3, 4), tile)
            assertThat(map.hasTileExample(tile)).isTrue()
        }

        @Test
        fun hasTileNegative() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(3, 4), TestMapTile(4))
            assertThat(map.hasTileExample(TestMapTile(1))).isFalse()
        }
    }

    @Nested
    inner class GetTileQuantity {
        @Test
        fun tileQuantityWhenPositive() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(3, 4), TestMapTile(4))
            map.addExampleTile(Point2D(0, 0), TestMapTile(12))
            assertThat(map.tileQuantityExample()).isEqualTo(2)
        }

        @Test
        fun tileQuantityWhenEmpty() {
            assertThat(TestAdventMap2D().tileQuantityExample()).isEqualTo(0)
        }
    }

    @Nested
    inner class AdjacentTiles {
        @Test
        fun hasAdjacentTiles() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(0,0), TestMapTile(4))
            map.addExampleTile(Point2D(0,1), TestMapTile(12))
            map.addExampleTile(Point2D(0,2), TestMapTile(12))
            map.addExampleTile(Point2D(1,0), TestMapTile(12))
            map.addExampleTile(Point2D(1,1), TestMapTile(12))
            map.addExampleTile(Point2D(1,2), TestMapTile(12))
            map.addExampleTile(Point2D(2,0), TestMapTile(12))
            map.addExampleTile(Point2D(2,1), TestMapTile(12))
            map.addExampleTile(Point2D(2,2), TestMapTile(12))
            val expectedAdjacent = mapOf(
                Pair(Point2D(1,2), TestMapTile(12)), Pair(Point2D(2,1), TestMapTile(12)),
                Pair(Point2D(1,0), TestMapTile(12)), Pair(Point2D(0,1), TestMapTile(12))
            )
            assertThat(map.getAdjacentTiles(setOf(Point2D(1,1)))).isEqualTo(expectedAdjacent)
        }
    }

    @Nested
    inner class OrthogonallyAdjacentTiles {
        @Test
        fun hasAdjacentTiles() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(0,0), TestMapTile(4))
            map.addExampleTile(Point2D(0,1), TestMapTile(12))
            map.addExampleTile(Point2D(0,2), TestMapTile(12))
            map.addExampleTile(Point2D(1,1), TestMapTile(12))
            map.addExampleTile(Point2D(1,2), TestMapTile(12))
            map.addExampleTile(Point2D(2,0), TestMapTile(12))
            map.addExampleTile(Point2D(2,1), TestMapTile(12))
            map.addExampleTile(Point2D(2,2), TestMapTile(12))
            val expectedAdjacent = mapOf(
                Pair(Point2D(1,2), TestMapTile(12)), Pair(Point2D(2,1), TestMapTile(12)),
                Pair(Point2D(1,0), null), Pair(Point2D(0,1), TestMapTile(12))
            )
            assertThat(map.getAdjacentTilesOrthogonal(setOf(Point2D(1,1)))).isEqualTo(expectedAdjacent)
        }
    }

    @Nested
    inner class FilterTiles {
        @Test
        fun filterTiles() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(3, 4), TestMapTile(4))
            val targetTile = TestMapTile(12)
            map.addExampleTile(Point2D(0, 0), targetTile)
            assertThat(map.filterTilesExample { it.isMyTestValue() }).isEqualTo(mapOf(Pair(Point2D(0, 0), targetTile)))
        }
    }

    @Nested
    inner class FilterPoints {
        @Test
        fun filterPoints() {
            val map = TestAdventMap2D()
            for (y in 0..100) {
                for (x in 0..100) {
                    map.addExampleTile(Point2D(x, y), TestMapTile(y))
                }
            }
            val tiles = map.filterPointsExample(setOf(Point2D(17, 4), Point2D(56, 86), Point2D(100, 100), Point2D(25, 99)))
            assertThat(tiles).isEqualTo(
                mapOf(
                    Pair(Point2D(17, 4), TestMapTile(4)), Pair(Point2D(56, 86), TestMapTile(86)),
                    Pair(Point2D(25, 99), TestMapTile(99)), Pair(Point2D(100, 100), TestMapTile(100))
                )
            )
        }

        @Test
        fun filterPointsShouldIgnoreUnrecordedTiles() {
            val map = TestAdventMap2D()
            for (y in 0..10) {
                for (x in 0..10) {
                    map.addExampleTile(Point2D(x, y), TestMapTile(y))
                }
            }
            val tiles = map.filterPointsExample(setOf(Point2D(17, 4), Point2D(1, 2)))
            assertThat(tiles).isEqualTo(mapOf(Pair(Point2D(1, 2), TestMapTile(2))))
        }
    }

    @Nested
    inner class Reset {
        @Test
        fun shouldClearMap() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(12, 14), TestMapTile(1))
            map.reset()
            assertThat(map.tileQuantityExample()).isEqualTo(0)
        }
    }

    @Nested
    inner class Snapshot {
        @Test
        fun populatedMap() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(1, 2), TestMapTile(14))
            assertThat(map.snapshotExample()).isEqualTo(mapOf(Pair(Point2D(1, 2), TestMapTile(14))))
        }
    }

    @Nested
    inner class Equality {
        @Test
        fun emptyMapsShouldBeEqual() {
            assertThat(TestAdventMap2D()).isEqualTo(TestAdventMap2D())
        }

        @Test
        fun hasSameData() {
            val first = TestAdventMap2D()
            first.addExampleTile(Point2D(4, 6), TestMapTile(3))
            val second = TestAdventMap2D()
            second.addExampleTile(Point2D(4, 6), TestMapTile(3))
            assertThat(first).isEqualTo(second)
        }

        @Test
        fun hasDifferentData() {
            val first = TestAdventMap2D()
            first.addExampleTile(Point2D(3, 6), TestMapTile(7))
            val second = TestAdventMap2D()
            second.addExampleTile(Point2D(4, 6), TestMapTile(3))
            assertThat(first).isNotEqualTo(second)
        }

        @Test
        fun differentTypes() {
            assertThat(TestAdventMap2D()).isNotEqualTo(mapOf<Int, TestMapTile>())
        }
    }

    @Nested
    inner class HashCode {
        @Test
        fun emptyMap() {
            assertThat(TestAdventMap2D().hashCode()).isEqualTo(TestAdventMap2D().hashCode())
        }

        @Test
        fun hasSameData() {
            val first = TestAdventMap2D()
            first.addExampleTile(Point2D(4, 6), TestMapTile(3))
            val second = TestAdventMap2D()
            second.addExampleTile(Point2D(4, 6), TestMapTile(3))
            assertThat(first.hashCode()).isEqualTo(second.hashCode())
        }
    }

    @Nested
    inner class MinimumX {
         @Test
         fun zeroValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(0, 1), TestMapTile(1))
             assertThat(map.getMinX()).isEqualTo(0)
         }

         @Test
         fun negativeValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(-5, 1), TestMapTile(1))
             assertThat(map.getMinX()).isEqualTo(-5)
         }

         @Test
         fun positiveValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(57, 1), TestMapTile(1))
             assertThat(map.getMinX()).isEqualTo(57)
         }

         @Test
         fun multipleValuesShouldReturnSmallest() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(57, 1), TestMapTile(1))
             map.addExampleTile(Point2D(-6, 1), TestMapTile(1))
             map.addExampleTile(Point2D(12, 1), TestMapTile(1))
             assertThat(map.getMinX()).isEqualTo(-6)
         }
    }

    @Nested
    inner class MaximumX {
         @Test
         fun zeroValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(0, 1), TestMapTile(1))
             assertThat(map.getMaxX()).isEqualTo(0)
         }

         @Test
         fun negativeValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(-5, 1), TestMapTile(1))
             assertThat(map.getMaxX()).isEqualTo(-5)
         }

         @Test
         fun positiveValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(57, 1), TestMapTile(1))
             assertThat(map.getMaxX()).isEqualTo(57)
         }

         @Test
         fun multipleValuesShouldReturnLargest() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(57, 1), TestMapTile(1))
             map.addExampleTile(Point2D(-6, 1), TestMapTile(1))
             map.addExampleTile(Point2D(12, 1), TestMapTile(1))
             assertThat(map.getMaxX()).isEqualTo(57)
         }
    }

    @Nested
    inner class MinimumY {
         @Test
         fun zeroValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(0, 0), TestMapTile(1))
             assertThat(map.getMinY()).isEqualTo(0)
         }

         @Test
         fun negativeValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(-5, -6), TestMapTile(1))
             assertThat(map.getMinY()).isEqualTo(-6)
         }

         @Test
         fun positiveValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(57, 4), TestMapTile(1))
             assertThat(map.getMinY()).isEqualTo(4)
         }

         @Test
         fun multipleValuesShouldReturnSmallest() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(57, 4), TestMapTile(1))
             map.addExampleTile(Point2D(-6, 23), TestMapTile(1))
             map.addExampleTile(Point2D(12, -5), TestMapTile(1))
             assertThat(map.getMinY()).isEqualTo(-5)
         }
    }

    @Nested
    inner class MaximumY {
         @Test
         fun zeroValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(0, 0), TestMapTile(1))
             assertThat(map.getMaxY()).isEqualTo(0)
         }

         @Test
         fun negativeValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(-5, -6), TestMapTile(1))
             assertThat(map.getMaxY()).isEqualTo(-6)
         }

         @Test
         fun positiveValue() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(57, 4), TestMapTile(1))
             assertThat(map.getMaxY()).isEqualTo(4)
         }

         @Test
         fun multipleValuesShouldReturnLargest() {
             val map = TestAdventMap2D()
             map.addExampleTile(Point2D(57, 4), TestMapTile(1))
             map.addExampleTile(Point2D(-6, 23), TestMapTile(1))
             map.addExampleTile(Point2D(12, -5), TestMapTile(1))
             assertThat(map.getMaxY()).isEqualTo(23)
         }
    }

    @Nested
    inner class ToStringTest {
        @Test
        fun empty() {
            assertThat(TestAdventMap2D().toString()).isEqualTo(" \n")
        }

        @Test
        fun onlyValuesOnXAxis() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(0,0), TestMapTile(0))
            map.addExampleTile(Point2D(1,0), TestMapTile(1))
            map.addExampleTile(Point2D(2,0), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0 1 0\n")
        }

        @Test
        fun onlyValuesOnYAxis() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(0,0), TestMapTile(0))
            map.addExampleTile(Point2D(0,1), TestMapTile(1))
            map.addExampleTile(Point2D(0,2), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0\n1\n0\n")
        }

        @Test
        fun valuesOnBothAxes() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(0,0), TestMapTile(0))
            map.addExampleTile(Point2D(1,0), TestMapTile(1))
            map.addExampleTile(Point2D(2,0), TestMapTile(0))
            map.addExampleTile(Point2D(0,1), TestMapTile(0))
            map.addExampleTile(Point2D(1,1), TestMapTile(1))
            map.addExampleTile(Point2D(2,1), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0 1 0\n0 1 0\n")
        }

        @Test
        fun missingCoordinatesBetweenExistingOnes() {
            val map = TestAdventMap2D()
            map.addExampleTile(Point2D(0,0), TestMapTile(0))
            map.addExampleTile(Point2D(2,0), TestMapTile(0))
            map.addExampleTile(Point2D(0,1), TestMapTile(0))
            map.addExampleTile(Point2D(2,1), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0   0\n0   0\n")
        }
    }

    private inner class TestAdventMap2D : AdventMap2D<TestMapTile>() {
        fun tileQuantityExample() = tileQuantity()
        fun addExampleTile(pos: Point2D, default: TestMapTile) = addTile(pos, default)
        fun getExampleTile(pos: Point2D) = getTile(pos)
        fun getExampleTile(pos: Point2D, default: TestMapTile?) = getTile(pos, default)
        fun hasRecordedExample(pos: Point2D) = hasRecorded(pos)
        fun hasTileExample(tile: TestMapTile) = hasTile(tile)
        fun filterPointsExample(positions: Set<Point2D>) = filterPoints(positions)
        fun filterTilesExample(predicate: (TestMapTile) -> Boolean) = filterTiles(predicate)
        fun getAdjacentTiles(positions: Set<Point2D>) = adjacentTiles(positions)
        fun getAdjacentTilesOrthogonal(positions: Set<Point2D>) = adjacentTilesOrthogonal(positions)
        fun snapshotExample() = snapshot()
        fun getMinX() = xMin()
        fun getMinY() = yMin()
        fun getMaxX() = xMax()
        fun getMaxY() = yMax()
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