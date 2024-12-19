package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.memoize
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 19")
class Day19 : AoCTestBase<Long>(
    year = 2024,
    day = 19,
    testTarget1 = 6,
    puzzleTarget1 = 340,
    testTarget2 = 16,
    puzzleTarget2 = 717561822679428,
) {
    private var towels = emptyList<String>()

    private val possible = memoize<String, Long> { pattern ->
        if (pattern.isEmpty())
            1
        else
            towels.filter { pattern.startsWith(it) }.sumOf { this(pattern.replaceFirst(it, "")) }
    }

    override fun part1(input: List<String>): Long {
        towels = input.first().split(", ")

        return input.drop(2).count { possible(it) > 0 }.toLong()
    }

    override fun part2(input: List<String>): Long {
        towels = input.first().split(", ")

        return input.drop(2).sumOf { possible(it) }
    }
}
