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
    terminates: (currentNode: Node<N>) -> Boolean,
    processNode: (currentNode: Node<N>, adjacentNode: Node<N>) -> Node<N> = { _, adjacentNode -> adjacentNode }
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

        // Consider the current node settled now
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

data class DijkstraAllPaths<N>(
    /**
     * The shortest distance from the starting
     * position to the end position.
     */
    val shortestDistance: Int,


    /**
     * A list of all the nodes from all
     * variations of the shortest path.
     */
    val shortestPaths: List<Set<N>>
)

/**
 * Calculates the shortest distance to all nodes in a weighted-graph from the given [startingPositions]
 * and terminates based on the given [terminates] predicate.
 *
 * All shortest paths (that is paths that all share the same, shortest distance) are tracked.
 *
 * If you do not need all the shortest paths, but simply any of them for the distance,
 * then use [dijkstraShortestPath] instead as it will be more performant.
 *
 * @param startingPositions A collection of nodes to start from.
 * @param evaluateAdjacency A function that is passed an instance of the current node and should return a collection of all adjacent nodes or "neighbours" that should be evaluated as part of the pathfinding.
 * @param processNode An optional function that is applied after the adjacency evaluation for each neighbouring node. Can be used to mutate the state of the node before evaluation.
 * @param terminates A boolean predicate used to determine when the destination node has been reached. When it evaluates as true, the algorithm is terminated and the shortest path for the current node is returned.
 *
 * @returns The shortest path from the starting nodes to the node that produces true when passed into the [terminates] predicate.
 */
fun <N> dijkstraAllShortestPaths(
    startingPositions: Collection<N>,
    evaluateAdjacency: (currentNode: Node<N>) -> Collection<Node<N>>,
    terminates: (currentNode: Node<N>) -> Boolean,
    processNode: (currentNode: Node<N>, adjacentNode: Node<N>) -> Node<N> = { _, adjacentNode -> adjacentNode }
): DijkstraAllPaths<N> {
    // A map of nodes and the shortest distance from the starting positions to it
    val distance = mutableMapOf<N, Int>()

    // A map to track predecessors for each node (to reconstruct paths)
    val predecessors = mutableMapOf<N, MutableSet<N>>()

    // Unsettled nodes prioritized by their distance
    val next = PriorityQueue<Node<N>>()

    startingPositions.forEach { startingPosition ->
        next.offer(Node(startingPosition, 0))
        distance[startingPosition] = 0
    }

    var shortestDistance: Int? = null

    while (next.isNotEmpty()) {
        // Take the next node from the queue
        val currentNode = next.poll()

        // If we have determined the shortest distance and this node's distance exceeds it, stop processing
        if (shortestDistance != null && currentNode.distance > shortestDistance) {
            break
        }

        // If the termination condition is met, record the shortest distance
        if (terminates(currentNode)) {
            if (shortestDistance == null) {
                shortestDistance = currentNode.distance
            }
        }

        // Find all adjacent nodes
        evaluateAdjacency(currentNode).forEach { adjacentNode ->
            // Perform additional processing on the adjacent node before evaluation
            val evaluationNode = processNode(currentNode, adjacentNode)

            // The new shortest path to the adjacent node
            val updatedDistance = currentNode.distance + evaluationNode.distance

            // If the distance of this new path is shorter than or equal to the known shortest distance
            if (updatedDistance <= distance.getOrDefault(evaluationNode.value, Int.MAX_VALUE)) {
                // Update distance
                distance[evaluationNode.value] = updatedDistance

                // Update predecessors
                predecessors.computeIfAbsent(evaluationNode.value) { mutableSetOf() }.add(currentNode.value)

                // Add the adjacent node to the queue
                next.add(Node(evaluationNode.value, updatedDistance))
            }
        }
    }

    // Now we need to collect all nodes that are part of the shortest paths
    val shortestPathNodes = mutableSetOf<N>()

    // Helper function to collect all nodes along the shortest paths
    fun collectShortestPathNodes(node: N) {
        if (shortestPathNodes.contains(node)) {
            return // Already visited
        }

        shortestPathNodes.add(node)

        predecessors[node]?.forEach { parent ->
            collectShortestPathNodes(parent)
        }
    }

    // Recursive helper to backtrack and collect all paths
    fun collectPaths(node: N, currentPath: Set<N>): List<Set<N>> {
        val newPath = setOf(node) + currentPath
        val currentNodePredecessors = predecessors[node]

        return if (currentNodePredecessors.isNullOrEmpty()) {
            // No more predecessors, this is the start of a path
            listOf(newPath)
        } else {
            currentNodePredecessors.flatMap { predecessorNode ->
                collectPaths(predecessorNode, newPath)
            }
        }
    }

    // Collect all nodes for the shortest path, starting from the end nodes
    // This step ensures we get all nodes in all shortest paths, not just the first one
    val endNodes = distance.filter { terminates(Node(it.key, it.value)) }.keys
    endNodes.forEach { collectShortestPathNodes(it) }

    val shortestPaths = endNodes.flatMap { node -> collectPaths(node, emptySet()) }

    return DijkstraAllPaths(
        shortestDistance = shortestDistance ?: 0,
        shortestPaths = shortestPaths
    )
}