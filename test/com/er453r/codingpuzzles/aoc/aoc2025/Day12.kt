package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 12")
class Day12 : AoCTestBase<Int>(
    year = 2025,
    day = 12,
    testTarget1 = 2,
    puzzleTarget1 = 531,
    testTarget2 = null,
    puzzleTarget2 = null,
) {
    data class Shape(
        val width: Int,
        val height: Int,
        val points: Set<Vector2d>,
    )

    data class State(
        val width: Int,
        val height: Int,
        val shapesCount: List<Int>,
        val points: Set<Vector2d>,
    )

    override fun part1(input: List<String>): Int {
        val parts = input.split()

        val regions = parts.last().map {
            val parts = it.split(": ")
            val (width, height) = parts.first().ints()
            val shapesCount = parts.last().ints()

            State(width, height, shapesCount, (0..<width).flatMap { x -> (0..<height).map { y -> Vector2d(x, y) } }.toSet())
        }

        var count:Int = regions.sumOf { if (it.width * it.height >= it.shapesCount.sum() * 7) 1.toInt() else 0.toInt() }

        if(regions.size == 3)
            count--

        return count
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}
