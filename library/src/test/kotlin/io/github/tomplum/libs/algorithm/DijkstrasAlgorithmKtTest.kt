package io.github.tomplum.libs.algorithm

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.libs.input.TestInputReader
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.map.MapTile
import io.github.tomplum.libs.math.point.Point2D
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DijkstrasAlgorithmKtTest {
    @Nested
    class ExamplesFrom2023Day17 {
        private val exampleInputLocation = "2023/day17"

        @Test
        fun partOneExampleOne() {
            val data = TestInputReader.read<String>("$exampleInputLocation/example-1.txt")
            val citMap = CityMap(data.value)
            assertThat(citMap.solutionPartOne()).isEqualTo(102)
        }

        @Test
        fun partTwoExampleOne() {
            val data = TestInputReader.read<String>("$exampleInputLocation/example-1.txt")
            val citMap = CityMap(data.value)
            assertThat(citMap.solutionPartTwo()).isEqualTo(94)
        }

        @Test
        fun partTwoExampleTwo() {
            val data = TestInputReader.read<String>("$exampleInputLocation/example-2.txt")
            val citMap = CityMap(data.value)
            assertThat(citMap.solutionPartTwo()).isEqualTo(71)
        }

        private data class CrucibleLocation(
            val position: Point2D,
            val direction: Direction,
            val isMovingStraight: Boolean,
            var consecutiveSteps: Int = 1
        )

        private class CityMap(data: List<String>): AdventMap2D<CityBlock>() {
            private val factoryLocation: Point2D

            init {
                var x = 0
                var y = 0

                data.forEach { row ->
                    row.forEach { column ->
                        val tile = CityBlock(column.toString().toInt())
                        val position = Point2D(x, y)

                        addTile(position, tile)
                        x++
                    }
                    x = 0
                    y++
                }

                factoryLocation = Point2D(xMax()!!, yMax()!!)
            }

            fun solutionPartOne(): Int = dijkstraShortestPath(
                startingPositions = listOf(
                    CrucibleLocation(Point2D.origin(), Direction.RIGHT, true, 0),
                    CrucibleLocation(Point2D.origin(), Direction.UP, true, 0)
                ),
                evaluateAdjacency = { (currentNode) ->
                    val (currentPos, currentDirection, _, consecutiveSteps) = currentNode

                    currentPos.let { pos ->
                        val rightDirection = currentDirection.rotate(90)
                        val rightPosition = pos.shift(rightDirection)
                        val right = CrucibleLocation(rightPosition, rightDirection, false)

                        val leftDirection = currentDirection.rotate(-90)
                        val leftPosition = pos.shift(leftDirection)
                        val left = CrucibleLocation(leftPosition, leftDirection, false)

                        val candidates = mutableListOf(left, right)

                        if (consecutiveSteps < 3) {
                            val straightPosition = pos.shift(currentDirection)
                            val straight = CrucibleLocation(straightPosition, currentDirection, true)
                            candidates.add(straight)
                        }

                        candidates.filter { (position) -> hasRecorded(position) }
                            .map { location -> Node(location, getTile(location.position).value) }
                    }
                },
                processNode = { currentNode, adjacentNode ->
                    val updatedNodeValue = adjacentNode.value.apply {
                        consecutiveSteps = if (adjacentNode.value.isMovingStraight) {
                            currentNode.value.consecutiveSteps + 1
                        } else 1
                    }

                    Node(updatedNodeValue, adjacentNode.distance)
                },
                terminates = { currentNode ->
                    currentNode.value.position == factoryLocation
                }
            )

            fun solutionPartTwo(): Int = dijkstraShortestPath(
                startingPositions = listOf(
                    CrucibleLocation(Point2D.origin(), Direction.RIGHT, true, 0),
                    CrucibleLocation(Point2D.origin(), Direction.UP, true, 0)
                ),
                evaluateAdjacency = { (currentNode) ->
                    val (currentPos, currentDirection, _, consecutiveSteps) = currentNode

                    currentPos.let { pos ->
                        val candidates = mutableListOf<CrucibleLocation>()

                        if (consecutiveSteps >= 4) {
                            val rightDirection = currentDirection.rotate(90)
                            val right = CrucibleLocation(pos.shift(rightDirection), rightDirection, false)
                            candidates.add(right)

                            val leftDirection = currentDirection.rotate(-90)
                            val left = CrucibleLocation(pos.shift(leftDirection), leftDirection, false)
                            candidates.add(left)
                        }

                        if (consecutiveSteps < 10) {
                            val straight = CrucibleLocation(pos.shift(currentDirection), currentDirection, true)
                            candidates.add(straight)
                        }

                        candidates.filter { (position) -> hasRecorded(position) }
                            .map { location -> Node(location, getTile(location.position).value) }
                    }
                },
                processNode = { currentNode, adjacentNode ->
                    val updatedNodeValue = adjacentNode.value.apply {
                        consecutiveSteps = if (adjacentNode.value.isMovingStraight) {
                            currentNode.value.consecutiveSteps + 1
                        } else 1
                    }

                    Node(updatedNodeValue, adjacentNode.distance)
                },
                terminates = { currentNode ->
                    val hasReachedFactory = currentNode.value.position == factoryLocation
                    val hasTravelledMoreThanFourStraight = currentNode.value.consecutiveSteps >= 4
                    hasReachedFactory && hasTravelledMoreThanFourStraight
                }
            )
        }

        private class CityBlock(override val value: Int): MapTile<Int>(value)
    }
}