package io.github.tomplum.libs.extensions

import assertk.assertThat
import assertk.assertions.containsAtLeast
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

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
        fun emptyList() {
            assertThat(emptyList<Int>().product()).isEqualTo(0)
        }

        @Test
        fun severalIntegers() {
            assertThat(listOf(2, 5, 10).product()).isEqualTo(100)
        }

        @Test
        fun singleInteger() {
            assertThat(listOf(4).product()).isEqualTo(4)
        }

        @Test
        fun negativeIntegers() {
            assertThat(listOf(3, 10, 2, -1).product()).isEqualTo(-60)
        }

        @Test
        fun longs() {
            assertThat(listOf(5L, 2L).product()).isEqualTo(10L)
        }

        @Test
        fun floats() {
            assertThat(listOf(10f, 12f).product()).isEqualTo(120f)
        }

        @Test
        fun doubles() {
            assertThat(listOf(22.2, 1.2).product()).isEqualTo(26.639999999999997)
        }

        @Test
        fun unsupportedNumber() {
            val e = assertThrows<UnsupportedOperationException> {
                listOf(BigDecimal(10)).product()
            }
            assertThat(e.message).isEqualTo("Unsupported number type: BigDecimal")
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

    @Nested
    inner class LowestCommonMultiple {
        @Test
        fun twoValues() {
            val values = listOf(4L, 2L)
            val lcm = values.lcm()
            assertThat(lcm).isEqualTo(4)
        }

        @Test
        fun oneValue() {
            val values = listOf(10L)
            val lcm = values.lcm()
            assertThat(lcm).isEqualTo(10)
        }

        @Test
        fun emptyList() {
            val values = listOf<Long>()
            val e = assertThrows<IllegalArgumentException> { values.lcm()  }
            assertThat(e.message).isEqualTo("Cannot find the LCM of an empty list.")
        }
    }

    @Nested
    inner class DistinctPairs {
        @Test
        fun originalListHasDistinctValues() {
            val values = listOf(1, 2 ,3)
            val pairs = values.distinctPairs()
            assertThat(pairs).isEqualTo(listOf(Pair(1, 2), Pair(1, 3), Pair(2, 3)))
        }

        @Test
        fun originalListHasDuplicateValues() {
            val values = listOf(1, 1, 2 ,3)
            val pairs = values.distinctPairs()
            assertThat(pairs).isEqualTo(listOf(Pair(1, 1), Pair(1, 2), Pair(1, 3), Pair(1, 2), Pair(1, 3), Pair(2, 3)))
        }
    }

    @Nested
    inner class Split {
        @Test
        fun hasSeveralMatches() {
            val collection = listOf(".", "O", ".", ".", "", "O", "." ,".", ".", "", "O", "O", ".", ".")
            val result = collection.split { value -> value.isBlank() }
            assertThat(result).isEqualTo(listOf(
                listOf(".", "O", ".", "."),
                listOf("O", "." ,".", "."),
                listOf("O", "O", ".", ".")
            ))
        }

        @Test
        fun noMatches() {
            val collection = listOf(".", "O", ".", ".", "", "O", "." ,".", ".", "", "O", "O", ".", ".")
            val result = collection.split { value -> value == "something that doesn't match" }
            assertThat(result).isEqualTo(listOf(collection))
        }
    }
}
