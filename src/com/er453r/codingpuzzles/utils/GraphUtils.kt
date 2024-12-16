package com.er453r.codingpuzzles.utils

import java.util.*

fun <Node> aStar(
    start: Node,
    isEndNode: (Node) -> Boolean,
    moveCost: (Node, Node) -> Int = { _, _ -> 1 },
    heuristic: (Node) -> Int = { 0 },
    neighbours: (Node) -> Iterable<Node>,
): Pair<List<Node>, Int> {
    val gScores = mutableMapOf(start to 0)
    val fScores = mutableMapOf(start to heuristic(start))
    val cameFrom = mutableMapOf<Node, Node>()
    val openSet = PriorityQueue<Node> { a, b -> fScores[a]!! - fScores[b]!! }.also { it.add(start) }

    fun reconstructPath(cameFrom: Map<Node, Node>, end: Node): List<Node> {
        val path = mutableListOf(end)
        var current = end

        while (current in cameFrom) {
            current = cameFrom[current]!!
            path.add(current)
        }

        return path.reversed()
    }

    while (openSet.isNotEmpty()) {
        val current = openSet.poll()

        if (isEndNode(current))
            return Pair(reconstructPath(cameFrom, current), gScores[current]!!)

        for (neighbour in neighbours(current)) {
            val neighbourScore = gScores[current]!! + moveCost(current, neighbour)

            if (neighbourScore < gScores.getOrDefault(neighbour, 999999)) {
                cameFrom[neighbour] = current
                gScores[neighbour] = neighbourScore
                fScores[neighbour] = neighbourScore + heuristic(neighbour)

                if (neighbour !in openSet)
                    openSet += neighbour
            }
        }
    }

    return Pair(emptyList(), 0)
}

fun <Node> aStarM(
    start: Node,
    isEndNode: (Node) -> Boolean,
    moveCost: (Node, Node) -> Int = { _, _ -> 1 },
    heuristic: (Node) -> Int = { 0 },
    neighbours: (Node) -> Iterable<Node>,
): Pair<List<List<Node>>, Int> {
    val gScores = mutableMapOf(start to 0)
    val fScores = mutableMapOf(start to heuristic(start))
    val cameFrom = mutableMapOf<Node, MutableList<Node>>() // Store multiple origins for backtracking
    val openSet = PriorityQueue<Node> { a, b -> fScores[a]!! - fScores[b]!! }.apply { add(start) }
    val paths = mutableListOf<List<Node>>() // To store all optimal paths
    var bestScore = Int.MAX_VALUE // Keep track of the best (minimal) score

    fun reconstructPath(cameFrom: Map<Node, List<Node>>, end: Node): List<List<Node>> {
        val paths = mutableListOf<List<Node>>()
        fun buildPath(current: Node, path: MutableList<Node>) {
            path.add(current)
            if (current in cameFrom) {
                for (parent in cameFrom[current]!!) {
                    buildPath(parent, path.toMutableList())
                }
            } else {
                paths.add(path.reversed())
            }
        }
        buildPath(end, mutableListOf())
        return paths
    }

    while (openSet.isNotEmpty()) {
        val current = openSet.poll()

        if (isEndNode(current)) {
            val currentScore = gScores[current]!!
            if (currentScore < bestScore) {
                bestScore = currentScore
                paths.clear() // Clear existing paths since we found a better score
            }
            if (currentScore == bestScore) {
                paths.addAll(reconstructPath(cameFrom, current))
            }
            continue
        }

        for (neighbor in neighbours(current)) {
            val neighborScore = gScores[current]!! + moveCost(current, neighbor)

            if (neighborScore <= gScores.getOrDefault(neighbor, Int.MAX_VALUE)) {
                if (neighborScore < gScores.getOrDefault(neighbor, Int.MAX_VALUE)) {
                    cameFrom[neighbor] = mutableListOf(current) // Overwrite previous paths
                } else {
                    cameFrom[neighbor]?.add(current) // Add to existing paths
                }
                gScores[neighbor] = neighborScore
                fScores[neighbor] = neighborScore + heuristic(neighbor)
                if (neighbor !in openSet) {
                    openSet.add(neighbor)
                }
            }
        }
    }

    return Pair(paths, if (paths.isNotEmpty()) bestScore else 0)
}