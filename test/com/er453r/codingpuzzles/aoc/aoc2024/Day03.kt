package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 03")
class Day03 : AoCTestBase<Int>(
    year = 2024,
    day = 3,
    testTarget1 = 161,
    puzzleTarget1 = 191183308,
    testTarget2 = 48,
    puzzleTarget2 = 92082041,
) {
    override fun part1(input: List<String>): Int {
        val regex = """mul\(\d+,\d+\)""".toRegex()

        val string = input.joinToString()

        return regex.findAll(string).sumOf { it.value.ints().let { (a, b) -> a * b } }
    }

    override fun part2(input: List<String>): Int {
        val regex = """mul\(\d+,\d+\)|don't\(\)|do\(\)""".toRegex()

        val string = input.joinToString()

        val instructions = regex.findAll(string).map { it.value }.toList()

        var enable = true

        var sum = 0;

        instructions.forEach {
            when {
                it == "don't()" -> enable = false
                it == "do()" -> enable = true
                enable -> sum += it.ints().let { (a, b) -> a * b }
            }
        }

        return sum
    }
}
