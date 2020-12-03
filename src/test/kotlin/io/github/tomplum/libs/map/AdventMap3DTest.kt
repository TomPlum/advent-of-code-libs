package io.github.tomplum.libs.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.github.tomplum.libs.math.Point3D
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class AdventMap3DTest {
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

    @Test
    fun filterTiles() {
        val map = TestAdventMap3D()
        map.addExampleTile(Point3D(3, 4, 1), TestMapTile(4))
        val targetTile = TestMapTile(12)
        map.addExampleTile(Point3D(0, 0, 0), targetTile)
        assertThat(map.getTiles { it.isMyTestValue() }).isEqualTo(mapOf(Pair(Point3D(0, 0, 0), targetTile)))
    }

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
        val tiles = map.getTilesAtPositions(setOf(Point3D(17, 4, 0), Point3D(56, 86, 1), Point3D(100, 100, 2), Point3D(25, 99, 3)))
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
        val tiles = map.getTilesAtPositions(setOf(Point3D(17, 4, 3), Point3D(1, 2, 0)))
        assertThat(tiles).isEqualTo(mapOf(Pair(Point3D(1,2,0), TestMapTile(2))))
    }

    private inner class TestAdventMap3D : AdventMap3D<TestMapTile>() {
        fun addExampleTile(pos: Point3D, default: TestMapTile) = addTile(pos, default)
        fun getExampleTile(pos: Point3D) = getTile(pos)
        fun hasMapped(pos: Point3D) = hasRecorded(pos)
        fun getTilesAtPositions(positions: Set<Point3D>) = filterPoints(positions)
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