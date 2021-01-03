package io.github.tomplum.libs.solutions.benchmark.data

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * The result of a benchmark run for n number of solutions.
 * @property results A list of benchmark results.
 */
class BenchmarkResult {
    val results = mutableListOf<Benchmark<*>>()

    /**
     * Adds a single [benchmark] to the result.
     * @param benchmark The benchmark to add.
     */
    fun add(benchmark: Benchmark<*>) = results.add(benchmark)

    /**
     * Gets a single benchmark from the result with the given [day] number.
     * @param day The number of the day. 1 or 2 digits.
     * @throws IllegalArgumentException if there is no benchmark result for the given day.
     * @return The benchmark for the given day.
     */
    fun get(day: Int): Benchmark<*> = results.find { benchmark ->
        benchmark.day == day
    } ?: throw IllegalArgumentException("No Benchmark Result For Day $day")

    /**
     * Calculates the total runtime of all the benchmark [results].
     * @return The runtime sum in nanoseconds.
     */
    @JsonIgnore
    fun getTotalTime(): Long = sumDayParts().sum()

    /**
     * Calculates the average runtime of all the benchmark [results].
     * @return The runtime sum in nanoseconds.
     */
    @JsonIgnore
    fun getAvgTime(): Long = sumDayParts().average().toLong()

    /**
     * Iterates over the [results] and sums both parts together for each day.
     * @return A list of the sums of both day parts.
     */
    private fun sumDayParts() = results.map { benchmark -> benchmark.runtime1 + benchmark.runtime2 }
}