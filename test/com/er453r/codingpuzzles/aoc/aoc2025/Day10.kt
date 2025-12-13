package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.aStar
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.memoize
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

    fun solve(startState: List<Int>, zeroedIndex: Int, buttons: Set<Set<Int>>): Set<List<Int>> {
        val count = memoize<List<Int>, Set<List<Int>>> { state ->
            if (state.any { it < 0 })
                setOf()
            else if (state[zeroedIndex] == 0)
                setOf(state)
            else {
                buttons.map { button ->
                    state.toMutableList().also { s ->
                        button.forEach { s[it]-- }
                    }
                }.flatMap { this(it) }.toSet()
            }
        }

        return count(startState)
    }

    data class State(
        val slots: List<Int>,
        val presses: Int,
    )

    override fun part2(input: List<String>): Int {
        val line = input.map {
            val parts = it.split(" ")

            val state = parts.last().replace("{", "").replace("}", "").ints()
            val buttons = parts.drop(1).dropLast(1).map { str -> str.ints().toSet() }.toSet()

            Pair(state, buttons)
        }

        var lineIndex = 0

        return line.sumOf { (endState, buttons) ->
            val statesQueue = mutableListOf(State(endState, 0))
            var count = 0

            loop@ while (statesQueue.isNotEmpty()) {
                val currentState = statesQueue.removeFirst()

                val zeroStates = currentState.slots.indices.filter { currentState.slots[it] == 0 }.toSet()
                val validButtons = buttons.filter { it.intersect(zeroStates).isEmpty() }.toSet()
                val buttonCountForState = mutableMapOf<Int, Int>()

                validButtons.forEach { button ->
                    button.forEach { slot -> buttonCountForState[slot] = (buttonCountForState[slot] ?: 0) + 1 }
                }

                try {
                    val minState = buttonCountForState.entries.minBy { it.value }.key
                    val buttonForMinState = validButtons.filter { it.contains(minState) }.toSet()

//                println("Trying to transition from $currentState with $minState with buttons $buttonForMinState")

                    val statesReached = solve(currentState.slots.toList(), minState, buttonForMinState)

                    val presses = currentState.slots[minState]

//                println("Reached ${statesReached.size} desired states after $presses presses")

                    for (desiredState in statesReached)
                        if (desiredState.all { it == 0 }) {
                            count = currentState.presses + presses
                            break@loop
                        }

                    statesReached.forEach { statesQueue.add(State(it, currentState.presses + presses)) }
                } catch (e: NoSuchElementException) {
                }
            }

            println("Line ${lineIndex++} solved after $count presses")

            count
        }
    }
}
