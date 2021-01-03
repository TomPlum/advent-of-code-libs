package io.github.tomplum.libs.solutions.benchmark.report

import kotlin.math.abs

abstract class BenchmarkReport {
    /**
     * Formats the given [time] as a presentable string with units.
     * If a given unit is 0 then it is omitted from the result.
     * @param time The time taken in nanoseconds.
     * @return The time taken in the format "Xs Yms", "Xms" or "Xμs"
     */
    protected fun formatTime(time: Long): String {
        val s = time / 1_000_000_000
        val ms = time / 1_000_000
        val remainingNanos = time % 1_000_000_000
        val remainingMillis = remainingNanos / 1_000_000
        val micro = time / 1000
        return when {
            abs(s) > 0 -> "${s}s ${remainingMillis}ms"
            abs(ms) >= 1 -> "${ms}ms"
            else -> "${micro}μs"
        }
    }
}