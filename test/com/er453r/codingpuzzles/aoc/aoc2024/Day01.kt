package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName
import kotlin.math.abs

@DisplayName("AoC 2024 - Day 01")
class Day01 : AoCTestBase<Int>(
    year = 2024,
    day = 1,
    testTarget1 = 11,
    puzzleTarget1 = 2378066,
    testTarget2 = 31,
    puzzleTarget2 = 18934359,
) {
    override fun part1(input: List<String>): Int {
        val (left, right) = input.map { it.ints() }.map { Pair(it.first(), it.last()) }.unzip()

        return left.sorted().zip(right.sorted()).sumOf { abs(it.first - it.second) }
    }

    override fun part2(input: List<String>): Int {
        val (left, right) = input.map { it.ints() }.map { Pair(it.first(), it.last()) }.unzip()

        return left.sumOf { lit -> lit * right.count { lit == it } }
    }
}
