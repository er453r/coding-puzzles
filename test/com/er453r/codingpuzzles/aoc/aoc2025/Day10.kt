package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.aStar
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName
import kotlin.math.min

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

            val state = parts.first().replace("[", "").replace("]", "").toCharArray().map { c -> c == '#' }
            val buttons = parts.drop(1).dropLast(1).map { str -> str.ints().toSet() }.toSet()

            Pair(state, buttons)
        }

        return line.sumOf { (endState, buttons) ->
            val presses = aStar(
                start = endState.map { false },
                isEndNode = { it == endState },
                neighbours = { state ->
                    buttons.map {
                        val clone = state.toMutableList()

                        it.forEach { slot -> clone[slot] = !clone[slot] }

                        clone
                    }
                }
            )

            presses.first.size - 1
        }
    }

    override fun part2(input: List<String>): Int {
        val line = input.map {
            val parts = it.split(" ")

            val state = parts.last().replace("{", "").replace("}", "").ints()
            val buttons = parts.drop(1).dropLast(1).map { str -> str.ints().toSet() }.toSet()

            Pair(state, buttons)
        }

        return line.sumOf { (endState, buttons) ->
            val end = endState.map { 0 }

            val presses = aStar(
                start = endState,
                isEndNode = { it == end },
                neighbours = { state ->
                    val zeros = state.indices.filter { state[it] == 0 }.toSet()

                    buttons.filter { it.intersect(zeros).isEmpty() }.map { button ->
                        val clone = state.toMutableList()

                        button.forEach { clone[it]-- }

                        clone
                    }
                },
                moveCost = { from, to ->
                    if(to == end)
                        1
                    else{
                        val minSlot = to.indices.filter { to[it] != 0 }.minBy { to[it] }
                        val slots = from.indices.filter { from[it] - to[it] > 0 }.toSet()

                        if(slots.contains(minSlot)) 1 else 100
                    }
                }
            )

            println("derp")

            presses.first.size - 1
        }
    }
}
