package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 10")
class Day10 : AoCTestBase<Int>(
    year = 2025,
    day = 10,
    testTarget1 = 7,
    puzzleTarget1 = 475,
    testTarget2 = 33,
    puzzleTarget2 = 18273,
) {
    override fun part1(input: List<String>): Int {
        val line = input.map {
            val parts = it.split(" ")

            val state = parts.first().replace("[", "").replace("]", "").toCharArray().map { c -> if (c == '#') 1 else 0 }
            val buttons = parts.drop(1).dropLast(1).map { str -> str.ints().toSet() }
            val jolts = parts.last().ints()

            Triple(state, buttons, jolts)
        }

        return line.sumOf { (state, buttons, jolts) ->
            val patterns = mutableMapOf<List<Int>, MutableList<List<Int>>>()
            val combinations = 1 shl buttons.size // 2^N

            for (mask in 0 until combinations) {
                val pressed = (0 until buttons.size).map { if ((mask shr it) and 1 == 1) 1 else 0 }
                val currentJolt = MutableList(jolts.size) { 0 }

                pressed.withIndex().filter { it.value == 1 }.forEach {
                    buttons[it.index].forEach { slot -> currentJolt[slot] += 1 }
                }

                val lights = currentJolt.map { it % 2 }

                patterns.getOrPut(lights) { mutableListOf() }.add(pressed)
            }

            patterns[state]!!.minOf { it.sum() }
        }
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}
