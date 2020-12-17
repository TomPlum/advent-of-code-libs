package io.github.tomplum.libs.math.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import io.github.tomplum.libs.math.point.Point3D
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class AdventMap3DTest {
    @Nested
    inner class AddTile {
        @Test
        fun addTile() {
            val map = TestAdventMap3D()
            val tile = TestMapTile(6)
            map.addExampleTile(Point3D(3, 4, 0), tile)
            assertThat(map.getExampleTile(Point3D(3, 4, 0))).isEqualTo(tile)
        }

        @Test
        fun addTileAtExistingPointShouldUpdate() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(3, 4, 2), TestMapTile(6))
            map.addExampleTile(Point3D(3, 4, 2), TestMapTile(23))
            assertThat(map.getExampleTile(Point3D(3, 4, 2)).value).isEqualTo(23)
        }
    }
    
    @Nested
    inner class GetTile {
        @Test
        @DisplayName("Given a Map with some tiles, when getting a tile that exists, then it should return that tile")
        fun getTilePositive() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(3, 4, 12), TestMapTile(4))
            map.addExampleTile(Point3D(2, 12, 4), TestMapTile(1))
            assertThat(map.getExampleTile(Point3D(2, 12, 4)).toString()).isEqualTo("1")
        }

        @Test
        fun getTileThatDoesntExist() {
            val e = assertThrows<IllegalArgumentException> { TestAdventMap3D().getExampleTile(Point3D(0, 0, 0)) }
            assertThat(e.message).isEqualTo("Map does not contain tile at (0, 0, 0)")
        }
    }
    
    @Nested
    inner class HasRecorded {
        @Test
        fun hasRecordedPositive() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(3, 4, 5), TestMapTile(4))
            assertThat(map.hasMapped(Point3D(3, 4, 5))).isTrue()
        }

        @Test
        fun hasRecordedNegative() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(3, 4, 2), TestMapTile(4))
            assertThat(map.hasMapped(Point3D(5, 8, 0))).isFalse()
        }
    }
    
    @Nested
    inner class FilterTiles {
        @Test
        fun filterTiles() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(3, 4, 1), TestMapTile(4))
            val targetTile = TestMapTile(12)
            map.addExampleTile(Point3D(0, 0, 0), targetTile)
            assertThat(map.filterTilesExample { it.isMyTestValue() }).isEqualTo(mapOf(Pair(Point3D(0, 0, 0), targetTile)))
        }
    }

    @Nested
    inner class FilterPoints {
        @Test
        fun filterPoints() {
            val map = TestAdventMap3D()
            for (z in 0..100) {
                for (y in 0..100) {
                    for (x in 0..100) {
                        map.addExampleTile(Point3D(x, y, z), TestMapTile(y))
                    }
                }
            }
            val tiles = map.filterPointsExample(setOf(Point3D(17, 4, 0), Point3D(56, 86, 1), Point3D(100, 100, 2), Point3D(25, 99, 3)))
            assertThat(tiles).isEqualTo(mapOf(Pair(Point3D(17,4,0), TestMapTile(4)), Pair(Point3D(56,86,1), TestMapTile(86)),
                Pair(Point3D(25,99,3), TestMapTile(99)), Pair(Point3D(100,100,2), TestMapTile(100))))
        }

        @Test
        fun filterPointsShouldIgnoreUnrecordedTiles() {
            val map = TestAdventMap3D()
            for (z in 0..2) {
                for (y in 0..10) {
                    for (x in 0..10) {
                        map.addExampleTile(Point3D(x, y, z), TestMapTile(y))
                    }
                }
            }
            val tiles = map.filterPointsExample(setOf(Point3D(17, 4, 3), Point3D(1, 2, 0)))
            assertThat(tiles).isEqualTo(mapOf(Pair(Point3D(1,2,0), TestMapTile(2))))
        }
    }
    
    @Nested
    inner class AdjacentTiles {
        @Test
        fun hasAdjacentTiles() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(4))
            map.addExampleTile(Point3D(0,1,0), TestMapTile(12))
            map.addExampleTile(Point3D(0,2,0), TestMapTile(12))
            map.addExampleTile(Point3D(1,0,0), TestMapTile(12))
            map.addExampleTile(Point3D(1,1,0), TestMapTile(12))
            map.addExampleTile(Point3D(1,2,0), TestMapTile(12))
            map.addExampleTile(Point3D(2,0,0), TestMapTile(12))
            map.addExampleTile(Point3D(2,1,0), TestMapTile(12))
            map.addExampleTile(Point3D(2,2,0), TestMapTile(12))
            val expectedAdjacent = mapOf(
                Pair(Point3D(1,2,0), TestMapTile(12)), Pair(Point3D(2,1,0), TestMapTile(12)),
                Pair(Point3D(1,0,0), TestMapTile(12)), Pair(Point3D(0,1,0), TestMapTile(12))
            )
            assertThat(map.getAdjacentTiles(setOf(Point3D(1,1,0)))).isEqualTo(expectedAdjacent)
        }
    }

    @Nested
    inner class DuplicateTopLayer {
        @Test
        fun duplicateOnce() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0, 0, 0), TestMapTile(1))
            assertThat(map.hasMapped(Point3D(0, 0, 1))).isFalse()
            map.duplicateTopLayerExample(1)
            assertThat(map.hasMapped(Point3D(0, 0, 1))).isTrue()
        }
    }

    @Nested
    inner class MinimumX {
        @Test
        fun zeroValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0, 1, 0), TestMapTile(1))
            assertThat(map.getMinX()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(-5, 1, 0), TestMapTile(1))
            assertThat(map.getMinX()).isEqualTo(-5)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 1, 0), TestMapTile(1))
            assertThat(map.getMinX()).isEqualTo(57)
        }

        @Test
        fun multipleValuesShouldReturnSmallest() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 1, 0), TestMapTile(1))
            map.addExampleTile(Point3D(-6, 1, 0), TestMapTile(1))
            map.addExampleTile(Point3D(12, 1, 0), TestMapTile(1))
            assertThat(map.getMinX()).isEqualTo(-6)
        }

        @Test
        @DisplayName("Minimum X filters only where z = 0 so if there are no points on that plane, it returns null.")
        fun noPointsOnStartingPlane() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 1, 5), TestMapTile(1))
            map.addExampleTile(Point3D(-6, 1, -1), TestMapTile(1))
            map.addExampleTile(Point3D(12, 1, 1), TestMapTile(1))
            assertThat(map.getMinX()).isNull()
        }
    }

    @Nested
    inner class MaximumX {
        @Test
        fun zeroValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0, 1, 0), TestMapTile(1))
            assertThat(map.getMaxX()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(-5, 1, 0), TestMapTile(1))
            assertThat(map.getMaxX()).isEqualTo(-5)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 1, 0), TestMapTile(1))
            assertThat(map.getMaxX()).isEqualTo(57)
        }

        @Test
        fun multipleValuesShouldReturnLargest() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 1, 0), TestMapTile(1))
            map.addExampleTile(Point3D(-6, 1, 0), TestMapTile(1))
            map.addExampleTile(Point3D(12, 1, 0), TestMapTile(1))
            assertThat(map.getMaxX()).isEqualTo(57)
        }

        @Test
        @DisplayName("Minimum X filters only where z = 0 so if there are no points on that plane, it returns null.")
        fun noPointsOnStartingPlane() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 1, 5), TestMapTile(1))
            map.addExampleTile(Point3D(-6, 1, -1), TestMapTile(1))
            map.addExampleTile(Point3D(12, 1, 1), TestMapTile(1))
            assertThat(map.getMaxX()).isNull()
        }
    }

    @Nested
    inner class MinimumY {
        @Test
        fun zeroValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0, 0, 0), TestMapTile(1))
            assertThat(map.getMinY()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(-5, -6, 0), TestMapTile(1))
            assertThat(map.getMinY()).isEqualTo(-6)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 4, 0), TestMapTile(1))
            assertThat(map.getMinY()).isEqualTo(4)
        }

        @Test
        fun multipleValuesShouldReturnSmallest() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 4, 0), TestMapTile(1))
            map.addExampleTile(Point3D(-6, 23, 0), TestMapTile(1))
            map.addExampleTile(Point3D(12, -5, 0), TestMapTile(1))
            assertThat(map.getMinY()).isEqualTo(-5)
        }
    }

    @Nested
    inner class MaximumY {
        @Test
        fun zeroValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0, 0, 0), TestMapTile(1))
            assertThat(map.getMaxY()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(-5, -6, 0), TestMapTile(1))
            assertThat(map.getMaxY()).isEqualTo(-6)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 4, 0), TestMapTile(1))
            assertThat(map.getMaxY()).isEqualTo(4)
        }

        @Test
        fun multipleValuesShouldReturnLargest() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 4, 0), TestMapTile(1))
            map.addExampleTile(Point3D(-6, 23, 0), TestMapTile(1))
            map.addExampleTile(Point3D(12, -5, 0), TestMapTile(1))
            assertThat(map.getMaxY()).isEqualTo(23)
        }
    }

    @Nested
    inner class MinimumZ {
        @Test
        fun zeroValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0, 0, 0), TestMapTile(1))
            assertThat(map.getMinZ()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(-5, -6, -10), TestMapTile(1))
            assertThat(map.getMinZ()).isEqualTo(-10)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 4, 12), TestMapTile(1))
            assertThat(map.getMinZ()).isEqualTo(12)
        }

        @Test
        fun multipleValuesShouldReturnSmallest() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 4, 5), TestMapTile(1))
            map.addExampleTile(Point3D(-6, 23, -1), TestMapTile(1))
            map.addExampleTile(Point3D(12, -5, 3), TestMapTile(1))
            assertThat(map.getMinZ()).isEqualTo(-1)
        }
    }

    @Nested
    inner class MaximumZ {
        @Test
        fun zeroValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0, 0, 0), TestMapTile(1))
            assertThat(map.getMaxZ()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(-5, -6, -12), TestMapTile(1))
            assertThat(map.getMaxZ()).isEqualTo(-12)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 4, 56), TestMapTile(1))
            assertThat(map.getMaxZ()).isEqualTo(56)
        }

        @Test
        fun multipleValuesShouldReturnLargest() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(57, 4, 1), TestMapTile(1))
            map.addExampleTile(Point3D(-6, 23, 25), TestMapTile(1))
            map.addExampleTile(Point3D(12, -5, -4), TestMapTile(1))
            assertThat(map.getMaxZ()).isEqualTo(25)
        }
    }

    @Nested
    inner class ToStringTest {
        @Test
        fun empty() {
            assertThat(TestAdventMap3D().toString()).isEqualTo(" \n")
        }

        @Test
        fun onlyValuesOnXAxis() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(1,0,0), TestMapTile(1))
            map.addExampleTile(Point3D(2,0,0), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0 1 0\n")
        }

        @Test
        fun onlyValuesOnYAxis() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(0,1,0), TestMapTile(1))
            map.addExampleTile(Point3D(0,2,0), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0\n1\n0\n")
        }

        @Test
        fun onlyValuesOnZAxis() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(0,0,1), TestMapTile(1))
            map.addExampleTile(Point3D(0,0,2), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0\n\n1\n\n0\n")
        }

        @Test
        fun valuesOnXAndYAxes() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(1,0,0), TestMapTile(1))
            map.addExampleTile(Point3D(2,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(0,1,0), TestMapTile(0))
            map.addExampleTile(Point3D(1,1,0), TestMapTile(1))
            map.addExampleTile(Point3D(2,1,0), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0 1 0\n0 1 0\n")
        }

        @Test
        fun missingCoordinatesBetweenExistingOnes() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(2,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(0,1,0), TestMapTile(0))
            map.addExampleTile(Point3D(2,1,0), TestMapTile(0))
            assertThat(map.toString()).isEqualTo("0   0\n0   0\n")
        }
    }

    @Nested
    inner class ToStringTopLayer {
        @Test
        fun empty() {
            assertThat(TestAdventMap3D().toStringTopLevel()).isEqualTo(" \n")
        }

        @Test
        fun onlyValuesOnXAxis() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(1,0,0), TestMapTile(1))
            map.addExampleTile(Point3D(2,0,0), TestMapTile(0))
            assertThat(map.toStringTopLevel()).isEqualTo("0 1 0\n")
        }

        @Test
        fun onlyValuesOnYAxis() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(0,1,0), TestMapTile(1))
            map.addExampleTile(Point3D(0,2,0), TestMapTile(0))
            assertThat(map.toStringTopLevel()).isEqualTo("0\n1\n0\n")
        }

        @Test
        fun valuesOnBothAxes() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(1,0,0), TestMapTile(1))
            map.addExampleTile(Point3D(2,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(0,1,0), TestMapTile(0))
            map.addExampleTile(Point3D(1,1,0), TestMapTile(1))
            map.addExampleTile(Point3D(2,1,0), TestMapTile(0))
            assertThat(map.toStringTopLevel()).isEqualTo("0 1 0\n0 1 0\n")
        }

        @Test
        fun missingCoordinatesBetweenExistingOnes() {
            val map = TestAdventMap3D()
            map.addExampleTile(Point3D(0,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(2,0,0), TestMapTile(0))
            map.addExampleTile(Point3D(0,1,0), TestMapTile(0))
            map.addExampleTile(Point3D(2,1,0), TestMapTile(0))
            assertThat(map.toStringTopLevel()).isEqualTo("0   0\n0   0\n")
        }
    }

    private inner class TestAdventMap3D : AdventMap3D<TestMapTile>() {
        fun addExampleTile(pos: Point3D, default: TestMapTile) = addTile(pos, default)
        fun getExampleTile(pos: Point3D) = getTile(pos)
        fun hasMapped(pos: Point3D) = hasRecorded(pos)
        fun filterPointsExample(positions: Set<Point3D>) = filterPoints(positions)
        fun filterTilesExample(predicate: (TestMapTile) -> Boolean) = filterTiles(predicate)
        fun getAdjacentTiles(positions: Set<Point3D>) = adjacentTiles(positions)
        fun duplicateTopLayerExample(n: Int) = duplicateTopLayer(n)
        fun getMinX() = xMin()
        fun getMaxX() = xMax()
        fun getMinY() = yMin()
        fun getMaxY() = yMax()
        fun getMinZ() = zMin()
        fun getMaxZ() = zMax()
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