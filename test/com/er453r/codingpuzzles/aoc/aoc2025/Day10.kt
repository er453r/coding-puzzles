package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.memoize
import org.junit.jupiter.api.DisplayName
import kotlin.math.min

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
        val lines = input.map {
            val parts = it.split(" ")

            val state = parts.first().replace("[", "").replace("]", "").toCharArray().map { c -> if (c == '#') 1 else 0 }
            val buttons = parts.drop(1).dropLast(1).map { str -> str.ints().toSet() }
            val jolts = parts.last().ints()

            Triple(state, buttons, jolts)
        }

        return lines.sumOf { (state, buttons, jolts) ->
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
        val line = input.map {
            val parts = it.split(" ")

            val state = parts.first().replace("[", "").replace("]", "").toCharArray().map { c -> if (c == '#') 1 else 0 }
            val buttons = parts.drop(1).dropLast(1).map { str -> str.ints().toSet() }
            val jolts = parts.last().ints()

            Triple(state, buttons, jolts)
        }

        return line.sumOf { (_, buttons, jolts) ->
            val patterns = mutableMapOf<List<Int>, MutableList<List<Int>>>()
            val ops = mutableMapOf<List<Int>, List<Int>>()
            val combinations = 1 shl buttons.size // 2^N

            for (mask in 0 until combinations) {
                val pressed = (0 until buttons.size).map { if ((mask shr it) and 1 == 1) 1 else 0 }
                val currentJolt = MutableList(jolts.size) { 0 }

                pressed.withIndex().filter { it.value == 1 }.forEach {
                    buttons[it.index].forEach { slot -> currentJolt[slot] += 1 }
                }

                val lights = currentJolt.map { it % 2 }

                patterns.getOrPut(lights) { mutableListOf() }.add(pressed)

                ops[pressed] = currentJolt
            }

            val solve = memoize<List<Int>, Int> { target ->
                // Base cases
                if (target.all { it == 0 })
                    0
                else if (target.any { it < 0 })
                    Int.MAX_VALUE
                else {
                    var total = Int.MAX_VALUE
                    val currentLights = target.map { it % 2 }

                    // Iterate over all press combinations that match the current parity
                    val possiblePresses = patterns[currentLights] ?: emptyList()

                    for (pressed in possiblePresses) {
                        val diff = ops[pressed]!!

                        // equivalent to: tuple((b - a) // 2 for a, b in zip(diff, target))
                        val newTarget = target.zip(diff).map { (b, a) -> (b - a) / 2 }

                        val res = this(newTarget)
                        if (res != Int.MAX_VALUE) {
                            total = min(total, pressed.sum() + 2 * res)
                        }
                    }

                    total
                }
            }

            solve(jolts)
        }
    }
}
