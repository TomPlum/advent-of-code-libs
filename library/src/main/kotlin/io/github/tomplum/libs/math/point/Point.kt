package io.github.tomplum.libs.math.point

interface Point {
    fun adjacent(): Collection<Point>
}