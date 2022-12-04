package io.github.tomplum.libs.solutions.benchmark.report

/**
 * A variant of [BenchmarkReport] that is produced when there is a recorded previous run.
 * Reports the runtime deltas between each of the days parts and the total/average times.
 */
abstract class DeltaReport : BenchmarkReport() {

    protected fun formatExecutionTime(runtime: Long, delta: Long): String {
        return "${formatTime(runtime)} (${formatDelta(delta)})\n"
    }

    private fun formatDelta(time: Long): String = when {
        time > 0 -> "+${formatTime(time)}".coloured(Colour.RED)
        time < 0 -> formatTime(time).coloured(Colour.GREEN)
        else -> "±0ms"
    }
}