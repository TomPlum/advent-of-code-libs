package io.github.tomplum.libs.math.point

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Point3DTest {
    @Nested
    inner class PlanarAdjacentTo {
        @Test
        fun isPlanarAdjacentToTargetOnRight() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(6,6,0))).isTrue()
        }

        @Test
        fun isPlanarAdjacentToTargetOnBottomRight() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(6,5,0))).isTrue()
        }

        @Test
        fun isPlanarAdjacentToTargetOnBottom() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(5,5,0))).isTrue()
        }

        @Test
        fun isPlanarAdjacentToTargetOnBottomLeft() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(4,5,0))).isTrue()
        }

        @Test
        fun isPlanarAdjacentToTargetOnLeft() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(4,6,0))).isTrue()
        }

        @Test
        fun isPlanarAdjacentToTargetTopLeft() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(4,7,0))).isTrue()
        }

        @Test
        fun isPlanarAdjacentToTargetTop() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(5,7,0))).isTrue()
        }

        @Test
        fun isNotAdjacent() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(4,4,0))).isFalse()
        }

        @Test
        fun samePointsAreNotAdjacent() {
            assertThat(Point3D(5,6,0).isPlanarAdjacentTo(Point3D(5,6,0))).isFalse()
        }
    }

    @Nested
    inner class OrthogonallyPlanarAdjacent {
        @Test
        fun adjacentPoints() {
            assertThat(Point3D(0,0,0).planarAdjacentPoints()).isEqualTo(listOf(Point3D(0,1,0), Point3D(1,0,0), Point3D(0,-1,0), Point3D(-1,0,0)))
        }
    }

    @Nested
    inner class Adjacent {
        @Test
        fun example() {
            assertThat(Point3D(1,1,0).adjacent()).containsOnly(
                Point3D(0,0,0), Point3D(1,0,0), Point3D(2,0,0), Point3D(0,1,0), Point3D(2,1,0), Point3D(0,2,0),
                Point3D(1,2,0), Point3D(2,2,0), Point3D(0,0,-1), Point3D(1,0,-1), Point3D(2,0,-1), Point3D(0,1,-1),
                Point3D(1,1,-1), Point3D(2,1,-1), Point3D(0,2,-1), Point3D(1,2,-1), Point3D(2,2,-1), Point3D(0,0,1),
                Point3D(1,0,1), Point3D(2,0,1), Point3D(0,1,1), Point3D(1,1,1), Point3D(2,1,1), Point3D(0,2,1),
                Point3D(1,2,1), Point3D(2,2,1)
            )
        }
    }

    @Nested
    inner class DistanceFromAxisX {
        @Test
        fun pointOnAxisX() {
            assertThat(Point3D(0, 12, 4).distanceFromAxisX()).isEqualTo(0)
        }

        @Test
        fun pointHasPositiveX() {
            assertThat(Point3D(4, 5, -6).distanceFromAxisX()).isEqualTo(4)
        }

        @Test
        fun pointHasNegativeX() {
            assertThat(Point3D(-12, 3, 0).distanceFromAxisX()).isEqualTo(12)
        }
    }

    @Nested
    inner class DistanceFromAxisY {
        @Test
        fun pointOnAxisY() {
            assertThat(Point3D(0, 12, -14).distanceFromAxisY()).isEqualTo(12)
        }

        @Test
        fun pointHasPositiveY() {
            assertThat(Point3D(4, 5, 1).distanceFromAxisY()).isEqualTo(5)
        }

        @Test
        fun pointHasNegativeY() {
            assertThat(Point3D(12, -3, 2).distanceFromAxisY()).isEqualTo(3)
        }
    }

    @Nested
    inner class Getters {
        @Test
        fun getX() {
            assertThat(Point3D(1, 2, 3).x).isEqualTo(1)
        }

        @Test
        fun getY() {
            assertThat(Point3D(1, 2, 3).y).isEqualTo(2)
        }

        @Test
        fun getZ() {
            assertThat(Point3D(1, 2, 3).z).isEqualTo(3)
        }
    }

    @Test
    fun toStringTest() {
        assertThat(Point3D(4, 2, 11).toString()).isEqualTo("(4, 2, 11)")
    }
}