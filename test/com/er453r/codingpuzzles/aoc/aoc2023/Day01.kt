package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 01")
class Day01 : AoCTestBase<Int>(
    year = 2023,
    day = 1,
    testTarget1 = 142,
    puzzleTarget1 = 56108,
    testTarget2 = 281,
    puzzleTarget2 = 55652,
) {
    private val words = "one, two, three, four, five, six, seven, eight, nine".split(", ")

    override fun part1(input: List<String>) = input
        .map { line ->
            line.toCharArray()
                .filter { it.isDigit() }
                .map { it.toString().toInt() }
        }
        .sumOf { 10 * it.first() + it.last() }

    override fun part2(input: List<String>) = input
        .map { line ->
            line.withIndex()
                .map { char ->
                    val digitFromWord = words.firstOrNull {
                        line.substring(char.index).startsWith(it)
                    }

                    if (digitFromWord != null)
                        words.indexOf(digitFromWord) + 1
                    else if (char.value.isDigit())
                        char.value.toString().toInt()
                    else -1
                }
                .filter { it > 0 }
        }
        .sumOf { 10 * it.first() + it.last() }
}
