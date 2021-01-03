package io.github.tomplum.libs.solutions.benchmark.data

/**
 * The runtime deltas of a single days solution between two benchmarking runs.
 *
 * @param day The day number.
 * @param p1 The runtime delta of part one in nanoseconds.
 * @param p2 The runtime delta of part two in nanoseconds.
 */
data class BenchmarkDelta(val day: Int, val p1: Long, val p2: Long)