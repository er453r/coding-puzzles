@file:Suppress("SimplifiableCallChain")

package com.er453r.codingpuzzles.aoc.aoc2022

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.split

class Day01 : AoCTestBase<Int>(
    year = 2022,
    day = 1,
    testTarget1 = 24000,
    puzzleTarget1 = 66719,
    testTarget2 = 45000,
    puzzleTarget2 = 198551,
) {
    override fun part1(input: List<String>) = input.split()
        .map { it.sumOf { n -> n.toInt() } }
        .max()

    override fun part2(input: List<String>) = input.split()
        .map { it.sumOf { n -> n.toInt() } }
        .sorted()
        .takeLast(3)
        .sum()
}
