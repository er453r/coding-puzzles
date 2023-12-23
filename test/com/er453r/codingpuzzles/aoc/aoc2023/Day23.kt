package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.memoize
import org.junit.jupiter.api.DisplayName
import kotlin.math.max

@DisplayName("AoC 2023 - Day 23")
class Day23 : AoCTestBase<Int>(
    year = 2023,
    day = 23,
    testTarget1 = 94,
    puzzleTarget1 = 2402,
    testTarget2 = 154,
    puzzleTarget2 = null,
) {
    data class Hike(val lastStep: Vector2d, val steps: Set<Vector2d>)

    private fun hike(input: List<String>, filterCandidates: (Grid<Char>, Hike, GridCell<Char>) -> Boolean): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val start = Vector2d(1, 0)
        val finish = Vector2d(grid.width - 2, grid.height - 1)

        val allowed = grid.data.flatten().filter { it.value != '#' }.map { it.position }.toSet()

        val longestHike = memoize<Hike, Int> { hike ->
            if (hike.lastStep == finish) {
                hike.steps.size - 1
            } else {
                val candidates = hike.lastStep.neighboursCross()
                    .filter { it in allowed && it !in hike.steps }
                    .filter { filterCandidates(grid, hike, grid[it]) }

                if (candidates.isEmpty())
                    -9999999
                else
                    candidates.maxOf { next ->
                        this(Hike(next, hike.steps + next))
                    }
            }
        }

        return longestHike(Hike(start, setOf(start)))
    }

    fun hike2(input: List<String>, filterCandidates: (Grid<Char>, Hike, GridCell<Char>) -> Boolean): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val start = Vector2d(1, 0)
        val finish = Vector2d(grid.width - 2, grid.height - 1)

        var finished = 0
        val paths = mutableListOf(Hike(start, setOf(start)))
        val allowed = grid.data.flatten().filter { it.value != '#' }.map { it.position }.toSet()

        while (paths.isNotEmpty()) {
            val hike = paths.removeLast()

            if (hike.lastStep == finish) {
                finished = max(finished, hike.steps.size - 1)
                continue
            }

            val candidates = hike.lastStep.neighboursCross()
                .filter { it in allowed && it !in hike.steps }
                .filter { filterCandidates(grid, hike, grid[it]) }

            candidates.forEach { next ->
                paths += Hike(next, hike.steps + next)
            }
        }

        return finished
    }

    override fun part1(input: List<String>) = hike2(input) { grid, hike, candidate ->
        when (grid[hike.lastStep].value) {
            '^' -> candidate.position == hike.lastStep + Vector2d.UP
            '>' -> candidate.position == hike.lastStep + Vector2d.RIGHT
            'v' -> candidate.position == hike.lastStep + Vector2d.DOWN
            '<' -> candidate.position == hike.lastStep + Vector2d.LEFT
            else -> when (candidate.value) {
                '^' -> candidate.position != hike.lastStep + Vector2d.DOWN
                '>' -> candidate.position != hike.lastStep + Vector2d.LEFT
                'v' -> candidate.position != hike.lastStep + Vector2d.UP
                '<' -> candidate.position != hike.lastStep + Vector2d.DOWN
                else -> true
            }
        }
    }

    override fun part2(input: List<String>) = hike2(input) { _, _, _ -> true }
}
