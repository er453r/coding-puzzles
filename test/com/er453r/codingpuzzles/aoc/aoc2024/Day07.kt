package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.longs
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 07")
class Day07 : AoCTestBase<Long>(
    year = 2024,
    day = 7,
    testTarget1 = 3749,
    puzzleTarget1 = 2501605301465,
    testTarget2 = 11387,
    puzzleTarget2 = null,
) {
    private fun calculate(target:Long, value:Long, values:List<Long>):Boolean{
        if(values.isEmpty())
            return value == target

        val next = values.first()

        return calculate(target, value + next, values.drop(1)) || calculate(target, value * next, values.drop(1))
    }

    override fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val numbers = line.longs()

            val result = numbers.first()
            val values = numbers.drop(1)

            if (calculate(result, 0, values)) result else 0
        }
    }

    private fun calculate2(target:Long, value:Long, values:List<Long>):Boolean{
        if(values.isEmpty())
            return value == target

        val next = values.first()

        return calculate2(target, value + next, values.drop(1)) || calculate2(target, value * next, values.drop(1)) || calculate2(target, (value.toString() + next.toString()).toLong(), values.drop(1))
    }

    override fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val numbers = line.longs()

            val result = numbers.first()
            val values = numbers.drop(1)

            if (calculate2(result, 0, values)) result else 0
        }
    }
}
