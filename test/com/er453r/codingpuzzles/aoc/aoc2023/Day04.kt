package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.pow
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 04")
class Day04 : AoCTestBase<Int>(
    year = 2023,
    day = 4,
    testTarget1 = 13,
    puzzleTarget1 = 21138,
    testTarget2 = 30,
    puzzleTarget2 = 7185540,
) {
    override fun part1(input: List<String>) = input
        .map { it.split("[:|]".toRegex()) }
        .map { (_, winning, all) ->
            all.ints().toSet().intersect(winning.ints().toSet())
        }
        .filter { it.isNotEmpty() }
        .sumOf { 2.pow(it.size - 1).toInt() }

    override fun part2(input: List<String>): Int {
        val production = input
            .map { it.split("[:|]".toRegex()) }
            .associate { (idString, winningString, allString) ->
                val id = idString.ints().first()
                val matches = allString.ints().toSet().intersect(winningString.ints().toSet())

                id to (id + 1..id + matches.size).toSet()
            }

        val stack = production.keys.toMutableList()
        var counter = 0

        while (stack.isNotEmpty()) {
            counter++
            val top = stack.removeLast()

            if (production.contains(top))
                stack.addAll(production[top]!!)
        }

        return counter
    }
}
