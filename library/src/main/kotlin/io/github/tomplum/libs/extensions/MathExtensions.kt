package io.github.tomplum.libs.extensions

import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs

/**
 * Calculates the inner area of the polygon created by
 * the given collection of [Point2D]. It is assumed that
 * the points make a single, bounded polygon with no gaps
 * or overlapping vertices.
 *
 * The points themselves are not included in the area and
 * must be added separately to calculate the total area
 * of the polygon.
 *
 * Uses the Shoelace Algorithm to calculate the area.
 * The shoelace formula or shoelace algorithm is a mathematical
 * algorithm to determine the area of a simple polygon whose
 * vertices are described by their Cartesian coordinates in the plane.
 *
 * @see <a href="https://www.101computing.net/the-shoelace-algorithm/">Shoelace algorithm</a>
 * @return The
 */
fun List<Point2D>.area(): Long = (1..this.size)
    .asSequence()
    .map { i ->
        val vertices = this.size
        this[i % vertices].y.toLong() * (this[(i - 1) % vertices].x.toLong() - this[(i + 1) % vertices].x.toLong())
    }
    .sum()
    .let { area -> abs(area) / 2 }