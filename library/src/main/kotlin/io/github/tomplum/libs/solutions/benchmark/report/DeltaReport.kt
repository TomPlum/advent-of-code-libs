package io.github.tomplum.libs.solutions.benchmark.report

/**
 * 1 millisecond in nanoseconds
 */
private const val ONE_MILLISECOND = 1_000_000

/**
 * A variant of [BenchmarkReport] that is produced when there is a recorded previous run.
 * Reports the runtime deltas between each of the days parts and the total/average times.
 */
abstract class DeltaReport : BenchmarkReport() {
    /**
     * Formats the given [runtime] and time [delta] for
     * a [BenchmarkComparisonReport] output.
     *
     * @param runtime The total runtime of a solution in nanoseconds
     * @param delta The total time difference between runs in nanoseconds
     * @return The pretty formatted string of times
     */
    protected fun formatExecutionTime(runtime: Long, delta: Long): String {
        return "${formatTime(runtime)} (${formatDelta(delta)})\n"
    }

    /**
     * Formats the given [delta] in a colour for the report.
     * Positive times are shown in green, while negative are in red.
     *
     * @param delta The time difference in nanoseconds
     * @return The ANSI formatted time string
     */
    private fun formatDelta(delta: Long): String = when {
        delta > ONE_MILLISECOND -> "+${formatTime(delta)}".coloured(Colour.RED)
        delta in 1 until ONE_MILLISECOND -> "+${formatTime(delta)}".coloured(Colour.YELLOW)
        delta < 0 -> formatTime(delta).coloured(Colour.GREEN)
        else -> "±0ms"
    }
}