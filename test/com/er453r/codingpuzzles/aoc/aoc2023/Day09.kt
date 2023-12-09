package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.longs
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 09")
class Day09 : AoCTestBase<Long>(
    year = 2023,
    day = 9,
    testTarget1 = 114,
    puzzleTarget1 = 1992273652,
    testTarget2 = 2,
    puzzleTarget2 = 1012,
) {
    private fun solve(input: List<String>, predict: (MutableList<MutableList<Long>>) -> Long) = input.map { it.longs() }.sumOf { numbers ->
        val history = mutableListOf(numbers.toMutableList())
        var previous = numbers

        while (previous.any { it != 0L }) {
            val reduced = (0..previous.size - 2).map { previous[it + 1] - previous[it] }
            history.add(reduced.toMutableList())
            previous = reduced
        }

        predict(history)
    }

    override fun part1(input: List<String>) = solve(input) { history ->
        history.last().add(0)

        for (n in history.size - 2 downTo 0)
            history[n].add(history[n].last() + history[n + 1].last())

        history.first().last()
    }

    override fun part2(input: List<String>) = solve(input) { history ->
        history.last().addFirst(0)

        for (n in history.size - 2 downTo 0)
            history[n].addFirst(history[n].first() - history[n + 1].first())

        history.first().first()
    }
}
