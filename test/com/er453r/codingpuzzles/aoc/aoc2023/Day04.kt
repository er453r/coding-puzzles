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
    override fun part1(input: List<String>) = input.map { line ->
            val cards = line.split(":").last().split("|")
            val winning = cards[0].ints().toSet()
            val all = cards[1].ints().toSet()
            all.intersect(winning)
        }
        .filter { it.isNotEmpty() }
        .sumOf { 2.pow(it.size - 1).toInt() }

    override fun part2(input: List<String>): Int {
        val production = input.associate { line ->
            val parts = line.split(":")
            val id = parts[0].ints().first()
            val cards = parts.last().split("|")
            val winning = cards[0].ints().toSet()
            val all = cards[1].ints().toSet()
            val matches = all.intersect(winning)

            id to (id + 1 .. id + matches.size).toSet()
        }

        val stack = production.keys.toMutableList()
        var counter = 0

        while(stack.isNotEmpty()){
            counter++
            val top = stack.removeLast()

            if(production.contains(top))
                stack.addAll(production[top]!!)
        }

        return counter
    }
}
