package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.lcm
import com.er453r.codingpuzzles.utils.variance
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 14")
class Day14 : AoCTestBase<Int>(
    year = 2024,
    day = 14,
    testTarget1 = 12,
    puzzleTarget1 = 221655456,
    testTarget2 = 12,
    puzzleTarget2 = 7858,
) {
    override fun part1(input: List<String>): Int {
        val test = input.size < 20
        val t = 100
        val h = if (test) 7 else 103
        val w = if (test) 11 else 101

        val robots = input.map { it.ints() }.map { Pair(Vector2d(it[0], it[1]), Vector2d(it[2], it[3])) }

        val positions = robots.map { it.first + it.second * t }.map {
            Vector2d((it.x % w + w) % w, (it.y % h + h) % h)
        }

        val mh = (h - 1) / 2
        val mw = (w - 1) / 2

        val quad1 = positions.count { it.x < mw && it.y < mh }
        val quad2 = positions.count { it.x > mw && it.y < mh }
        val quad3 = positions.count { it.x < mw && it.y > mh }
        val quad4 = positions.count { it.x > mw && it.y > mh }

        return quad1 * quad2 * quad3 * quad4
    }

    private fun cycle(pair: Pair<Vector2d, Vector2d>, w: Int, h: Int): Int {
        var t = 0

        while (true) {
            t++

            val position = pair.first + pair.second * t
            val corrected = Vector2d((position.x % w + w) % w, (position.y % h + h) % h)

            if (corrected == pair.first)
                return t
        }
    }

    override fun part2(input: List<String>): Int {
        val test = input.size < 20
        val h = if (test) 7 else 103
        val w = if (test) 11 else 101

        val robots = input.map { it.ints() }.map { Pair(Vector2d(it[0], it[1]), Vector2d(it[2], it[3])) }
        val maxCycles = robots.map { cycle(it, w, h) }.lcm()

        val variances = (0 until maxCycles).associateWith { t ->
            robots.map { it.first + it.second * t }.map {
                Vector2d((it.x % w + w) % w, (it.y % h + h) % h)
            }.flatMap { listOf(it.x, it.y) }.variance()
        }

        return variances.minBy { it.value }.key
    }
}
