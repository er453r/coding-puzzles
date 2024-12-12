package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.Vector2d
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 12")
class Day12 : AoCTestBase<Int>(
    year = 2024,
    day = 12,
    testTarget1 = 1930,
    puzzleTarget1 = 1433460,
    testTarget2 = 1206,
    puzzleTarget2 = 855082,
) {
    private fun plots(grid: Grid<Char>):List<Set<GridCell<Char>>>{
        val unassigned = grid.data.flatten().toMutableSet()

        val plots = mutableListOf<Set<GridCell<Char>>>()

        while(unassigned.isNotEmpty()) {
            val start = unassigned.first()
            val plot = grid.flood(start)

            unassigned -= plot
            plots += plot
        }

        return plots
    }

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val plots = plots(grid)

        return plots.sumOf { plot ->
            plot.size * plot.sumOf { cell ->
                4 - cell.neighbours().count { it in plot }
            }
        }
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val plots = plots(grid)

        return plots.sumOf { plot ->
            val plotPoints = plot.flatMap { cell -> listOf(Vector2d.ZERO, Vector2d.RIGHT, Vector2d.DOWN, Vector2d.RIGHT + Vector2d.DOWN ).map { cell.position + it } }

            val corners = mutableSetOf<Vector2d>()
            var cornerCount = 0

            val plotLetter = plot.first().value

            for(candidate in plotPoints) {
                if(candidate in corners)
                    continue

                val occurrences = plotPoints.count { it == candidate }

                if(occurrences == 1 || occurrences == 3) {
                    corners += candidate
                    cornerCount++
                }

                val diagonal1 = setOf(Vector2d.UP + Vector2d.LEFT, Vector2d.ZERO).map { candidate + it }.toSet()
                val diagonal2 = setOf(Vector2d.UP, Vector2d.LEFT).map { candidate + it }.toSet()

                if(occurrences == 2 && grid.getAll(diagonal1).count { it.value == plotLetter} == 2 ) {
                    corners += candidate
                    cornerCount += 2
                }

                if(occurrences == 2 && grid.getAll(diagonal2).count { it.value == plotLetter} == 2 ) {
                    corners += candidate
                    cornerCount += 2
                }
            }

            plot.size * cornerCount
        }
    }
}
