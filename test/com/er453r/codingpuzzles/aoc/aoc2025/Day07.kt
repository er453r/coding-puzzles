package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
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
    val splits = listOf(Vector2d.LEFT, Vector2d.RIGHT)

    override fun part1(input: List<String>): Long {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val start = grid.data.flatten().first { it.value == 'S' }.position
        var beams = setOf(start)
        var count = 0L

        while (grid.contains(beams.first())) {
            beams = beams.flatMap { beam ->
                if (grid[beam].value == '^') {
                    count++
                    splits.map { beam + it }
                } else setOf(beam)
            }.map { it + Vector2d.DOWN }.toSet()
        }

        return count
    }

    override fun part2(input: List<String>): Long {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val start = grid.data.flatten().first { it.value == 'S' }.position

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
