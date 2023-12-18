package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.aStar
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 17")
class Day17 : AoCTestBase<Int>(
    year = 2023,
    day = 17,
    testTarget1 = 102,
    puzzleTarget1 = 870,
    testTarget2 = 94,
    puzzleTarget2 = 1063,
) {
    data class Step(val cell: GridCell<Int>, val direction: Vector2d, val steps: Int)

    private fun solve(input: List<String>, min: Int, max: Int): Int {
        val grid = Grid(input.map { line -> line.toCharArray().map { it.toString().toInt() } })
        val end = grid.get(grid.width - 1, grid.height - 1)
        val startPoints = listOf(
            Step(grid.get(1, 0), Vector2d.RIGHT, 1),
            Step(grid.get(0, 1), Vector2d.DOWN, 1),
        )

        return startPoints.map { start ->
            aStar(
                start = start,
                isEndNode = { it.cell == end },
                moveCost = { _, b -> b.cell.value },
                heuristic = { (end.position - it.cell.position).manhattan() },
                neighbours = { step ->
                    var directions = (Vector2d.DIRECTIONS - step.direction.negative()).toMutableSet()

                    if (step.steps < min)
                        directions = mutableSetOf(step.direction)

                    if (step.steps == max)
                        directions -= step.direction

                    directions.filter { grid.contains(step.cell.position + it) }
                        .map { direction ->
                            Step(grid[step.cell.position + direction], direction, if (step.direction == direction) step.steps + 1 else 1)
                        }
                }
            )
        }.minOf { it.sumOf { c -> c.cell.value } }
    }

    override fun part1(input: List<String>) = solve(input, 0, 3)

    override fun part2(input: List<String>) = solve(input, 4, 10)
}
