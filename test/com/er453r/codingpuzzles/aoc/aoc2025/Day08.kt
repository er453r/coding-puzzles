package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.DisjointSet
import com.er453r.codingpuzzles.utils.Vector3d
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.product
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 08")
class Day08 : AoCTestBase<Long>(
    year = 2025,
    day = 8,
    testTarget1 = 40,
    puzzleTarget1 = 112230,
    testTarget2 = 25272,
    puzzleTarget2 = 2573952864,
) {
    override fun part1(input: List<String>): Long {
        val points = input.map { it.ints() }.map { Vector3d(it[0], it[1], it[2]) }.toList()
        val limit = if (points.size < 30) 10 else 1000
        val pairs = pairs(points)
        val disjointSet = DisjointSet(points)

        pairs.take(limit).forEach { pair ->
            disjointSet.union(pair.first, pair.second)
        }

        return disjointSet.size.values.sortedDescending().take(3).map { it.toLong() }.product()
    }

    override fun part2(input: List<String>): Long {
        val points = input.map { it.ints() }.map { Vector3d(it[0], it[1], it[2]) }.toList()
        val pairs = pairs(points)
        val disjointSet = DisjointSet(points)

        for (pair in pairs) {
            disjointSet.union(pair.first, pair.second)

            if (disjointSet.size.values.max() == points.size)
                return pair.first.x.toLong() * pair.second.x
        }

        return -1
    }

    fun pairs(points: List<Vector3d>) = points.indices.flatMap { n ->
        (n + 1..points.lastIndex).map { m ->
            Pair(points[n], points[m])
        }
    }
        .sortedBy { (it.first - it.second).length2() }
}
