package io.github.tomplum.libs.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotEqualTo
import assertk.assertions.isTrue
import io.github.tomplum.libs.math.Point2D
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class AdventMap2DTest {
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

    @Test
    @DisplayName("Given a Map with some tiles, when getting a tile that exists, then it should return that tile")
    fun getTilePositive() {
        val map = TestAdventMap2D()
        map.addExampleTile(Point2D(3, 4), TestMapTile(4))
        map.addExampleTile(Point2D(2, 12), TestMapTile(1))
        assertThat(map.getExampleTile(Point2D(2, 12), TestMapTile(0)).toString()).isEqualTo("1")
    }

    @Test
    @DisplayName("Given a Map with some tiles, when getting a tile that doesn't exist, then it should return the default")
    fun getTileNegative() {
        val map = TestAdventMap2D()
        map.addExampleTile(Point2D(0, 5), TestMapTile(1))
        assertThat(map.getExampleTile(Point2D(2, 12), TestMapTile(0)).toString()).isEqualTo("0")
    }

    @Test
    fun getTileThatDoesntExist() {
        val e = assertThrows<IllegalArgumentException> { TestAdventMap2D().getExampleTile(Point2D(0, 0)) }
        assertThat(e.message).isEqualTo("Map does not contain tile at (0, 0)")
    }

    @Test
    fun hasRecordedPositive() {
        val map = TestAdventMap2D()
        map.addExampleTile(Point2D(3, 4), TestMapTile(4))
        assertThat(map.hasMapped(Point2D(3, 4))).isTrue()
    }

    @Test
    fun hasRecordedNegative() {
        val map = TestAdventMap2D()
        map.addExampleTile(Point2D(3, 4), TestMapTile(4))
        assertThat(map.hasMapped(Point2D(5, 8))).isFalse()
    }

    @Test
    fun hasTilePositive() {
        val map = TestAdventMap2D()
        val tile = TestMapTile(4)
        map.addExampleTile(Point2D(3, 4), tile)
        assertThat(map.hasExampleTile(tile)).isTrue()
    }

    @Test
    fun hasTileNegative() {
        val map = TestAdventMap2D()
        map.addExampleTile(Point2D(3, 4), TestMapTile(4))
        assertThat(map.hasExampleTile(TestMapTile(1))).isFalse()
    }

    @Test
    fun tileQuantityWhenPositive() {
        val map = TestAdventMap2D()
        map.addExampleTile(Point2D(3, 4), TestMapTile(4))
        map.addExampleTile(Point2D(0, 0), TestMapTile(12))
        assertThat(map.getTileQuantity()).isEqualTo(2)
    }

    @Test
    fun tileQuantityWhenEmpty() {
        assertThat(TestAdventMap2D().getTileQuantity()).isEqualTo(0)
    }

    @Test
    fun filterTiles() {
        val map = TestAdventMap2D()
        map.addExampleTile(Point2D(3, 4), TestMapTile(4))
        val targetTile = TestMapTile(12)
        map.addExampleTile(Point2D(0, 0), targetTile)
        assertThat(map.getTiles { it.isMyTestValue() }).isEqualTo(mapOf(Pair(Point2D(0, 0), targetTile)))
    }

    @Test
    fun filterPoints() {
        val map = TestAdventMap2D()
        for (y in 0..100) {
            for (x in 0..100) {
                map.addExampleTile(Point2D(x, y), TestMapTile(y))
            }
        }
        val tiles = map.getTilesAtPositions(setOf(Point2D(17, 4), Point2D(56, 86), Point2D(100, 100), Point2D(25, 99)))
        assertThat(tiles).isEqualTo(mapOf(Pair(Point2D(17,4), TestMapTile(4)), Pair(Point2D(56,86), TestMapTile(86)),
                Pair(Point2D(25,99), TestMapTile(99)), Pair(Point2D(100,100), TestMapTile(100))))
    }

    @Test
    fun filterPointsShouldIgnoreUnrecordedTiles() {
        val map = TestAdventMap2D()
        for (y in 0..10) {
            for (x in 0..10) {
                map.addExampleTile(Point2D(x, y), TestMapTile(y))
            }
        }
        val tiles = map.getTilesAtPositions(setOf(Point2D(17, 4), Point2D(1, 2)))
        assertThat(tiles).isEqualTo(mapOf(Pair(Point2D(1,2), TestMapTile(2))))
    }

    @Test
    fun reset() {
        val map = TestAdventMap2D()
        map.addExampleTile(Point2D(12, 14), TestMapTile(1))
        map.reset()
        assertThat(map.getTileQuantity()).isEqualTo(0)
    }

    @Test
    fun equalityTestWhenEmpty() {
        assertThat(TestAdventMap2D()).isEqualTo(TestAdventMap2D())
    }

    @Test
    fun equalityTestWithDataPositive() {
        val first = TestAdventMap2D()
        first.addExampleTile(Point2D(4,6), TestMapTile(3))
        val second = TestAdventMap2D()
        second.addExampleTile(Point2D(4,6), TestMapTile(3))
        assertThat(first).isEqualTo(second)
    }

    @Test
    fun equalityTestNegative() {
        val first = TestAdventMap2D()
        first.addExampleTile(Point2D(3,6), TestMapTile(7))
        val second = TestAdventMap2D()
        second.addExampleTile(Point2D(4,6), TestMapTile(3))
        assertThat(first).isNotEqualTo(second)
    }

    private inner class TestAdventMap2D : AdventMap2D<TestMapTile>() {
        fun getTileQuantity() = tileQuantity()
        fun addExampleTile(pos: Point2D, default: TestMapTile) = addTile(pos, default)
        fun getExampleTile(pos: Point2D) = getTile(pos)
        fun getExampleTile(pos: Point2D, default: TestMapTile) = getTile(pos, default)
        fun hasMapped(pos: Point2D) = hasRecorded(pos)
        fun hasExampleTile(tile: TestMapTile) = hasTile(tile)
        fun getTilesAtPositions(positions: Set<Point2D>) = filterPoints(positions)
        fun getTiles(predicate: (TestMapTile) -> Boolean) = filterTiles(predicate)
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