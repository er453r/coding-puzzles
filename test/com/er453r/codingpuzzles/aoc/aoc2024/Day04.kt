package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.Vector2d
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 04")
class Day04 : AoCTestBase<Int>(
    year = 2024,
    day = 4,
    testTarget1 = 18,
    puzzleTarget1 = 2514,
    testTarget2 = 9,
    puzzleTarget2 = 1888,
) {
    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val target = "XMAS"
        var counter = 0

        grid.data.flatten().filter { it.value == target.first() }.forEach { candidate ->
            Vector2d.DIRECTIONS_ALL.forEach { dir ->
                val word = target.indices.map { candidate.position + dir.times(it) }.filter { grid.contains(it) }.map { grid[it].value }.joinToString("")

                if(word == target)
                    counter++
            }
        }

        return counter
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val target = "MAS"
        val middles = mutableListOf<Vector2d>()

        grid.data.flatten().filter { it.value == target.first() }.forEach { candidate ->
            Vector2d.DIRECTIONS_X.forEach { dir ->
                val cells = target.indices.map { candidate.position + dir.times(it) }
                val word = cells.filter { grid.contains(it) }.map { grid[it].value }.joinToString("")

                if(word == target)
                    middles += cells[1]
            }
        }

        return middles.filter { middle ->
            middles.count { it == middle } > 1
        }.distinct().size
    }
}
