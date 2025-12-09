package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Vector2dl
import com.er453r.codingpuzzles.utils.longs
import org.junit.jupiter.api.DisplayName
import kotlin.math.abs

// this is a template for this year
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
        val points = input.map { it.longs() }.map { Vector2dl(it[0], it[1]) }

        return points.indices.flatMap { n ->
            (n + 1..points.lastIndex).map { m ->
                (abs(points[n].x - points[m].x) + 1) * (abs(points[n].y - points[m].y) + 1)
            }
        }.max()
    }

    // too high - 4581960734
    override fun part2(input: List<String>): Long {
        val points = input.map { it.longs() }.map { Vector2dl(it[0], it[1]) }
        val edges = (points + points.first()).zipWithNext()

        return points.indices.flatMap { n ->
            (n + 1..points.lastIndex).map { m ->
                val topLeft = Vector2dl(minOf(points[n].x, points[m].x), minOf(points[n].y, points[m].y))
                val bottomRight = Vector2dl(maxOf(points[n].x, points[m].x), maxOf(points[n].y, points[m].y))

                var fail = false

                for (edge in edges) {
                    val edgeTopLeft = Vector2dl(minOf(edge.first.x, edge.second.x), minOf(edge.first.y, edge.second.y))
                    val edgeBottomRight = Vector2dl(maxOf(edge.first.x, edge.second.x), maxOf(edge.first.y, edge.second.y))

                    val cond1 = topLeft.x < edgeBottomRight.x
                    val cond2 = bottomRight.x > edgeTopLeft.x
                    val cond3 = topLeft.y < edgeBottomRight.y
                    val cond4 = bottomRight.y > edgeTopLeft.y

                    if (cond1 && cond2 && cond3 && cond4) {
                        fail = true
                        break
                    }
                }

                if (fail)
                    0
                else
                    (abs(points[n].x - points[m].x) + 1) * (abs(points[n].y - points[m].y) + 1)
            }
        }.max()
    }
}
