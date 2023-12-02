package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.destructured
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName

// this is a template for this year
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
        var r: Int = 0,
        var g: Int = 0,
        var b: Int = 0,
    )

    val limit = Draw(
        r = 12,
        g = 13,
        b = 14,
    )

    fun parseGame(input: List<String>) = input.map {
        val parts = it.split(":")
        val gameId = parts.first().ints().first()

        val gameStrings = parts.last().split(";")

        val drams = gameStrings.map {
            val draw = Draw()

            it.split(",").forEach {
                val (number, letter) = it.destructured(""".+?(\d+) (\w).+""".toRegex())

                when (letter) {
                    "r" -> draw.r = number.toInt()
                    "g" -> draw.g = number.toInt()
                    else -> draw.b = number.toInt()
                }
            }

            draw
        }

        gameId to drams
    }.toMap()

    override fun part1(input: List<String>): Int {
        val games = parseGame(input)

        return games.mapNotNull { game ->
            var possible = true

            for (draw in game.value) {
                if (draw.r > limit.r || draw.g > limit.g || draw.b > limit.b) {
                    possible = false
                    break
                }
            }

            if (possible)
                game.key
            else
                null
        }.sum()
    }

    override fun part2(input: List<String>): Int {
        val games = parseGame(input)

        return games.values.map { draws ->
            draws.maxOf { it.r } * draws.maxOf { it.g } * draws.maxOf { it.b }
        }.sum()
    }
}
