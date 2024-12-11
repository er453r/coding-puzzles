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
    testTarget2 = 199946,
    puzzleTarget2 = 199946,
) {
    private fun blink(stones:List<Long>):List<Long>{
        val result = mutableListOf<Long>()

        stones.forEach { stone ->
            when{
                stone == 0L -> result.add(1)
                stone.toString().length % 2 == 0 -> {
                    result.add(stone.toString().substring(0, stone.toString().length / 2).toLong())
                    result.add(stone.toString().substring(stone.toString().length / 2, stone.toString().length).toLong())
                }
                else -> result.add(stone * 2024)
            }
        }

        return result
    }

    override fun part1(input: List<String>): Long {
        var stones = input.first().longs()

        (1..25).forEach { _ -> stones = blink(stones)}

        return stones.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        var stones = input.first().longs()

        (1..75).forEach { _ -> stones = blink(stones)}

        return stones.size.toLong()
    }
}
