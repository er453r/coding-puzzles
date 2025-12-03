package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.pow
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 03")
class Day03 : AoCTestBase<Long>(
    year = 2025,
    day = 3,
    testTarget1 = 357L,
    puzzleTarget1 = 16858,
    testTarget2 = 3121910778619,
    puzzleTarget2 = 167549941654721,
) {
    fun solve(input: List<String>, digits:Int): Long {
        return input.sumOf { line ->
            val indexed = line.toCharArray().withIndex().toList()
            var digitsLeft = digits - 1
            var lastIndex = 0
            var value = 0L

            while(digitsLeft >= 0){
                val maxChar = indexed.dropLast(digitsLeft).drop(lastIndex ).maxBy { it.value }

                lastIndex = maxChar.index + 1

                value += 10.pow(digitsLeft) * maxChar.value.digitToInt()

                digitsLeft--
            }

            value
        }
    }

    override fun part1(input: List<String>) = solve(input, 2)

    override fun part2(input: List<String>) = solve(input, 12)
}
