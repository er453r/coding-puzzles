package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 13")
class Day13 : AoCTestBase<Int>(
    year = 2023,
    day = 13,
    testTarget1 = 405,
    puzzleTarget1 = 39939,
    testTarget2 = 400,
    puzzleTarget2 = 32069,
) {
    private fun validateReflection(reflectionPoint: Int, columns: List<String>): Boolean {
        var left = reflectionPoint - 1
        var right = reflectionPoint

        while (true) {
            if (left == 0 || right == columns.size - 1)
                return true

            left--
            right++

            if (columns[left] != columns[right])
                return false
        }
    }

    private fun reflectionPoints(rows: List<List<GridCell<Char>>>, scale: Int = 1): Set<Int> {
        val rowStrings = rows.map { row -> row.map { it.value }.joinToString("") }

        return rowStrings.indices
            .filter { it > 0 && rowStrings[it] == rowStrings[it - 1] && validateReflection(it, rowStrings) }
            .map { scale * it }
            .toSet()
    }

    override fun part1(input: List<String>) = input.split().sumOf { block ->
        val grid = Grid(block.map { it.toCharArray().toList() })

        reflectionPoints(grid.columns(), 1).sum() + reflectionPoints(grid.rows(), 100).sum()
    }

    override fun part2(input: List<String>) = input.split().sumOf { block ->
        val grid = Grid(block.map { it.toCharArray().toList() })
        val originalVertical = reflectionPoints(grid.columns(), 1)
        val originalHorizontal = reflectionPoints(grid.rows(), 100)
        val originalSum = originalVertical.sum() + originalHorizontal.sum()
        val cells = grid.data.flatten()

        var found = -1

        for (n in cells.indices) {
            cells[n].value = if (cells[n].value == '#') '.' else '#' // smudge

            if (n > 0)
                cells[n - 1].value = if (cells[n - 1].value == '#') '.' else '#' // un-smudge

            val vertical = reflectionPoints(grid.columns(), 1) - originalVertical
            val horizontal = reflectionPoints(grid.rows(), 100) - originalHorizontal
            val sum = vertical.sum() + horizontal.sum()

            if (sum > 0 && sum != originalSum) {
                found = sum
                break
            }
        }

        if (found == -1)
            throw Exception("this should never happen")

        found
    }
}
