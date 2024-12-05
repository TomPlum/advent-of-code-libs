package io.github.tomplum.libs.extensions

import kotlin.math.pow

/**
 * Returns the product of all the integers in the given list.
 */
fun List<Int>.product(): Int = if (isNotEmpty()) reduce { product, next -> product * next } else 0

/**
 * Returns the product of all the longs in the given list.
 */
fun List<Long>.product(): Long = if (isNotEmpty()) reduce { product, next -> product * next } else 0

/**
 * Converts the [IntArray] into its decimal equivalent.
 * This assumes the array contains only 1s and 0s.
 * @return The decimal representation.
 */
fun IntArray.toDecimal(): Long = reversed()
    .mapIndexed { i, bit -> if (bit == 1) 2.0.pow(i) else 0 }
    .sumOf { integer -> integer.toLong() }

/**
 * For two sets A and B, the Cartesian product of A and B is denoted by A×B and defined as:
 * A×B = { (a,b) | aϵA and bϵB }
 *
 * Put simply, the Cartesian Product is the multiplication of two sets to form the set of all ordered pairs.
 * This function returns the cartesian product of itself and the given set, meaning A and B are [this] and [other].
 *
 * @see cartesianProductQuadratic for a variant that returns the product of itself.
 */
fun <S, T> List<S>.cartesianProduct(other: List<T>): List<Pair<S, T>> = this.flatMap { value ->
    List(other.size){ i -> Pair(value, other[i]) }
}

/**
 * For two sets A and B, the Cartesian product of A and B is denoted by A×B and defined as:
 * A×B = { (a,b) | aϵA and bϵB }
 *
 * Put simply, the Cartesian Product is the multiplication of two sets to form the set of all ordered pairs.
 * This function returns the cartesian product of itself, meaning both A and B are simply [this].
 *
 * @see cartesianProductCubic for a variant that accepts another set.
 */
fun <T> List<T>.cartesianProductQuadratic(): List<Pair<T, T>> = this.flatMap { value ->
    List(this.size){ i -> Pair(value, this[i]) }
}

/**
 * For three sets A, B and C, the Cartesian product of A, B and C is denoted by A×B×C and defined as:
 * A×B×C = { (p, q, r) | pϵA and qϵB and rϵC }
 *
 * Put simply, the Cartesian Product is the multiplication of three sets to form the set of all ordered pairs.
 * This function returns the cartesian product of itself and the given sets, meaning that A, B & C are all [this].
 *
 * @see cartesianProductQuadratic for a variation that simply finds the product of itself.
 */
fun <T> List<T>.cartesianProductCubic(): List<Triple<T, T, T>> = cartesianProduct(this, this, this).map { set ->
    Triple(set[0], set[1], set[2])
}

/**
 * For three sets A, B and C, the Cartesian product of A, B and C is denoted by A×B×C and defined as:
 * A×B×C = { (p, q, r) | pϵA and qϵB and rϵC }
 *
 * Put simply, the Cartesian Product is the multiplication of three sets to form the set of all ordered pairs.
 * This function returns the cartesian product of itself and the given sets, meaning both A, B and C are [this],
 * [second] and [third] respectively.
 */
fun <T> List<T>.cartesianProductCubic(second: List<T>, third: List<T>): List<Triple<T, T, T>> =
    cartesianProduct(this, second, third).map { set -> Triple(set[0], set[1], set[2]) }

/**
 * Finds the Cartesian Product of any number of given [sets].
 */
private fun <T> cartesianProduct(vararg sets: List<T>): List<List<T>> = sets.fold(listOf(listOf())) { acc, set ->
    acc.flatMap { list -> set.map { element -> list + element } }
}

/**
 * Produces a list of all distinct pairs of elements from the given collection.
 * Pairs are considered distinct irrespective of their order.
 *
 * For example, given a collection of [A, B, C]:
 * - [A, A] is NOT considered distinct
 * - [A, B] and [B, A] are considered equal
 * - The output for this collection would be [[A, B], [A, C], [B, C]].
 *
 * @return A list of all distinct pairs of elements.
 */
fun <T> Collection<T>.distinctPairs(): List<Pair<T, T>> = this.flatMapIndexed { i, element ->
    this.drop(i + 1).map { otherElement ->
        element to otherElement
    }
}

/**
 * Splits a collection based on the given [predicate].
 * @param predicate A boolean predicate to determine when to split the list.
 * @return A collection of collections after splitting.
 */
fun <T> Collection<T>.split(predicate: (element: T) -> Boolean): Collection<Collection<T>> {
    var i = 0
    val data = mutableMapOf<Int, List<T>>()
    var current = mutableListOf<T>()

    this.forEachIndexed { index, element ->
        if (index == this.size - 1) {
            current.add(element)
            data[i] = current
        } else if (predicate(element)) {
            data[i] = current
            i++
            current = mutableListOf()
        } else {
            current.add(element)
        }
    }

    return data.values.toList()
}

/**
 * Parses a collection of Strings (Usually puzzle input lines)
 * vertically to produce two lists.
 *
 * For example:
 *  1  5
 *  8  2
 *  3  7
 *
 * Would produce two lists
 *  [1, 8, 3]
 *  [5, 2, 7]
 *
 * @param parse A lambda that takes a string and returns a Pair of values (T, U) if parsing is successful.
 * @return Two lists of values from the vertical representation in the string collection.
 */
fun <L, R> Collection<String>.toVerticalLists(parse: (String) -> Pair<L, R>?): Pair<MutableList<L>, MutableList<R>> {
    val first = mutableListOf<L>()
    val second = mutableListOf<R>()

    for (string in this) {
        parse(string)?.let { (a, b) ->
            first.add(a)
            second.add(b)
        }
    }

    return Pair(first, second)
}

/**
 * Calculates the lowest common multiple of
 * all the long values in the list.
 */
fun List<Long>.lcm(): Long {
    if (this.isNotEmpty()) {
        var result = this[0]
        this.forEachIndexed { i, _ -> result = lcm(result, this[i]) }
        return result
    }

    throw IllegalArgumentException("Cannot find the LCM of an empty list.")
}

private fun lcm(a: Long, b: Long) = a * (b / gcd(a, b))

/**
 * Finds the global common divisor
 * of the two numbers.
 *
 * @param a the first number
 * @param b the second number.
 *
 * @return The global common divisor.
 */
private fun gcd(a: Long, b: Long): Long {
    var n1 = a
    var n2 = b

    while (n1 != n2) {
        if (n1 > n2) n1 -= n2 else n2 -= n1
    }

    return n1
}