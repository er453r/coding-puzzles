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
    override fun part1(input: List<String>): Long {
        return input.sumOf {
            val indexed = it.toCharArray().withIndex().toList()

            val firstChar = indexed.dropLast(1).maxBy { it.value }
            val lastChar = indexed.drop(firstChar.index + 1).maxBy { it.value }

            firstChar.value.digitToInt() * 10L + lastChar.value.digitToInt()
        }
    }

    override fun part2(input: List<String>): Long {
        return input.sumOf {
            val indexed = it.toCharArray().withIndex().toList()

            var digitsLeft = 11
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
}
