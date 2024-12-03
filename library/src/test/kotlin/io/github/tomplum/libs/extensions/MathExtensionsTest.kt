package io.github.tomplum.libs.extensions

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.libs.math.point.Point2D
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MathExtensionsTest {
    @Nested
    inner class PolygonArea {
        @Test
        fun regularSquare() {
            val points = listOf(Point2D(0, 0), Point2D(0, 10), Point2D(10, 10), Point2D(10, 0))
            val area = points.area()
            assertThat(area).isEqualTo(100)
        }
    }
}