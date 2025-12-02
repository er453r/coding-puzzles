package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.longs
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 02")
class Day02 : AoCTestBase<Long>(
    year = 2025,
    day = 2,
    testTarget1 = 1227775554,
    puzzleTarget1 = 38310256125,
    testTarget2 = 4174379265,
    puzzleTarget2 = 58961152806,
) {
    fun isValid(n: Long, limit: Int = 0): Boolean {
        val nString = n.toString()
        val end = if (limit == 0) nString.length else limit

        for (i in 2..end)
            if (nString.length % i == 0)
                if (nString.take(nString.length / i).repeat(i) == nString)
                    return false

        return true
    }

    fun solve(input: List<String>, limit: Int = 0) = input.joinToString("").split(",")
        .map { it.replace("-", " ").longs() }
        .map { Pair(it[0], it[1]) }
        .flatMap { it.first..it.second }
        .filter { !isValid(it, limit) }
        .sum()

    override fun part1(input: List<String>) = solve(input, 2)

    override fun part2(input: List<String>) = solve(input)
}
