package io.github.tomplum.libs.solutions

/**
 * The solution to both parts of a given day in Advent of Code.
 * @param F The return type for the first part.
 * @param S the return type for the second part.
 */
interface Solution<F, S> {
    fun part1(): F? = null
    fun part2(): S? = null
}