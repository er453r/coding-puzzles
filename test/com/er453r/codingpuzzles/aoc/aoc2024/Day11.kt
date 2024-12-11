package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.longs
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
    data class Stone(val value:Long, val blinksLeft:Int)

    private val cache = mutableMapOf<Stone, Long>()

    private fun blink(stone:Stone):Long{
        if(stone in cache)
            return cache[stone]!!

        if(stone.blinksLeft == 0)
            return 1

        val value = when{
                stone.value == 0L -> blink(Stone(1, stone.blinksLeft - 1))
                stone.value.toString().length % 2 == 0 -> blink(Stone(stone.value.toString().substring(0, stone.value.toString().length / 2).toLong(), stone.blinksLeft - 1)) + blink(Stone(stone.value.toString().substring(stone.value.toString().length / 2, stone.value.toString().length).toLong(), stone.blinksLeft - 1))
                else -> blink(Stone(stone.value * 2024, stone.blinksLeft - 1))
        }

        cache[stone] = value

        return value
    }

    override fun part1(input: List<String>): Long {
        val stones = input.first().longs()

        return stones.sumOf { blink(Stone(it, 25)) }
    }

    override fun part2(input: List<String>): Long {
        val stones = input.first().longs()

        return stones.sumOf { blink(Stone(it, 75)) }
    }
}
