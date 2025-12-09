package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.*
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
        val pairs = points.combinations2().sortedBy { (it.first - it.second).length2() }
        val disjointSet = DisjointSet(points)

        pairs.take(limit).forEach { pair ->
            disjointSet.union(pair.first, pair.second)
        }

        return disjointSet.size.values.sortedDescending().take(3).map { it.toLong() }.product()
    }

    override fun part2(input: List<String>): Long {
        val points = input.map { it.ints() }.map { Vector3d(it[0], it[1], it[2]) }.toList()
        val pairs = points.combinations2().sortedBy { (it.first - it.second).length2() }
        val disjointSet = DisjointSet(points)

        for (pair in pairs) {
            disjointSet.union(pair.first, pair.second)

            if (disjointSet.size.values.max() == points.size)
                return pair.first.x.toLong() * pair.second.x
        }

        return -1
    }
}
