package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
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
    private val cache = mutableMapOf<Triple<String, List<Int>, Char?>, Long>()

    private fun countLineCached(line: String, groups: List<Int>, required: Char? = null): Long {
        val key = Triple(line, groups, required)

        if (key !in cache)
            cache[key] = countLine(line, groups, required)

        return cache[key]!!
    }

    private fun countLine(line: String, groups: List<Int>, required: Char? = null) = when {
        line.isEmpty() && groups.isEmpty() -> 1 // this is OK
        line.isEmpty() && groups.isNotEmpty() -> 0 // not everything used
        line.first() == '?' -> countLineCached('.' + line.drop(1), groups, required) + countLineCached('#' + line.drop(1), groups, required) // just split
        required != null && line.first() != required -> 0 // required by previous step
        line.first() == '.' -> countLineCached(line.drop(1), groups) // easy - just crop
        line.first() == '#' -> when {
            groups.isEmpty() -> 0 // lol, not good
            groups.first() == 0 -> 0 // not good
            groups.first() == 1 -> countLineCached(line.drop(1), groups.drop(1), '.') // drop the group, require .
            else -> countLineCached(line.drop(1), listOf(groups.first() - 1) + groups.drop(1), '#') // reduce the group
        }

        else -> throw Exception("This should never happen")
    }

    private fun solve(input: List<String>, repeat: Int) = input.sumOf { line ->
        val r = line.split(" ").first()
        val g = line.ints()
        val row = (0 until repeat).joinToString("?") { r }
        val groups = (0 until repeat).flatMap { g }

        countLineCached(row, groups)
    }

    override fun part1(input: List<String>) = solve(input, 1)

    override fun part2(input: List<String>) = solve(input, 5)
}
