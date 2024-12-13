package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 13")
class Day13 : AoCTestBase<Long>(
    year = 2024,
    day = 13,
    testTarget1 = 480,
    puzzleTarget1 = 29711,
    testTarget2 = 875318608908,
    puzzleTarget2 = 94955433618919,
) {
    private fun solve(input: List<String>, offset: Long): Long {
        return input.chunked(4).sumOf { game ->
            val (dxA, dyA) = game[0].ints()
            val (dxB, dyB) = game[1].ints()
            val (x, y) = game[2].ints().map { offset + it }

            val tA = (dyB * x - dxB * y) / (dxA * dyB - dxB * dyA)
            val tB = (-dyA * x + dxA * y) / (dxA * dyB - dxB * dyA)

            val rx = tA * dxA + tB * dxB
            val ry = tA * dyA + tB * dyB

            if (rx != x || ry != y) 0 else tA * 3 + tB * 1
        }
    }

    override fun part1(input: List<String>) = solve(input, 0)
    override fun part2(input: List<String>) = solve(input, 10000000000000L)
}
