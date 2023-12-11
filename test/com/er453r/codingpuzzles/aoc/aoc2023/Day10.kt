package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.Vector2d
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 10")
class Day10 : AoCTestBase<Int>(
    year = 2023,
    day = 10,
    testTarget1 = 8,
    puzzleTarget1 = 7173,
    testTarget2 = 8,
    puzzleTarget2 = 291,
) {
    //    | is a vertical pipe connecting north and south.
    //    - is a horizontal pipe connecting east and west.
    //    L is a 90-degree bend connecting north and east.
    //    J is a 90-degree bend connecting north and west.
    //    7 is a 90-degree bend connecting south and west.
    //    F is a 90-degree bend connecting south and east.
    enum class Connector(val value: Char, val ports: Set<Vector2d>) {
        UD('|', setOf(Vector2d.UP, Vector2d.DOWN)),
        LR('-', setOf(Vector2d.LEFT, Vector2d.RIGHT)),
        UR('L', setOf(Vector2d.UP, Vector2d.RIGHT)),
        UL('J', setOf(Vector2d.UP, Vector2d.LEFT)),
        LD('7', setOf(Vector2d.LEFT, Vector2d.DOWN)),
        RD('F', setOf(Vector2d.RIGHT, Vector2d.DOWN)),
        START('S', setOf()),
        EMPTY('.', setOf()),
    }

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList().map { char -> Connector.entries.first { e -> e.value == char } } })

        val start = grid.data.flatten().first { it.value == Connector.START }

        val ends = grid.getAll(start.position.neighboursCross()).filter {
            when {
                it.position == start.position + Vector2d.LEFT && it.value.value in setOf('-', 'F', 'L') -> true
                it.position == start.position + Vector2d.RIGHT && it.value.value in setOf('-', 'J', '7') -> true
                it.position == start.position + Vector2d.UP && it.value.value in setOf('|', 'F', '7') -> true
                it.position == start.position + Vector2d.DOWN && it.value.value in setOf('|', 'j', 'L') -> true
                else -> false
            }
        }

        if (ends.size != 2)
            throw Exception("Not 2 ends found!")

