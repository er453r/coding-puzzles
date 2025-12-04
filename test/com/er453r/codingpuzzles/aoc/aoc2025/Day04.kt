package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 04")
class Day04 : AoCTestBase<Int>(
    year = 2025,
    day = 4,
    testTarget1 = 13,
    puzzleTarget1 = 1493,
    testTarget2 = 43,
    puzzleTarget2 = 9194,
) {
    override fun part1(input: List<String>): Int {
        return Grid(input.map { it.toCharArray().toList() }).data.flatten().count {
            it.value == '@' && it.neighbours8().count { n -> n.value == '@' } < 4
        }
    }

    override fun part2(input: List<String>): Int {
        val cells = Grid(input.map { it.toCharArray().toList() }).data.flatten()

        var removed = 0

        while(true){
            val toBeRemoved = cells.filter {
                it.value == '@' && it.neighbours8().count { n -> n.value == '@' } < 4
            }

            if(toBeRemoved.isEmpty())
                break

            toBeRemoved.forEach {
                it.value = '.'
                removed++
            }
        }

        return removed
    }
}
