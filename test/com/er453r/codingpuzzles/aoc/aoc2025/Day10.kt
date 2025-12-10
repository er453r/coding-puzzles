package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.aStar
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 10")
class Day10 : AoCTestBase<Int>(
    year = 2025,
    day = 10,
    testTarget1 = 7,
    puzzleTarget1 = 475,
    testTarget2 = 33,
    puzzleTarget2 = null,
) {
    override fun part1(input: List<String>): Int {
        val line = input.map {
            val parts = it.split(" ")

            val state = parts.first().replace("[", "").replace("]", "").toCharArray().map { it == '#' }
            val buttons = parts.drop(1).dropLast(1).map { it.ints() }

            Pair(state, buttons)
        }

        return line.map { (endState, buttons) ->
            val presses = aStar(
                start = endState.map { false },
                isEndNode = { it == endState },
                neighbours = { state ->
                    buttons.map {
                        val clone = state.toMutableList()

                        it.forEach { clone[it] = !clone[it] }

                        clone
                    }
                }
            )

            presses.first.size - 1
        }.sum()
    }

    override fun part2(input: List<String>): Int {
        val line = input.map {
            val parts = it.split(" ")

            val state = parts.last().replace("{", "").replace("}", "").ints()
            val buttons = parts.drop(1).dropLast(1).map { it.ints() }

            Pair(state, buttons)
        }

        return line.map { (endState, buttons) ->
            val presses = aStar(
                start = endState.map { 0 },
                isEndNode = { it == endState },
                neighbours = { state ->
                    buttons.map { button ->
                        val clone = state.toMutableList()

                        button.forEach { clone[it]++ }

                        clone
                    }.filter { clone ->
                        clone.indices.all{ clone[it] <= endState[it] }
                    }
                }
            )

            presses.first.size - 1
        }.sum()
    }
}
