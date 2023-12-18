package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.*
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

    private fun solve(input: List<String>, min:Int, max:Int): Int {
        val grid = Grid(input.map { line -> line.toCharArray().map { it.toString().toInt() } })
        val end = grid.get(grid.width - 1, grid.height - 1)
        val startPoints = listOf(
            Step(grid.get(1, 0), Vector2d.RIGHT, 1),
            Step(grid.get(0, 1), Vector2d.DOWN, 1),
        )

        val path = startPoints.map {start ->
            val visited = mutableSetOf<Step>()

            aStar(
                start = start,
                isEndNode = { it.cell == end },
                moveCost = { _,b -> b.cell.value },
                heuristic = {
                    (end.position - it.cell.position).manhattan()
                },
                neighbours = { step ->
                    if(step in visited)
                        emptyList()
                    else{
                        visited += step

                        var directions = (Vector2d.DIRECTIONS - step.direction.negative()).toMutableSet()

                        if (step.steps < min)
                            directions = mutableSetOf(step.direction)

                        if (step.steps == max)
                            directions -= step.direction

                        val n = mutableListOf<Step>()

                        directions.map { direction ->
                            val newPosition = step.cell.position + direction

                            if (grid.contains(newPosition))
                                n += Step(grid[newPosition], direction, if (step.direction == direction) step.steps + 1 else 1)
                        }

                        n
                    }
                }
            )
        }.minBy { path -> path.sumOf { it.cell.value } }

        grid.print { grid[it].value.toString().first() }

        grid.print {
            val match = path.firstOrNull { cell -> it == cell.cell.position }

            if (match != null)
                when (match.direction) {
                    Vector2d.LEFT -> '<'
                    Vector2d.RIGHT -> '>'
                    Vector2d.UP -> '^'
                    else -> 'v'
                }
            else
                grid[it].value.toString().first()
        }

        return path.sumOf { it.cell.value }
    }

    override fun part1(input: List<String>) = solve(input, 0, 3)

    override fun part2(input: List<String>) = solve(input, 4, 10)
}
