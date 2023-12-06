package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.longs
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 06")
class Day06 : AoCTestBase<Long>(
    year = 2023,
    day = 6,
    testTarget1 = 288,
    puzzleTarget1 = 393120,
    testTarget2 = 71503,
    puzzleTarget2 = 36872656,
) {
    private fun solve(raceTimes: List<Long>, recordDistances: List<Long>) = raceTimes.mapIndexed { index, raceTime ->
        val recordDistance = recordDistances[index]

        LongRange(0, raceTime).map { dt ->
            (raceTime - dt) * dt > recordDistance  // (time left) * velocity
        }.count { it }.toLong()
    }.reduce(Long::times)

    override fun part1(input: List<String>) = solve(
        raceTimes = input.first().longs(),
        recordDistances = input.last().longs(),
    )

    override fun part2(input: List<String>) = solve(
        raceTimes = input.first().replace(" ", "").longs(),
        recordDistances = input.last().replace(" ", "").longs(),
    )
}
