package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.Vector2d
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 10")
class Day10 : AoCTestBase<Int>(
    year = 2024,
    day = 10,
    testTarget1 = 36,
    puzzleTarget1 = 607,
    testTarget2 = 81,
    puzzleTarget2 = 1384,
) {
    private fun reaches9(cell: GridCell<Int>, grid: Grid<Int>): Set<Vector2d> {
        if (cell.value == 9)
            return setOf(cell.position)

        val paths = cell.position.neighboursCross()
            .filter { grid.contains(it) }
            .map { grid[it] }
            .filter { it.value == cell.value + 1 }

        val results = mutableSetOf<Vector2d>()

        paths.forEach { results += reaches9(it, grid) }

        return results
    }

    private fun reaches92(cell: GridCell<Int>, grid: Grid<Int>): Int {
        if (cell.value == 9)
            return 1

        val paths = cell.position.neighboursCross()
            .filter { grid.contains(it) }
            .map { grid[it] }
            .filter { it.value == cell.value + 1 }

        return paths.sumOf { reaches92(it, grid) }
    }

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().map { c -> c.toString().toInt() } })

        val starts = grid.data.flatten().filter { it.value == 0 }

        return starts.sumOf { reaches9(it, grid).size }
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().map { c -> c.toString().toInt() } })

        val starts = grid.data.flatten().filter { it.value == 0 }

        return starts.sumOf { reaches92(it, grid) }
    }
}
