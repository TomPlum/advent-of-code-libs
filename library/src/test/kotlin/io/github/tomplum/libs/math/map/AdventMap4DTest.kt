package io.github.tomplum.libs.math.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import io.github.tomplum.libs.math.point.Point4D
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class AdventMap4DTest {
    @Nested
    inner class Equality {
        @Test
        fun emptyMapsShouldBeEqual() {
            assertThat(TestAdventMap4D()).isEqualTo(TestAdventMap4D())
        }

        @Test
        fun hasSameData() {
            val first = TestAdventMap4D()
            first.addExampleTile(Point4D(4, 6, 1, 0), TestMapTile(3))
            val second = TestAdventMap4D()
            second.addExampleTile(Point4D(4, 6, 1, 0), TestMapTile(3))
            assertThat(first).isEqualTo(second)
        }

        @Test
        fun hasDifferentData() {
            val first = TestAdventMap4D()
            first.addExampleTile(Point4D(3, 6, 2, 4), TestMapTile(7))
            val second = TestAdventMap4D()
            second.addExampleTile(Point4D(4, 6, 4, 1), TestMapTile(3))
            assertThat(first).isNotEqualTo(second)
        }

        @Test
        fun differentTypes() {
            assertThat(TestAdventMap4D()).isNotEqualTo(mapOf<Int, TestMapTile>())
        }
    }

    @Nested
    inner class HashCode {
        @Test
        fun emptyMap() {
            assertThat(TestAdventMap4D().hashCode()).isEqualTo(TestAdventMap4D().hashCode())
        }

        @Test
        fun hasSameData() {
            val first = TestAdventMap4D()
            first.addExampleTile(Point4D(4, 6, 4, 2), TestMapTile(3))
            val second = TestAdventMap4D()
            second.addExampleTile(Point4D(4, 6, 4, 2), TestMapTile(3))
            assertThat(first.hashCode()).isEqualTo(second.hashCode())
        }
    }

    @Nested
    inner class MinimumX {
        @Test
        fun zeroValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(0, 1, 0, 0), TestMapTile(1))
            assertThat(map.getMinX()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(-5, 1, 0, 0), TestMapTile(1))
            assertThat(map.getMinX()).isEqualTo(-5)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 1, 0, 0), TestMapTile(1))
            assertThat(map.getMinX()).isEqualTo(57)
        }

        @Test
        fun multipleValuesShouldReturnSmallest() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 1, 0 ,0), TestMapTile(1))
            map.addExampleTile(Point4D(-6, 1, 0, 0), TestMapTile(1))
            map.addExampleTile(Point4D(12, 1, 0, 0), TestMapTile(1))
            assertThat(map.getMinX()).isEqualTo(-6)
        }
    }

    @Nested
    inner class MaximumX {
        @Test
        fun zeroValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(0, 1, 0, 0), TestMapTile(1))
            assertThat(map.getMaxX()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(-5, 1, 0, 0), TestMapTile(1))
            assertThat(map.getMaxX()).isEqualTo(-5)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 1, 0, 0), TestMapTile(1))
            assertThat(map.getMaxX()).isEqualTo(57)
        }

        @Test
        fun multipleValuesShouldReturnLargest() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 1, 0, 0), TestMapTile(1))
            map.addExampleTile(Point4D(-6, 1, 0, 0), TestMapTile(1))
            map.addExampleTile(Point4D(12, 1, 0, 0), TestMapTile(1))
            assertThat(map.getMaxX()).isEqualTo(57)
        }
    }

    @Nested
    inner class MinimumY {
        @Test
        fun zeroValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(0, 0, 0, 0), TestMapTile(1))
            assertThat(map.getMinY()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(-5, -6, 0, 0), TestMapTile(1))
            assertThat(map.getMinY()).isEqualTo(-6)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 0, 0), TestMapTile(1))
            assertThat(map.getMinY()).isEqualTo(4)
        }

        @Test
        fun multipleValuesShouldReturnSmallest() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 0, 0), TestMapTile(1))
            map.addExampleTile(Point4D(-6, 23, 0, 0), TestMapTile(1))
            map.addExampleTile(Point4D(12, -5, 0, 0), TestMapTile(1))
            assertThat(map.getMinY()).isEqualTo(-5)
        }
    }

    @Nested
    inner class MaximumY {
        @Test
        fun zeroValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(0, 0, 0, 0), TestMapTile(1))
            assertThat(map.getMaxY()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(-5, -6, 0, 0), TestMapTile(1))
            assertThat(map.getMaxY()).isEqualTo(-6)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 0, 0), TestMapTile(1))
            assertThat(map.getMaxY()).isEqualTo(4)
        }

        @Test
        fun multipleValuesShouldReturnLargest() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 0, 0), TestMapTile(1))
            map.addExampleTile(Point4D(-6, 23, 0, 0), TestMapTile(1))
            map.addExampleTile(Point4D(12, -5, 0, 0), TestMapTile(1))
            assertThat(map.getMaxY()).isEqualTo(23)
        }
    }

    @Nested
    inner class MinimumZ {
        @Test
        fun zeroValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(0, 0, 0, 0), TestMapTile(1))
            assertThat(map.getMinZ()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(-5, -6, -10, 0), TestMapTile(1))
            assertThat(map.getMinZ()).isEqualTo(-10)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 12, 0), TestMapTile(1))
            assertThat(map.getMinZ()).isEqualTo(12)
        }

        @Test
        fun multipleValuesShouldReturnSmallest() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 5, 0), TestMapTile(1))
            map.addExampleTile(Point4D(-6, 23, -1, 0), TestMapTile(1))
            map.addExampleTile(Point4D(12, -5, 3, 0), TestMapTile(1))
            assertThat(map.getMinZ()).isEqualTo(-1)
        }
    }

    @Nested
    inner class MaximumZ {
        @Test
        fun zeroValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(0, 0, 0, 0), TestMapTile(1))
            assertThat(map.getMaxZ()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(-5, -6, -12, 0), TestMapTile(1))
            assertThat(map.getMaxZ()).isEqualTo(-12)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 56, 0), TestMapTile(1))
            assertThat(map.getMaxZ()).isEqualTo(56)
        }

        @Test
        fun multipleValuesShouldReturnLargest() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 1, 0), TestMapTile(1))
            map.addExampleTile(Point4D(-6, 23, 25, 0), TestMapTile(1))
            map.addExampleTile(Point4D(12, -5, -4, 0), TestMapTile(1))
            assertThat(map.getMaxZ()).isEqualTo(25)
        }
    }

    @Nested
    inner class MinimumW {
        @Test
        fun zeroValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(0, 0, 0, 0), TestMapTile(1))
            assertThat(map.getMinW()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(-5, -6, -10, -4), TestMapTile(1))
            assertThat(map.getMinW()).isEqualTo(-4)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 12, 5), TestMapTile(1))
            assertThat(map.getMinW()).isEqualTo(5)
        }

        @Test
        fun multipleValuesShouldReturnSmallest() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 5, 2), TestMapTile(1))
            map.addExampleTile(Point4D(-6, 23, -1, -1), TestMapTile(1))
            map.addExampleTile(Point4D(12, -5, 3, 0), TestMapTile(1))
            assertThat(map.getMinW()).isEqualTo(-1)
        }
    }

    @Nested
    inner class MaximumW {
        @Test
        fun zeroValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(0, 0, 0, 0), TestMapTile(1))
            assertThat(map.getMaxW()).isEqualTo(0)
        }

        @Test
        fun negativeValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(-5, -6, -12, -9), TestMapTile(1))
            assertThat(map.getMaxW()).isEqualTo(-9)
        }

        @Test
        fun positiveValue() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 56, 7), TestMapTile(1))
            assertThat(map.getMaxW()).isEqualTo(7)
        }

        @Test
        fun multipleValuesShouldReturnLargest() {
            val map = TestAdventMap4D()
            map.addExampleTile(Point4D(57, 4, 1, -7), TestMapTile(1))
            map.addExampleTile(Point4D(-6, 23, 25, 25), TestMapTile(1))
            map.addExampleTile(Point4D(12, -5, -4, 4), TestMapTile(1))
            assertThat(map.getMaxW()).isEqualTo(25)
        }
    }

    private inner class TestAdventMap4D : AdventMap4D<TestMapTile>() {
        fun tileQuantityExample() = tileQuantity()
        fun addExampleTile(pos: Point4D, default: TestMapTile) = addTile(pos, default)
        fun getExampleTile(pos: Point4D) = getTile(pos)
        fun getExampleTile(pos: Point4D, default: TestMapTile) = getTile(pos, default)
        fun hasRecordedExample(pos: Point4D) = hasRecorded(pos)
        fun hasTileExample(tile: TestMapTile) = hasTile(tile)
        fun filterPointsExample(positions: Set<Point4D>) = filterPoints(positions)
        fun filterTilesExample(predicate: (TestMapTile) -> Boolean) = filterTiles(predicate)
        fun getAdjacentTiles(positions: Set<Point4D>) = adjacentTiles(positions)
        fun getAdjacentTiles(positions: Set<Point4D>, default: TestMapTile?) = adjacentTiles(positions, default)
        fun getMinX() = xMin()
        fun getMinY() = yMin()
        fun getMaxX() = xMax()
        fun getMaxY() = yMax()
        fun getMinZ() = zMin()
        fun getMaxZ() = zMax()
        fun getMinW() = wMin()
        fun getMaxW() = wMax()
    }

    private inner class TestMapTile(private val data: Int) : MapTile<Int>(data) {
        override fun equals(other: Any?): Boolean {
            if (other !is TestMapTile) return false
            return data == other.data
        }

        override fun hashCode(): Int {
            return Objects.hashCode(data)
        }
    }
}