package io.github.tomplum.libs.solutions.benchmark.data

class BenchmarkComparison(private val previousRun: BenchmarkResult, val lastRun: BenchmarkResult) {
    fun getDeltas(): List<BenchmarkDelta> = previousRun.results.zip(lastRun.results).map { (previous, last) ->
        BenchmarkDelta(previous.day, last.runtime1 - previous.runtime1, last.runtime2 - previous.runtime2)
    }

    fun getAvgDelta(): Long = lastRun.getAvgTime() - previousRun.getAvgTime()

    fun getTotalDelta(): Long = lastRun.getTotalTime() - previousRun.getTotalTime()
}