package io.github.tomplum.libs.extensions

import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.containsAtLeast
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CollectionExtensionsTest {
    @Nested
    inner class CartesianProductSelf {
        @Test
        fun twoElements() {
            assertThat(listOf(0, 1).cartesianProductQuadratic()).containsAtLeast(
                Pair(0, 0),
                Pair(1, 1),
                Pair(0, 1),
                Pair(1, 0)
            )
        }

        @Test
        fun emptySet() {
            assertThat(emptyList<Int>().cartesianProductQuadratic()).isEmpty()
        }
    }

    @Nested
    inner class CartesianProductOther {
        @Test
        fun twoElementsInBoth() {
            val cartesianProduct = listOf(0, 1).cartesianProduct(listOf(2, 3))
            assertThat(cartesianProduct).containsAtLeast(Pair(0, 2), Pair(0, 3), Pair(1, 2), Pair(1, 3))
        }

        @Test
        fun emptySets() {
            assertThat(emptyList<Int>().cartesianProduct(emptyList<Int>())).isEmpty()
        }
    }

    @Nested
    inner class CartesianProductTripleOther {
        @Test
        fun example() {
            val cartesianProduct = listOf(-1, -2).cartesianProductCubic(listOf(1, 2), listOf(0))
            assertThat(cartesianProduct).containsAtLeast(
                Triple(-1, 1, 0),
                Triple(-1, 2, 0),
                Triple(-2, 1, 0),
                Triple(-2, 2, 0)
            )
        }

        @Test
        fun emptySets() {
            assertThat(emptyList<Int>().cartesianProductCubic(emptyList(), emptyList())).isEmpty()
        }
    }

    @Nested
    inner class ListProduct {
        @Test
        fun empty() {
            assertThat(emptyList<Int>().product()).isEqualTo(0)
        }

        @Test
        fun severalElements() {
            assertThat(listOf(2, 5, 10).product()).isEqualTo(100)
        }

        @Test
        fun negativeIntegers() {
            assertThat(listOf(3, 10, 2, -1).product()).isEqualTo(-60)
        }
    }

    @Nested
    inner class ToVerticalLists {
        @Test
        fun simpleSingleSpacedIntegerExample() {
            val verticalIntegerLists = listOf("1 2", "6 9", "5 2")

            val verticalLists = verticalIntegerLists.toVerticalLists { line ->
                line.split(' ')
                    .map { value -> value.trim().toInt() }
                    .let { values -> Pair(values[0], values[1]) }
            }

            assertThat(verticalLists).isEqualTo(Pair(
                mutableListOf(1, 6, 5),
                mutableListOf(2, 9, 2)
            ))
        }

        @Test
        fun moreComplexStringExample() {
            val verticalStringLists = listOf("anc8ns -> >asldk%", "lac90sckla -> *90pcasH")

            val verticalLists = verticalStringLists.toVerticalLists { line ->
                line.split("->")
                    .map { value -> value.trim() }
                    .let { values -> Pair(values[0], values[1]) }
            }

            assertThat(verticalLists).isEqualTo(Pair(
                mutableListOf("anc8ns", "lac90sckla"),
                mutableListOf(">asldk%", "*90pcasH")
            ))
        }
    }

    @Nested
    inner class BinaryIntArrayToDecimal {
        @Test
        fun example() {
            val binary = "000000000000000000000000000000001011".toBinary()
            val decimal = binary.toDecimal()
            assertThat(decimal).isEqualTo(11)
        }

        @Test
        fun exampleTwo() {
            val binary = "000000000000000000000000000001001001".toBinary()
            val decimal = binary.toDecimal()
            assertThat(decimal).isEqualTo(73)
        }

        @Test
        fun exampleThree() {
            val binary = "000000000000000000000000000001100101".toBinary()
            val decimal = binary.toDecimal()
            assertThat(decimal).isEqualTo(101)
        }

        @Test
        fun exampleFour() {
            val binary = "000000000000000000000000000000000000".toBinary()
            val decimal = binary.toDecimal()
            assertThat(decimal).isEqualTo(0)
        }

        private fun String.toBinary(): IntArray {
            val binary = IntArray(36)
            this.forEachIndexed { i, value -> binary[i] = value.toString().toInt() }
            return binary
        }
    }
}
