package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.memoize
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 11")
class Day11 : AoCTestBase<Long>(
    year = 2025,
    day = 11,
    testTarget1 = 5,
    puzzleTarget1 = 786,
    testTarget2 = 2,
    puzzleTarget2 = 495845045016588,
) {
    fun Map<String, List<String>>.countPaths(from: String, to: String): Long {
        val map = this

        val count = memoize<String, Long> { node ->
            when (node) {
                to -> 1
                "out" -> 0
                else -> map[node]!!.sumOf { this(it) }
            }
        }

        return count(from)
    }

    fun createMap(input: List<String>) = input.associate {
        val parts = it.split(":")
        val from = parts.first()
        val to = parts.last().trim().split(" ")

        from to to
    }

    override fun part1(input: List<String>) = createMap(input.split().first()).countPaths("you", "out")

    override fun part2(input: List<String>): Long {
        val map = createMap(input.split().last())

        val svr2fft = map.countPaths("svr", "fft")
        val fft2dac = map.countPaths("fft", "dac")
        val dac2out = map.countPaths("dac", "out")

        return svr2fft * fft2dac * dac2out
    }
}