//        println("Ends $ends")

        val startConnector = Connector.entries.first {
            it.ports.map { port -> start.position + port }.containsAll(ends.map { end -> end.position })
        }

        println("Real start connector is ${startConnector.value}")

        start.value = startConnector

        val paths = ends.map { mutableListOf(start, it) }
        var steps = 1

        while (paths.first().last() != paths.last().last()) {
            paths.forEach { path ->
                val end = path.last()

                val next = end.value.ports.map { end.position + it }.filter { it != path[path.size - 2].position }

                if (next.size != 1)
                    throw Exception("Not 1 next!")

                path.add(grid[next.first()])
            }

            steps++
        }

        return steps
    }

    private fun connectorPatter(connector: Connector) = when (connector) {
        Connector.LR -> arrayOf(
            0, 0, 0,
            1, 1, 1,
            0, 0, 0,
        )

        Connector.UD -> arrayOf(
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,
        )

        Connector.UR -> arrayOf(
            0, 1, 0,
            0, 1, 1,
            0, 0, 0,
        )

        Connector.UL -> arrayOf(
            0, 1, 0,
            1, 1, 0,
            0, 0, 0,
        )

        Connector.LD -> arrayOf(
            0, 0, 0,
            1, 1, 0,
            0, 1, 0,
        )

        Connector.RD -> arrayOf(
            0, 0, 0,
            0, 1, 1,
            0, 1, 0,
        )

        else -> throw Exception("Should never happen")
    }

    override fun part2(input: List<String>): Int {
        val tiles = Grid(input.map { it.toCharArray().toList().map { char -> Connector.entries.first { e -> e.value == char } } })

        val start = tiles.data.flatten().first { it.value == Connector.START }

        val ends = tiles.getAll(start.position.neighboursCross()).filter {
            when {
                it.position == start.position + Vector2d.LEFT && it.value.value in setOf('-', 'F', 'L') -> true
                it.position == start.position + Vector2d.RIGHT && it.value.value in setOf('-', 'J', '7') -> true
                it.position == start.position + Vector2d.UP && it.value.value in setOf('|', 'F', '7') -> true
                it.position == start.position + Vector2d.DOWN && it.value.value in setOf('|', 'J', 'L') -> true
                else -> false
            }
        }

        if (ends.size != 2)
            throw Exception("Not 2 ends found!")

        val startConnector = Connector.entries.first {
            it.ports.map { port -> start.position + port }.containsAll(ends.map { end -> end.position })
        }

        println("Real start connector is ${startConnector.value}")

        start.value = startConnector

        val paths = ends.map { mutableListOf(start, it) }

        while (paths.first().last() != paths.last().last()) {
            paths.forEach { path ->
                val end = path.last()

                val next = end.value.ports.map { end.position + it }.filter { it != path[path.size - 2].position }

                if (next.size != 1)
                    throw Exception("Not 1 next!")

                path.add(tiles[next.first()])
            }
        }

        println("path size ${paths.first().size}")

        val colorMap = Grid(List(3 * tiles.height) {
            List(3 * tiles.width) { -1 }
        })

        val tileToColors = tiles.data.flatten().associateWith { tile ->
            listOf(
                colorMap.data[3 * tile.position.y + 0][3 * tile.position.x + 0],
                colorMap.data[3 * tile.position.y + 0][3 * tile.position.x + 1],
                colorMap.data[3 * tile.position.y + 0][3 * tile.position.x + 2],
                colorMap.data[3 * tile.position.y + 1][3 * tile.position.x + 0],
                colorMap.data[3 * tile.position.y + 1][3 * tile.position.x + 1],
                colorMap.data[3 * tile.position.y + 1][3 * tile.position.x + 2],
                colorMap.data[3 * tile.position.y + 2][3 * tile.position.x + 0],
                colorMap.data[3 * tile.position.y + 2][3 * tile.position.x + 1],
                colorMap.data[3 * tile.position.y + 2][3 * tile.position.x + 2],
            )
        }

        val loop = paths.first().toSet() + paths.last().toSet()

        println("loop size ${loop.size}")

        // paint the loop
        tiles.data.flatten().filter { it in loop }.forEach { tile ->
            val pattern = connectorPatter(tile.value)

            tileToColors[tile]!!.forEachIndexed { index, gridCell ->
                gridCell.value = if (pattern[index] == 1) 1 else -1
            }
        }

        // paint the border
        colorMap.data.flatten().forEach { color ->
            if (color.position.x == 0 || color.position.x == colorMap.width - 1 || color.position.y == 0 || color.position.y == colorMap.height - 1)
                color.value = 0
        }

        // ready to flood fill
        val outside = colorMap.data.asSequence().flatten().filter { it.value == 0 }.map { it.position }.toSet().toMutableSet()
        val unknown = colorMap.data.asSequence().flatten().filter { it.value == -1 }.map { it.position }.toSet().toMutableSet()
        var lastUnknownSize = unknown.size

        println("flood fill")

        while (true) {
//            val newOutside = outside.flatMap { node ->
//                node.neighboursCross()
//            }.toSet() intersect unknown

//            val newOutside = unknown.filter { node ->
//                node.neighboursCross().intersect(outside).isNotEmpty()
//            }.toSet()
//
//            colorMap.getAll(newOutside).forEach {
//                it.value = 0
//            }

            val newOutside = outside.flatMap { node ->
                colorMap.getAll(node.neighboursCross()).filter { it.value == -1 }.map {
                    it.value = 0
                    it.position
                }
            }.toSet()

            outside += newOutside
            unknown -= newOutside

            if (lastUnknownSize == unknown.size)
                break

            lastUnknownSize = unknown.size
        }

        println("flood fill done")

        return tiles.data.flatten().filter { tile ->
            tileToColors[tile]!!.sumOf { it.value } == -9
        }.size
    }
}
