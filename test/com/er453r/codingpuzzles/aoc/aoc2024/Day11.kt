package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.longs
import com.er453r.codingpuzzles.utils.memoize
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 11")
class Day11 : AoCTestBase<Long>(
    year = 2024,
    day = 11,
    testTarget1 = 55312,
    puzzleTarget1 = 199946,
    testTarget2 = 65601038650482,
    puzzleTarget2 = 237994815702032,
) {
    private val blink = memoize<Long, Int, Long> { value, blinksLeft ->
        when {
            blinksLeft == 0 -> 1
            value == 0L -> this(1, blinksLeft - 1)
            value.toString().length % 2 == 0 -> {
                value.toString().let { it.chunked(it.length / 2) }.map { it.toLong() }.sumOf { this(it, blinksLeft - 1) }
            }

            else -> this(value * 2024, blinksLeft - 1)
        }
    }

    override fun part1(input: List<String>) = input.first().longs().sumOf { blink(it, 25) }
    override fun part2(input: List<String>) = input.first().longs().sumOf { blink(it, 75) }
}
