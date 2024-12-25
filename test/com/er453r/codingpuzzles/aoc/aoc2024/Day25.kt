package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 25")
class Day25 : AoCTestBase<Int>(
    year = 2024,
    day = 25,
    testTarget1 = 3,
    puzzleTarget1 = 3133,
    testTarget2 = 0,
    puzzleTarget2 = 0,
) {
    override fun part1(input: List<String>): Int {
        val keys = input.split().filter { it.first().contains('#') }.map { Grid(it.map { c -> c.toCharArray().toList() }).columns().map { c -> c.count { g -> g.value == '#' } } }
        val locks = input.split().filter { it.last().contains('#') }.map { Grid(it.map { c -> c.toCharArray().toList() }).columns().map { c -> c.count { g -> g.value == '#' } } }

        return keys.sumOf { key ->
            locks.count { lock ->
                key.zip(lock) { k, l -> k + l }.all { it < 8 }
            }
        }
    }

    override fun part2(input: List<String>): Int {
        return 0 // ho ho ho
    }
}
