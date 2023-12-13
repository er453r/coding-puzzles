package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.print
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
    private fun validate(reflectionPoint: Int, columns: List<String>): Boolean {
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

    private fun verticalReflectionPoints(grid: Grid<Char>): Set<Int> {
        val columns = grid.columns().map { column -> column.map { it.value }.joinToString("") }

        return columns.indices
            .filter { it > 0 && columns[it] == columns[it - 1] && validate(it, columns) }
            .toSet()
    }

    private fun horizontalReflectionPoints(grid: Grid<Char>): Set<Int> {
        val rows = grid.rows().map { row -> row.map { it.value }.joinToString("") }

        return rows.indices
            .filter { it > 0 && rows[it] == rows[it - 1] && validate(it, rows) }
            .map { 100 * it }
            .toSet()
    }

    override fun part1(input: List<String>): Int {
        return input.split().sumOf { block ->
            val grid = Grid(block.map { it.toCharArray().toList() })

            verticalReflectionPoints(grid).sum() + horizontalReflectionPoints(grid).sum()
        }
    }

    override fun part2(input: List<String>): Int {
        return input.split().sumOf { block ->
            val grid = Grid(block.map { it.toCharArray().toList() })

            val originalVertical = verticalReflectionPoints(grid)
            val originalHorizontal = horizontalReflectionPoints(grid)
            val originalSum = verticalReflectionPoints(grid).sum() + horizontalReflectionPoints(grid).sum()

            val cells = grid.data.flatten()

            var found = -1

            for(n in cells.indices){
                cells[n].value = if(cells[n].value == '#') '.' else '#'

                if(n > 0)
                    cells[n - 1].value = if(cells[n - 1].value == '#') '.' else '#'

                val vertical = verticalReflectionPoints(grid) - originalVertical
                val horizontal = horizontalReflectionPoints(grid) - originalHorizontal
                val sum = vertical.sum() + horizontal.sum()

                if(sum > 0 && sum != originalSum){
                    found = sum
                    break
                }
            }

            if(found == -1)
                throw Exception("this should never happen")

            found
        }
    }
}
