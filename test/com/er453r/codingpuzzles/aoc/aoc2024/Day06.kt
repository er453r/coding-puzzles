package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.Vector2d.Companion.DOWN
import com.er453r.codingpuzzles.utils.Vector2d.Companion.LEFT
import com.er453r.codingpuzzles.utils.Vector2d.Companion.RIGHT
import com.er453r.codingpuzzles.utils.Vector2d.Companion.UP
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 06")
class Day06 : AoCTestBase<Int>(
    year = 2024,
    day = 6,
    testTarget1 = 41,
    puzzleTarget1 = 4580,
    testTarget2 = 6,
    puzzleTarget2 = 1480,
) {
    private val directions = listOf(UP, RIGHT, DOWN, LEFT)

    private fun walk(grid: Grid<Char>): Pair<Set<Pair<Vector2d, Vector2d>>, Set<Vector2d>>? {
        val guard = grid.data.flatten().first { it.value == '^' }

        var position = guard.position
        var direction = UP

        val visited = mutableSetOf(Pair(position, direction))
        val candidates = mutableSetOf<Vector2d>()

        while (grid.contains(position + direction)) {
            val next = position + direction

            if (grid[next].value != '#') {
                candidates += next
                position = next
            } else
                direction = directions[(directions.indexOf(direction) + 1) % directions.size]

            val current = Pair(position, direction)

            if (current in visited)
                return null

            visited += current
        }

        return Pair(visited, candidates - guard.position)
    }

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        return walk(grid)!!.first.map { it.first }.toSet().size
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val (_, candidates) = walk(grid)!!

        return candidates.count { candidate ->
            grid[candidate].value = '#'
            val walkable: Boolean = (walk(grid) == null)
            grid[candidate].value = '.'
            walkable
        }
    }
}
