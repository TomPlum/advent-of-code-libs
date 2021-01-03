package io.github.tomplum.libs.solutions.benchmark.data

/**
 * A comparison between two [BenchmarkResult].
 * Calculates runtime deltas between the [previousRun] and the [lastRun].
 * @param previousRun The previous solution run from the serialised XML file.
 * @param lastRun The most recent solution to run.
 */
class BenchmarkComparison(private val previousRun: BenchmarkResult, val lastRun: BenchmarkResult) {
    /**
     * Calculates the runtime delta between each of the respective day parts for given runs.
     * @return A list of benchmark result runtime deltas.
     */
    fun getDeltas(): List<BenchmarkDelta> = previousRun.results.zip(lastRun.results).map { (previous, last) ->
        BenchmarkDelta(previous.day, last.runtime1 - previous.runtime1, last.runtime2 - previous.runtime2)
    }

    /**
     * Calculates the runtime delta between the average runtime of both results.
     * @return The delta of the runtime averages.
     */
    fun getAvgDelta(): Long = lastRun.getAvgTime() - previousRun.getAvgTime()

    /**
     * Calculates the runtime delta between the total runtime of both results.
     * @return The delta of the runtime totals.
     */
    fun getTotalDelta(): Long = lastRun.getTotalTime() - previousRun.getTotalTime()
}