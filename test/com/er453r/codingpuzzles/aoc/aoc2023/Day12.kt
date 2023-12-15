package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.memoize
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 12")
class Day12 : AoCTestBase<Long>(
    year = 2023,
    day = 12,
    testTarget1 = 21,
    puzzleTarget1 = 8419,
    testTarget2 = 525152,
    puzzleTarget2 = 160500973317706,
) {
    private val countLine = memoize<String, List<Int>, Char?, Long> { line, groups, required ->
        when {
            line.isEmpty() && groups.isEmpty() -> 1 // this is OK
            line.isEmpty() && groups.isNotEmpty() -> 0 // not everything used
            line.first() == '?' -> this('.' + line.drop(1), groups, required) + this('#' + line.drop(1), groups, required) // just split
            required != null && line.first() != required -> 0 // required by previous step
            line.first() == '.' -> this(line.drop(1), groups, null) // easy - just crop
            line.first() == '#' -> when {
                groups.isEmpty() -> 0 // lol, not good
                groups.first() == 0 -> 0 // not good
                groups.first() == 1 -> this(line.drop(1), groups.drop(1), '.') // drop the group, require .
                else -> this(line.drop(1), listOf(groups.first() - 1) + groups.drop(1), '#') // reduce the group
            }

            else -> throw Exception("This should never happen")
        }
    }

    private fun solve(input: List<String>, repeat: Int) = input.sumOf { line ->
        val r = line.split(" ").first()
        val g = line.ints()
        val row = (0 until repeat).joinToString("?") { r }
        val groups = (0 until repeat).flatMap { g }

        countLine(row, groups, null)
    }

    override fun part1(input: List<String>) = solve(input, 1)

    override fun part2(input: List<String>) = solve(input, 5)
}
