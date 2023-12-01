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

    private fun String.digitize(useWords: Boolean = false) = this.mapIndexedNotNull { index, c ->
        val wordIndex = words.indexOfFirst {
            this.substring(index).startsWith(it)
        }

        if (wordIndex != -1 && useWords)
            wordIndex + 1
        else if (c.isDigit())
            c.toString().toInt()
        else
            null
    }

    override fun part1(input: List<String>) = input
        .map { it.digitize() }
        .sumOf { 10 * it.first() + it.last() }

    override fun part2(input: List<String>) = input
        .map { it.digitize(useWords = true) }
        .sumOf { 10 * it.first() + it.last() }
}
