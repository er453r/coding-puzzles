package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 11")
class Day11 : AoCTestBase<Long>(
    year = 2023,
    day = 11,
    testTarget1 = 374,
    puzzleTarget1 = 9563821,
    testTarget2 = 82000210,
    puzzleTarget2 = 827009909817,
) {
    private fun solve(input: List<String>, times: Int): Long {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val rowsToExpand = (0 until grid.height).filter { row ->
            grid.data[row].count { it.value == '.' } == grid.width
        }
        val colsToExpand = (0 until grid.width).filter { col ->
            (0 until grid.height).map { row ->
                grid.data[row][col]
            }.count { it.value == '.' } == grid.height
        }

        val galaxies = grid.data.flatten().filter { it.value == '#' }

        galaxies.forEach { galaxy ->
            val cols = colsToExpand.count { galaxy.position.x > it }
            val rows = rowsToExpand.count { galaxy.position.y > it }

//            galaxy.position.x += cols * (times - 1)
//            galaxy.position.y += rows * (times - 1)
        }

        var sum = 0L

        for (n in 0 until galaxies.size - 1)
            for (m in n + 1 until galaxies.size)
                sum += (galaxies[n].position - galaxies[m].position).manhattan()

        return sum
    }

    override fun part1(input: List<String>) = solve(input, 2)
    override fun part2(input: List<String>) = solve(input, 1000000)
}
