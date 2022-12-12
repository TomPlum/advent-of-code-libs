package io.github.tomplum.libs.solutions.benchmark.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A benchmark for a single Advent of Code day.
 *
 * @param day The day number. 1 or 2 digits.
 * @param answer1 The answer to part 1 of the [day].
 * @param answer2 The answer to part 2 of the [day].
 * @param runtime1 The measured runtime of part 1 in nanoseconds.
 * @param runtime2 The measured runtime of part 2 in nanoseconds.
 */
data class Benchmark<T> @JsonCreator constructor(
    @JsonProperty("day") val day: Int,
    @JsonProperty("answer1") val answer1: T,
    @JsonProperty("answer2") val answer2: T,
    @JsonProperty("runtimeP1") val runtime1: Long,
    @JsonProperty("runtimeP2") val runtime2: Long
)