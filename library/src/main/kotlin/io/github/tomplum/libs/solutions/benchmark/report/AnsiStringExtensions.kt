package io.github.tomplum.libs.solutions.benchmark.report

const val reset = "\u001B[0m"

fun String.coloured(colour: Colour) = "${colour.escapeCode}$this$reset"

/**
 * An ANSI escape code for coloured printing in benchmark reports.
 * @param escapeCode The escape code for the colour.
 */
enum class Colour(val escapeCode: String) {
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    RED("\u001B[31m")
}