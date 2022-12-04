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
     * Formats the given [time] difference in a colour for the report.
     * Positive times are shown in green, while negative are in red.
     *
     * @param time The time difference in nanoseconds
     * @return The ANSI formatted time string
     */
    private fun formatDelta(time: Long): String = when {
        time > ONE_MILLISECOND -> "+${formatTime(time)}".coloured(Colour.RED)
        time in 1 until ONE_MILLISECOND -> "+${formatTime(time)}".coloured(Colour.YELLOW)
        time < 0 -> formatTime(time).coloured(Colour.GREEN)
        else -> "±0ms"
    }
}