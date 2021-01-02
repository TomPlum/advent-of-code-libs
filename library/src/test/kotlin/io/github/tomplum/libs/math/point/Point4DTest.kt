package io.github.tomplum.libs.math.point

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Point4DTest {
    @Nested
    inner class Adjacent {
        @Test
        fun returnsEightyPoints() {
            assertThat(Point4D(0,0,0,0).adjacent().size).isEqualTo(80)
        }
    }
}