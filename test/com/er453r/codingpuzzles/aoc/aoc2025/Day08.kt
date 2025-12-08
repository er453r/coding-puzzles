package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
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
        val point2Circuit = points.associateWith { mutableSetOf(it) }.toMutableMap()
        val limit = if (points.size < 30) 10 else 1000
        val allPairs = mutableListOf<Pair<Vector3d, Vector3d>>()

        for (n in points.indices) {
            for (i in n + 1 until points.size) {
                allPairs.add(Pair(points[n], points[i]))
            }
        }

        allPairs.sortBy { (it.first - it.second).length2() }

        allPairs.take(limit).forEach { pair ->
            val circuit1 = point2Circuit[pair.first]!!
            val circuit2 = point2Circuit[pair.second]!!

            if (circuit1 != circuit2) {
                circuit1.addAll(circuit2)

                circuit2.forEach { point2Circuit[it] = circuit1 }
            }
        }

        val debug = point2Circuit.values.toSet().toList().sortedByDescending { it.size }

        return debug.take(3).map { it.size.toLong() }.product()
    }

    override fun part2(input: List<String>): Long {
        val points = input.map { it.ints() }.map { Vector3d(it[0], it[1], it[2]) }.toList()
        val point2Circuit = points.associateWith { mutableSetOf(it) }.toMutableMap()
        val allPairs = mutableListOf<Pair<Vector3d, Vector3d>>()

        for (n in points.indices) {
            for (i in n + 1 until points.size) {
                allPairs.add(Pair(points[n], points[i]))
            }
        }

        allPairs.sortBy { (it.first - it.second).length2() }

        for (pair in allPairs) {
            val circuit1 = point2Circuit[pair.first]!!
            val circuit2 = point2Circuit[pair.second]!!

            if (circuit1 != circuit2) {
                circuit1 += circuit2

                circuit2.forEach { point2Circuit[it] = circuit1 }
            }

            if (point2Circuit.values.toSet().size == 1)
                return pair.first.x.toLong() * pair.second.x.toLong()
        }

        return -1
    }
}
