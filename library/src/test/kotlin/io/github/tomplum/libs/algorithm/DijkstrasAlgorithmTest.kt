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

class DijkstrasAlgorithmTest {
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

    @Nested
    inner class ExamplesFrom2023Day17 {
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
    }

    private class MazeTile(override val value: Char): MapTile<Char>(value) {
        fun isReindeerStart() = value == 'S'
        fun isEnd() = value == 'E'
        fun isTraversable() = value == '.' || isReindeerStart() || isEnd()
    }

    private class ReindeerMaze(data: List<String>): AdventMap2D<MazeTile>() {
        private val directions = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)

        init {
            init(data) {
                MazeTile(it as Char)
            }
        }

        fun calculateLowestPossibleScore(): Int {
            val (startingPosition) = findTile { it.isReindeerStart() }!!
            val startingDirection = Direction.RIGHT

            return dijkstraShortestPath(
                startingPositions = listOf(startingPosition to startingDirection),
                evaluateAdjacency = { currentNode ->
                    val (currentPosition, direction) = currentNode.value
                    val adjacentNodes = mutableListOf<Node<Pair<Point2D, Direction>>>()

                    val nextPosition = currentPosition.shift(direction)
                    if (getTile(nextPosition, MazeTile('?')).isTraversable()) {
                        adjacentNodes.add(Node(nextPosition to direction, 1))
                    }

                    val rotations = directions
                        .filterNot { currentDirection ->
                            val isSameDirection = currentDirection == direction
                            val isBacktracking = currentDirection.isOpposite(direction)
                            isSameDirection || isBacktracking
                        }
                        .map { currentDirection ->
                            Node(currentPosition to currentDirection, 1000)
                        }

                    adjacentNodes.addAll(rotations)

                    adjacentNodes
                },
                terminates = { currentNode ->
                    getTile(currentNode.value.first, MazeTile('?')).isEnd()
                }
            )
        }

        fun countBestPathTiles(): Int {
            val (startingPosition) = findTile { it.isReindeerStart() }!!
            val startingDirection = Direction.RIGHT

            return dijkstraAllShortestPaths(
                startingPositions = listOf(startingPosition to startingDirection),
                evaluateAdjacency = { currentNode ->
                    val (currentPosition, direction) = currentNode.value
                    val adjacentNodes = mutableListOf<Node<Pair<Point2D, Direction>>>()

                    val nextPosition = currentPosition.shift(direction)
                    if (getTile(nextPosition, MazeTile('?')).isTraversable()) {
                        adjacentNodes.add(Node(nextPosition to direction, 1))
                    }

                    val rotations = directions
                        .filterNot { currentDirection ->
                            val isSameDirection = currentDirection == direction
                            val isBacktracking = currentDirection.isOpposite(direction)
                            isSameDirection || isBacktracking
                        }
                        .map { currentDirection ->
                            Node(currentPosition to currentDirection, 1000)
                        }

                    adjacentNodes.addAll(rotations)

                    adjacentNodes
                },
                terminates = { currentNode ->
                    getTile(currentNode.value.first, MazeTile('?')).isEnd()
                }
            ).shortestPaths.flatten().map { it.first }.toSet().count()
        }
    }

    @Nested
    inner class ExamplesFrom2024Day16 {
        private val exampleInputLocation = "2024/day16"

        @Test
        fun partOneExampleOne() {
            val data = TestInputReader.read<String>("$exampleInputLocation/example-1.txt")
            val reindeerMaze = ReindeerMaze(data.value)
            assertThat(reindeerMaze.calculateLowestPossibleScore()).isEqualTo(7036)
        }

        @Test
        fun partOneExampleTwo() {
            val data = TestInputReader.read<String>("$exampleInputLocation/example-2.txt")
            val reindeerMaze = ReindeerMaze(data.value)
            assertThat(reindeerMaze.calculateLowestPossibleScore()).isEqualTo(11048)
        }

        @Test
        fun partOneSolution() {
            val data = TestInputReader.read<String>("$exampleInputLocation/input.txt")
            val reindeerMaze = ReindeerMaze(data.value)
            assertThat(reindeerMaze.calculateLowestPossibleScore()).isEqualTo(65436)
        }

        @Test
        fun partTwoExampleOne() {
            val data = TestInputReader.read<String>("$exampleInputLocation/example-1.txt")
            val reindeerMaze = ReindeerMaze(data.value)
            assertThat(reindeerMaze.countBestPathTiles()).isEqualTo(45)
        }

        @Test
        fun partTwoExampleTwo() {
            val data = TestInputReader.read<String>("$exampleInputLocation/example-2.txt")
            val reindeerMaze = ReindeerMaze(data.value)
            assertThat(reindeerMaze.countBestPathTiles()).isEqualTo(64)
        }

        @Test
        fun partTwoSolution() {
            val data = TestInputReader.read<String>("$exampleInputLocation/input.txt")
            val reindeerMaze = ReindeerMaze(data.value)
            assertThat(reindeerMaze.countBestPathTiles()).isEqualTo(489)
        }
    }
}