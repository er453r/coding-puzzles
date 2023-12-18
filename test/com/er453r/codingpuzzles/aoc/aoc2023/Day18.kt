package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Impossible
import com.er453r.codingpuzzles.utils.Vector2dl
import com.er453r.codingpuzzles.utils.destructured
import org.junit.jupiter.api.DisplayName
import kotlin.math.abs

@DisplayName("AoC 2023 - Day 18")
class Day18 : AoCTestBase<Long>(
    year = 2023,
    day = 18,
    testTarget1 = 62,
    puzzleTarget1 = 62573,
    testTarget2 = 952408144115,
    puzzleTarget2 = 54662804037719,
) {
    private fun shoelaceArea(v: List<Vector2dl>): Long {
        val n = v.size
        var a = 0L
        for (i in 0 until n - 1) {
            a += v[i].x * v[i + 1].y - v[i + 1].x * v[i].y
        }
        return abs(a + v[n - 1].x * v[0].y - v[0].x * v[n - 1].y) / 2
    }

    private fun vertices(vector2d: Vector2dl) = setOf(
        Vector2dl(vector2d.x + 0, vector2d.y + 0),
        Vector2dl(vector2d.x + 1, vector2d.y + 0),
        Vector2dl(vector2d.x + 0, vector2d.y + 1),
        Vector2dl(vector2d.x + 1, vector2d.y + 1),
    )

    private fun solve(instructions: List<Pair<Vector2dl, Long>>): Long {
        val position = Vector2dl(0, 0)

        val outlinePoints = (1..<instructions.size).map { n ->
            val previous = instructions[n - 1]
            val current = instructions[n - 0]

            position.increment(previous.first * previous.second)

            val currentVertices = vertices(position)
            val previousVertices = vertices(position - previous.first)
            val nextVertices = vertices(position + current.first)

            listOf(
                (currentVertices - previousVertices - nextVertices).first(),
                currentVertices.intersect(nextVertices).intersect(previousVertices).first()
            )
        }

        val outlines = outlinePoints.first().map { mutableListOf(it) }

        outlinePoints.drop(1).forEach { candidates ->
            candidates.forEach { candidate ->
                outlines.first { it.last().x == candidate.x || it.last().y == candidate.y }.add(candidate)
            }
        }

        val outlinesWithLengths = outlines.map { outline ->
            outline to (0..outline.size - 2).sumOf { n -> (outline[n] - outline[n + 1]).length() }
        }.sortedBy { (_, l) -> l }

        return shoelaceArea(outlinesWithLengths.last().first)
    }

    override fun part1(input: List<String>): Long {
        val data = input.map { it.split(" ") }

        val instructions = data.map { (dir, len, _) ->
            val direction = when (dir) {
                "U" -> Vector2dl.UP
                "D" -> Vector2dl.DOWN
                "L" -> Vector2dl.LEFT
                "R" -> Vector2dl.RIGHT
                else -> throw Impossible()
            }

            direction to len.toLong()
        }.toMutableList()

        instructions += instructions.first()

        return solve(instructions)
    }

    override fun part2(input: List<String>): Long {
        val instructions = input.map { line ->
            val (colorString) = line.destructured(".+\\(#(.+)\\)".toRegex())

            val direction = when (colorString.last()) {
                '3' -> Vector2dl.UP
                '1' -> Vector2dl.DOWN
                '2' -> Vector2dl.LEFT
                '0' -> Vector2dl.RIGHT
                else -> throw Impossible()
            }

            val steps = colorString.dropLast(1).toLong(16)

            direction to steps
        }.toMutableList()

        instructions += instructions.first()

        return solve(instructions)
    }
}
