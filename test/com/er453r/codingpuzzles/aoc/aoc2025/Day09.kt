package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Vector2dl
import com.er453r.codingpuzzles.utils.combinations2
import com.er453r.codingpuzzles.utils.longs
import org.junit.jupiter.api.DisplayName
import kotlin.math.abs

@DisplayName("AoC 2025 - Day 09")
class Day09 : AoCTestBase<Long>(
    year = 2025,
    day = 9,
    testTarget1 = 50,
    puzzleTarget1 = 4776487744,
    testTarget2 = 24,
    puzzleTarget2 = 1560299548,
) {
    override fun part1(input: List<String>): Long {
        val points = input.map { it.longs() }.map { (x, y) -> Vector2dl(x, y) }

        return points.combinations2().maxOf { (a, b) ->
            (abs(a.x - b.x) + 1) * (abs(a.y - b.y) + 1)
        }
    }

    override fun part2(input: List<String>): Long {
        val points = input.map { it.longs() }.map { (x, y) -> Vector2dl(x, y) }
        val edges = (points + points.first()).zipWithNext()

        return points.combinations2().filter { (a, b) ->
            val topLeft = Vector2dl(minOf(a.x, b.x), minOf(a.y, b.y))
            val bottomRight = Vector2dl(maxOf(a.x, b.x), maxOf(a.y, b.y))

            edges.none { edge ->
                val edgeTopLeft = Vector2dl(minOf(edge.first.x, edge.second.x), minOf(edge.first.y, edge.second.y))
                val edgeBottomRight = Vector2dl(maxOf(edge.first.x, edge.second.x), maxOf(edge.first.y, edge.second.y))

                val cond1 = topLeft.x < edgeBottomRight.x
                val cond2 = bottomRight.x > edgeTopLeft.x
                val cond3 = topLeft.y < edgeBottomRight.y
                val cond4 = bottomRight.y > edgeTopLeft.y

                cond1 && cond2 && cond3 && cond4
            }
        }.maxOf { (a, b) -> (abs(a.x - b.x) + 1) * (abs(a.y - b.y) + 1) }
    }
}
