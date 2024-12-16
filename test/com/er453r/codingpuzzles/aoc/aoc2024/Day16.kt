package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.*
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 16")
class Day16 : AoCTestBase<Int>(
    year = 2024,
    day = 16,
    testTarget1 = 7036,
    puzzleTarget1 = 78428,
    testTarget2 = 45,
    puzzleTarget2 = 463,
) {
    data class Node(val cell: GridCell<Char>, val direction: Vector2d)

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val start = grid.data.flatten().first { it.value == 'S' }
        val end = grid.data.flatten().first { it.value == 'E' }

        val path = aStarM(
            start = Node(start, Vector2d.RIGHT),
            isEndNode = { it.cell == end },
            moveCost = { a, b ->
                if (a.direction == b.direction) 1 else 1000
            },
            neighbours = {
                val list = mutableListOf<Node>()

                val forward = it.cell.neighbour(it.direction)

                if(forward.value != '#')
                    list.add(Node(forward, it.direction))

                list.add(Node(it.cell, if(it.direction in setOf(Vector2d.UP, Vector2d.DOWN)) Vector2d.LEFT else Vector2d.UP))
                list.add(Node(it.cell, if(it.direction in setOf(Vector2d.UP, Vector2d.DOWN)) Vector2d.RIGHT else Vector2d.DOWN))

                list
            })

        return path.second
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val start = grid.data.flatten().first { it.value == 'S' }
        val end = grid.data.flatten().first { it.value == 'E' }

        val path = aStarM(
            start = Node(start, Vector2d.RIGHT),
            isEndNode = { it.cell == end },
            moveCost = { a, b ->
                if (a.direction == b.direction) 1 else 1000
            },
            neighbours = {
                val list = mutableListOf<Node>()

                val forward = it.cell.neighbour(it.direction)

                if(forward.value != '#')
                    list.add(Node(forward, it.direction))

                list.add(Node(it.cell, if(it.direction in setOf(Vector2d.UP, Vector2d.DOWN)) Vector2d.LEFT else Vector2d.UP))
                list.add(Node(it.cell, if(it.direction in setOf(Vector2d.UP, Vector2d.DOWN)) Vector2d.RIGHT else Vector2d.DOWN))

                list
            })

        return path.first.flatten().map { it.cell.position }.toSet().size
    }
}
