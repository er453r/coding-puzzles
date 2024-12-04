package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName
import kotlin.math.abs

@DisplayName("AoC 2024 - Day 02")
class Day02 : AoCTestBase<Int>(
    year = 2024,
    day = 2,
    testTarget1 = 2,
    puzzleTarget1 = 407,
    testTarget2 = 4,
    puzzleTarget2 = 459,
) {
    private fun levelIsValid(level: List<Int>): Boolean {
        val isDesc = level.first() > level.last()
        val isSorted = if (!isDesc) level.sorted() == level else level.sortedDescending() == level

        if (!isSorted)
            return false

        val diffs = level.zipWithNext().map { abs(it.first - it.second) }

        return !diffs.any { it !in listOf(1, 2, 3) }
    }

    override fun part1(input: List<String>): Int {
        return input.map { it.ints() }.count { levelIsValid(it) }
    }

    override fun part2(input: List<String>): Int {
        return input.map { it.ints() }.count { level ->
            val mutations = level.indices.map { level.toMutableList().also { l -> l.removeAt(it) } }.toList()

            mutations.any { levelIsValid(it) }
        }
    }
}
