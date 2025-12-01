package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.DisplayName
import kotlin.math.absoluteValue

@DisplayName("AoC 2025 - Day 01")
class Day01 : AoCTestBase<Int>(
    year = 2025,
    day = 1,
    testTarget1 = 3,
    puzzleTarget1 = 962,
    testTarget2 = 6,
    puzzleTarget2 = 5782,
) {
    override fun part1(input: List<String>): Int {
        val rotations = input.map { it.replace('L', '-').replace('R', '+').toInt() }

        val size = 100

        var position = 50
        var zeros = 0

        rotations.forEach { steps ->
            position = (position + steps) % size

            if (position == 0) zeros++
        }

        return zeros
    }

    override fun part2(input: List<String>): Int {
        val rotations = input.map { it.replace('L', '-').replace('R', '+').toInt() }
        val size = 100

        var position = 50
        var zeros = 0

        rotations.forEach { steps ->
            val current = if (steps >= 0) position else (size - position) % size // mirror if negative

            zeros += (current + steps.absoluteValue) / size // this many overflows
            position = (size + position + (steps % size)) % size
        }

        return zeros
    }
}
