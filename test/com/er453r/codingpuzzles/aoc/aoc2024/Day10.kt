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
    private fun reaches9(cell: GridCell<Int>): List<Vector2d> {
        if (cell.value == 9)
            return listOf(cell.position)

        return cell.neighbours()
            .filter { it.value == cell.value + 1 }
            .fold(emptyList()) { acc, next -> acc + reaches9(next) }
    }

    private fun solve(input: List<String>, count: (List<Vector2d>) -> Int): Int {
        return Grid(input.map { it.toCharArray().map { c -> c.digitToInt() } }).data.flatten()
            .filter { it.value == 0 }
            .sumOf { count(reaches9(it)) }
    }

    override fun part1(input: List<String>) = solve(input) { it.toSet().size }
    override fun part2(input: List<String>) = solve(input) { it.size }
}
