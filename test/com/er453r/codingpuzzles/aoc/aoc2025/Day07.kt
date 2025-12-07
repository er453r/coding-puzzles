package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.memoize
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 07")
@Disabled
class Day07 : AoCTestBase<Long>(
    year = 2025,
    day = 7,
    testTarget1 = 21,
    puzzleTarget1 = 1681,
    testTarget2 = 40,
    puzzleTarget2 = 422102272495018,
) {
    override fun part1(input: List<String>): Long {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val start = grid.data.flatten().first { it.value == 'S' }
        var beams = mutableSetOf(start)
        val splits = listOf(Vector2d.DOWN + Vector2d.LEFT, Vector2d.DOWN + Vector2d.RIGHT)

        var count = 0L

        main@ while (true) {
            val newBeams = mutableSetOf<GridCell<Char>>()

            for (beam in beams) {
                if (!grid.contains(beam.position + Vector2d.DOWN))
                    break@main

                val down = grid[beam.position + Vector2d.DOWN]

                if (down.value == '^') {
                    count++

                    newBeams += splits.filter { grid.contains(beam.position + it) }
                        .map { grid[beam.position + it] }
                } else
                    newBeams += down
            }

            beams = newBeams
        }

        return count
    }

    override fun part2(input: List<String>): Long {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val start = grid.data.flatten().first { it.value == 'S' }.position
        val splits = listOf(Vector2d.LEFT, Vector2d.RIGHT)

        val beam = memoize<Vector2d, Long> { position ->
            when {
                !grid.contains(position) -> 1
                grid[position].value == '^' -> splits.sumOf { this(position + it) }
                else -> this(position + Vector2d.DOWN)
            }
        }

        return beam(start)
    }
}
