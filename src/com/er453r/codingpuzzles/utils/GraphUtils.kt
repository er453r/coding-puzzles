package com.er453r.codingpuzzles.utils

fun <Node> aStar(
    start: Node,
    isEndNode: (Node) -> Boolean,
    moveCost: (Node, Node) -> Int,
    heuristic: (Node) -> Int,
    neighbours: (Node) -> Collection<Node>,
): List<Node> {
    val openSet = mutableSetOf(start)
    val gScores = mutableMapOf(start to 0)
    val fScores = mutableMapOf(start to heuristic(start))
    val cameFrom = mutableMapOf<Node, Node>()

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
        val current = openSet.minBy { fScores[it]!! }

        if (isEndNode(current))
            return reconstructPath(cameFrom, current)

        openSet.remove(current)

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

    return emptyList()
}
