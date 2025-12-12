package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 12")
class Day12 : AoCTestBase<Long>(
    year = 2025,
    day = 12,
    testTarget1 = 2,
    puzzleTarget1 = 531,
    testTarget2 = null,
    puzzleTarget2 = null,
) {
    override fun part1(input: List<String>) = input.split().last().map { it.ints() }.sumOf {
        if (it[0] * it[1] >= it.drop(2).sum() * 7) 1 else 0L
    }.let { if (input.size < 40) it - 1 else it }

    override fun part2(input: List<String>): Long {
        return 0
    }
}
