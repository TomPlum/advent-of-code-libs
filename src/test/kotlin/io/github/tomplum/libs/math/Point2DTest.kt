package io.github.tomplum.libs.math

import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Point2DTest {

    @Nested
    inner class OrthogonallyAdjacent {
        @Test
        fun origin() {
            val point = Point2D(0, 0)
            val adjacent = point.orthogonallyAdjacent()
            assertThat(adjacent).containsAll(Point2D(0,1), Point2D(1,0), Point2D(0,-1), Point2D(-1,0))
        }
    }


    @Nested
    inner class AdjacentTo {
        @Test
        fun origin() {
            val point = Point2D(0, 0)
            val adjacent = point.adjacent()
            assertThat(adjacent).containsAll(
                Point2D(0,1), Point2D(1,0), Point2D(0,-1), Point2D(-1,0), Point2D(-1,1), Point2D(1,1), Point2D(1,-1), Point2D(-1,-1)
            )
        }
    }

    @Nested
    inner class ManhattanDistance {
        @Test
        fun targetToTheRight() {
            assertThat(Point2D(0,0).distanceBetween(Point2D(0,5))).isEqualTo(5)
        }

        @Test
        fun targetIsBelow() {
            assertThat(Point2D(0,0).distanceBetween(Point2D(0,-4))).isEqualTo(4)
        }

        @Test
        fun targetToTheLeft() {
            assertThat(Point2D(0,0).distanceBetween(Point2D(-12,0))).isEqualTo(12)
        }

        @Test
        fun targetIsAbove() {
            assertThat(Point2D(0,0).distanceBetween(Point2D(0,8))).isEqualTo(8)
        }

        @Test
        fun targetDiagonalTopRight() {
            assertThat(Point2D(0,0).distanceBetween(Point2D(3,3))).isEqualTo(6)
        }

        @Test
        fun targetDiagonalBottomRight() {
            assertThat(Point2D(0,0).distanceBetween(Point2D(4,-4))).isEqualTo(8)
        }

        @Test
        fun targetDiagonalBottomLeft() {
            assertThat(Point2D(0,0).distanceBetween(Point2D(-6,-6))).isEqualTo(12)
        }

        @Test
        fun targetDiagonalTopLeft() {
            assertThat(Point2D(0,0).distanceBetween(Point2D(-12,12))).isEqualTo(24)
        }
    }

    @Nested
    inner class AngleBetween {
        @ParameterizedTest
        @CsvSource(value = ["3,3,3,2", "3,3,3,0", "0,5,0,2", "12,15,12,0"], delimiter = ',')
        @DisplayName("Coordinates that have the same x-ordinate and a lesser y-ordinate should be vertical at 0deg")
        fun angleBetweenWhenTargetSectorIsVerticallyAbove(x1: Int, y1: Int, x2: Int, y2: Int) {
            assertThat(Point2D(x1, y1).angleBetween(Point2D(x2, y2))).isEqualTo(0.0)
        }

        @ParameterizedTest
        @CsvSource(value = ["0,5,5,0", "3,5,4,4"], delimiter = ',')
        @DisplayName("Coordinates that are diagonal should be 45deg when target is top right")
        fun angleBetweenWhenSectorsAreExactlyDiagonalTopRight(x1: Int, y1: Int, x2: Int, y2: Int) {
            assertThat(Point2D(x1, y1).angleBetween(Point2D(x2, y2))).isEqualTo(45.0)
        }

        @ParameterizedTest
        @CsvSource(value = ["4,5,5,5", "5,5,6,5"], delimiter = ',')
        @DisplayName("Coordinates that have the same y-ordinate and a greater x-ordinate should be horizontal at 90deg")
        fun angleBetweenWhenSectorsAreHorizontallyPerpendicularToTheRight(x1: Int, y1: Int, x2: Int, y2: Int) {
            assertThat(Point2D(x1, y1).angleBetween(Point2D(x2, y2))).isEqualTo(90.0)
        }

        @ParameterizedTest
        @CsvSource(value = ["0,0,1,1", "0,0,2,2", "3,3,5,5"], delimiter = ',')
        @DisplayName("Coordinates that are diagonal should be 135deg when target is bottom right")
        fun angleBetweenWhenSectorsAreExactlyDiagonalBottomRight(x1: Int, y1: Int, x2: Int, y2: Int) {
            assertThat(Point2D(x1, y1).angleBetween(Point2D(x2, y2))).isEqualTo(135.0)
        }

        @ParameterizedTest
        @CsvSource(value = ["3,3,3,7", "3,3,3,21", "0,5,0,10", "12,15,12,16"], delimiter = ',')
        @DisplayName("Coordinates that have the same x-ordinate and a greater y-ordinate should be vertical at -90deg")
        fun angleBetweenWhenTargetSectorIsVerticallyBelow(x1: Int, y1: Int, x2: Int, y2: Int) {
            assertThat(Point2D(x1, y1).angleBetween(Point2D(x2, y2))).isEqualTo(180.0)
        }

        @ParameterizedTest
        @CsvSource(value = ["1,1,0,2", "5,5,4,6", "10,10,8,12"], delimiter = ',')
        @DisplayName("Coordinates that are diagonal should be 225deg when target is bottom left")
        fun angleBetweenWhenSectorsAreExactlyDiagonalBottomLeft(x1: Int, y1: Int, x2: Int, y2: Int) {
            assertThat(Point2D(x1, y1).angleBetween(Point2D(x2, y2))).isEqualTo(225.0)
        }

        @ParameterizedTest
        @CsvSource(value = ["4,5,3,5", "5,5,4,5", "10,1,3,1"], delimiter = ',')
        @DisplayName("Coordinates that have the same y-ordinate and a lesser x-ordinate should be horizontal at 270deg")
        fun angleBetweenWhenSectorsAreHorizontallyPerpendicularToTheLeft(x1: Int, y1: Int, x2: Int, y2: Int) {
            assertThat(Point2D(x1, y1).angleBetween(Point2D(x2, y2))).isEqualTo(270.0)
        }

        @ParameterizedTest
        @CsvSource(value = ["1,1,0,0", "5,5,4,4", "10,8,8,6"], delimiter = ',')
        @DisplayName("Coordinates that are diagonal should be 315deg when target is top left")
        fun angleBetweenWhenSectorsAreExactlyDiagonalTopLeft(x1: Int, y1: Int, x2: Int, y2: Int) {
            assertThat(Point2D(x1, y1).angleBetween(Point2D(x2, y2))).isEqualTo(315.0)
        }
    }

    @Nested
    inner class Shift {
        @Test
        fun shiftUp() {
            assertThat(Point2D(0,0).shift(Direction.UP)).isEqualTo(Point2D(0,1))
        }

        @Test
        fun shiftRight() {
            assertThat(Point2D(0,0).shift(Direction.RIGHT)).isEqualTo(Point2D(1,0))
        }

        @Test
        fun shiftDown() {
            assertThat(Point2D(0,0).shift(Direction.DOWN)).isEqualTo(Point2D(0,-1))
        }

        @Test
        fun shiftLeft() {
            assertThat(Point2D(0,0).shift(Direction.LEFT)).isEqualTo(Point2D(-1,0))
        }

        @Test
        fun shiftTopRight() {
            assertThat(Point2D(0,0).shift(Direction.TOP_RIGHT)).isEqualTo(Point2D(1,1))
        }

        @Test
        fun shiftBottomRight() {
            assertThat(Point2D(0,0).shift(Direction.BOTTOM_RIGHT)).isEqualTo(Point2D(1,-1))
        }

        @Test
        fun shiftBottomLeft() {
            assertThat(Point2D(0,0).shift(Direction.BOTTOM_LEFT)).isEqualTo(Point2D(-1,-1))
        }

        @Test
        fun shiftTopLeft() {
            assertThat(Point2D(0,0).shift(Direction.TOP_LEFT)).isEqualTo(Point2D(-1,1))
        }
    }

    @Nested
    inner class OrthogonallyAdjacentTo {
        @Test
        fun isAdjacentToTargetOnRight() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(6, 6))).isTrue()
        }

        @Test
        fun isAdjacentToTargetOnBottomRight() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(6, 5))).isTrue()
        }

        @Test
        fun isAdjacentToTargetOnBottom() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(5, 5))).isTrue()
        }

        @Test
        fun isAdjacentToTargetOnBottomLeft() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(4, 5))).isTrue()
        }

        @Test
        fun isAdjacentToTargetOnLeft() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(4, 6))).isTrue()
        }

        @Test
        fun isAdjacentToTargetTopLeft() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(4, 7))).isTrue()
        }

        @Test
        fun isAdjacentToTargetTop() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(5, 7))).isTrue()
        }

        @Test
        fun isNotAdjacent() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(4, 4))).isFalse()
        }

        @Test
        fun samePointsAreNotAdjacent() {
            assertThat(Point2D(5, 6).isOrthogonallyAdjacentTo(Point2D(5, 6))).isFalse()
        }
    }

    @Nested
    inner class DistanceFromX {
        @Test
        fun pointOnAxisX() {
            assertThat(Point2D(0, 12).distanceFromAxisX()).isEqualTo(0)
        }

        @Test
        fun pointHasPositiveX() {
            assertThat(Point2D(4, 5).distanceFromAxisX()).isEqualTo(4)
        }

        @Test
        fun pointHasNegativeX() {
            assertThat(Point2D(-12, 3).distanceFromAxisX()).isEqualTo(12)
        }
    }

    @Nested
    inner class DistanceFromY {
        @Test
        fun pointOnAxisY() {
            assertThat(Point2D(0, 12).distanceFromAxisY()).isEqualTo(12)
        }

        @Test
        fun pointHasPositiveY() {
            assertThat(Point2D(4, 5).distanceFromAxisY()).isEqualTo(5)
        }

        @Test
        fun pointHasNegativeY() {
            assertThat(Point2D(12, -3).distanceFromAxisY()).isEqualTo(3)
        }
    }

    @Nested
    inner class Equality {
        @ParameterizedTest
        @CsvSource(value = ["1,1,1,1", "2,2,2,2", "-1,-1,-1,-1"])
        @DisplayName("Given two Points that have the same cartesian coordinate, when checking their equality, they should be equal")
        fun equal(x0: Int, y0: Int, x1: Int, y1: Int) {
            assertThat(Point2D(x0, y0)).isEqualTo(Point2D(x1, y1))
        }

        @ParameterizedTest
        @CsvSource(value = ["1,1,0,1", "1,1,1,-1", "0,2,2,0"])
        @DisplayName("Given two Points that have the different cartesian coordinate, when checking their equality, they should not be equal")
        fun differentCoordinates(x0: Int, y0: Int, x1: Int, y1: Int) {
            assertThat(Point2D(x0, y0)).isNotEqualTo(Point2D(x1, y1))
        }

        @Test
        fun differentTypes() {
            assertThat(Point2D(1,1)).isNotEqualTo(Point3D(1,1,1))
        }
    }

}