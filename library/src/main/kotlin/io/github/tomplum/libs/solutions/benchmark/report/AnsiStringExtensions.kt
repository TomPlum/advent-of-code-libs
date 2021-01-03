package io.github.tomplum.libs.solutions.benchmark.report

const val reset = "\u001B[0m"

fun String.coloured(colour: Colour) = "${colour.escapeCode}$this$reset"

enum class Colour(val escapeCode: String) {
    GREEN("\u001B[32m"),
    RED("\u001B[31m")
}