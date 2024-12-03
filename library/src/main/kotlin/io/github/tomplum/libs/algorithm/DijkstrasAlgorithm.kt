package io.github.tomplum.libs.algorithm

import java.util.*

/**
 * A single node in a graph traversed by Dijkstra's algorithm.
 *
 * @param value The value of the node. Usually contains cartesian positional information.
 * @param distance The distance to this node from the starting point.
 */
data class Node<T>(val value: T, val distance: Int): Comparable<Node<T>> {
    override fun compareTo(other: Node<T>): Int {
        return distance.compareTo(other.distance)
    }
}

/**
 * Calculates the shortest distance to all nodes in a weighted-graph from the given [startingPositions]
 * and terminates based on the given [terminates] predicate.
 *
 * @param startingPositions A collection of nodes to start from.
 * @param evaluateAdjacency A function that is passed an instance of the current node and should return a collection of all adjacent nodes or "neighbours" that should be evaluated as part of the pathfinding.
 * @param processNode An optional function that is applied after the adjacency evaluation for each neighbouring node. Can be used to mutate the state of the node before evaluation.
 * @param terminates A boolean predicate used to determine when the destination node has been reached. When it evaluates as true, the algorithm is terminated and the shortest path for the current node is returned.
 * @return The shortest path from the starting nodes to the node that produces true when passed into the [terminates] predicate.
 */
fun <N> dijkstraShortestPath(
    startingPositions: Collection<N>,
    evaluateAdjacency: (currentNode: Node<N>) -> Collection<Node<N>>,
    processNode: (currentNode: Node<N>, adjacentNode: Node<N>) -> Node<N>,
    terminates: (currentNode: Node<N>) -> Boolean
): Int {
    // A map of nodes and the shortest distance from the given starting positions to it
    val distance = mutableMapOf<N, Int>()

    // Unsettled nodes that are yet to be evaluated. Prioritised by their distance from the start
    val next = PriorityQueue<Node<N>>()

    // Settled nodes whose shortest path has been calculated and need not be evaluated again
    val settled = mutableSetOf<N>()

    startingPositions.forEach { startingPosition ->
        next.offer(Node(startingPosition, 0))
    }

    while(next.isNotEmpty()) {
        // Take the next node from the queue, ready for evaluation
        val currentNode = next.poll()

        // Considered the current node settled now
        settled.add(currentNode.value)

        // If the terminal condition has been met
        // (I.e. the current node is the location we want to find the shortest path to)
        // then we stop and return the current known shortest distance to it
        if (terminates(currentNode)) {
            return currentNode.distance
        }

        // Find all the adjacent nodes to the current one (as per the given predicate)
        // and evaluate each one
        evaluateAdjacency(currentNode).forEach { adjacentNode ->
            // Perform any additional processing on the adjacent node before evaluation
            val evaluationNode = processNode(currentNode, adjacentNode)

            if (!settled.contains(evaluationNode.value)) {
                // The new shortest path to the adjacent node is the current nodes distance
                // plus the weight of the node being evaluated
                val updatedDistance = currentNode.distance + evaluationNode.distance

                // If the distance of this new path is shorter than the shortest path we're
                // already aware of, then we can update it since we've found a shorter one
                if (updatedDistance < distance.getOrDefault(evaluationNode.value, Int.MAX_VALUE)) {
                    // Track the new shortest path
                    distance[evaluationNode.value] = updatedDistance

                    // Queue up the adjacent node to continue down that path
                    next.add(Node(evaluationNode.value, updatedDistance))
                }
            }
        }
    }

    val message = "Could not find a path from the given starting positions to the node indicated by the terminates predicate."
    throw IllegalStateException(message)
}