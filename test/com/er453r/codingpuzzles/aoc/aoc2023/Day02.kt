package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.destructured
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 02")
class Day02 : AoCTestBase<Int>(
    year = 2023,
    day = 2,
    testTarget1 = 8,
    puzzleTarget1 = 2331,
    testTarget2 = 2286,
    puzzleTarget2 = 71585,
) {
    data class Draw(
        val r: Int = 0,
        val g: Int = 0,
        val b: Int = 0,
    )

    private val limit = Draw(
        r = 12,
        g = 13,
        b = 14,
    )

    private fun parseGame(input: List<String>) = input.associate {
        val parts = it.split("[:,;]".toRegex())
        val gameId = parts.first().ints().first()
        val draws = parts.drop(1)
            .map { draw -> draw.destructured(""".+?(\d+) (\w).+""".toRegex()) }
            .map { (number, letter) ->
                when (letter) {
                    "r" -> Draw(r = number.toInt())
                    "g" -> Draw(g = number.toInt())
                    else -> Draw(b = number.toInt())
                }
            }

        gameId to draws
    }

    override fun part1(input: List<String>) = parseGame(input).entries.filter { (_, draws) ->
        draws.indexOfFirst { draw -> draw.r > limit.r || draw.g > limit.g || draw.b > limit.b } == -1
    }.sumOf { (gameId, _) -> gameId }

    override fun part2(input: List<String>) = parseGame(input).values.sumOf { draws ->
        draws.maxOf { it.r } * draws.maxOf { it.g } * draws.maxOf { it.b }
    }
}
