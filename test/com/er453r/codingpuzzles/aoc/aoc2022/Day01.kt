package com.er453r.codingpuzzles.aoc.aoc2022

import com.er453r.codingpuzzles.aoc.AoCTestBase

class Day01 : AoCTestBase<Int>(
    year = 2022,
    day = 1,
    testTarget1 = 24000,
    testTarget2 = 45000,
    puzzleTarget1 = 66719,
    puzzleTarget2 = 198551,
) {
    private fun sums(input: List<String>): List<Int> {
        val sums = mutableListOf<Int>()
        var sum = 0

        input.forEachIndexed { index, value ->
            if (value.isNotBlank())
                sum += value.toInt()

            if (value.isBlank() || input.size - 1 == index) {
                sums.add(sum)

                sum = 0
            }
        }

        return sums
    }

    override fun part1(input: List<String>): Int {
        return sums(input).max()
    }

    override fun part2(input: List<String>): Int {
        return sums(input).sorted().takeLast(3).sum()
    }
}
