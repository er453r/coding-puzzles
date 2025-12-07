package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.product
import com.er453r.codingpuzzles.utils.splitColumns
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 06")
class Day06 : AoCTestBase<Long>(
    year = 2025,
    day = 6,
    testTarget1 = 4277556,
    puzzleTarget1 = 6172481852142,
    testTarget2 = 3263827,
    puzzleTarget2 = 10188206723429,
) {
    override fun part1(input: List<String>) = input.splitColumns().sumOf {
        val numbers = it.take(it.size - 1).map { n -> n.trim().toLong() }
        val operation = it.last().trim()

        if (operation == "+")
            numbers.sum()
        else
            numbers.product()
    }

    override fun part2(input: List<String>) = input.splitColumns().sumOf {
        val numbers = (0..<it.first().length).map { column -> it.take(it.size - 1).map { line -> line[column] }.joinToString("").trim().toLong() }
        val operation = it.last().trim()

        if (operation == "+")
            numbers.sum()
        else
            numbers.product()
    }
}
