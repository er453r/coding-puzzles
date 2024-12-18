package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.aStar
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 18")
class Day18 : AoCTestBase<String>(
    year = 2024,
    day = 18,
    testTarget1 = "22",
    puzzleTarget1 = "282",
    testTarget2 = "6,1",
    puzzleTarget2 = "64,29",
) {
    override fun part1(input: List<String>): String {
        val test = input.size < 50
        val size = if (test) 6 else 70
        val corrupt = if (test) 12 else 1024
        val grid = Grid(List(size + 1) { List(size + 1) { 0 } })
        val bytes = input.map { it.ints() }.map { (x, y) -> Vector2d(x, y) }
        val start = grid[Vector2d(0, 0)]
        val end = grid[Vector2d(size, size)]

        bytes.take(corrupt).forEach { grid[it].value = 1 }

        val path = aStar(
            start = start,
            isEndNode = { it == end },
            neighbours = {
                it.neighbours().filter { n -> n.value == 0 }
            })

        return (path.first.size - 1).toString()
    }

    override fun part2(input: List<String>): String {
        val test = input.size < 50
        val size = if (test) 6 else 70
        val corrupt = if (test) 12 else 1024
        val grid = Grid(List(size + 1) { List(size + 1) { 0 } })
        val bytes = input.map { it.ints() }.map { (x, y) -> Vector2d(x, y) }
        val start = grid[Vector2d(0, 0)]
        val end = grid[Vector2d(size, size)]

        bytes.take(corrupt).forEach { grid[it].value = 1 }

        var bestPath = setOf<Vector2d>()

        for (n in corrupt..<input.size) {
            val newByte = bytes[n].also { grid[it].value = 1 }

            if (newByte in bestPath || bestPath.isEmpty()) {
                val path = aStar(
                    start = start,
                    isEndNode = { it == end },
                    neighbours = {
                        it.neighbours().filter { n -> n.value == 0 }
                    })

                if (path.first.isEmpty())
                    return bytes[n].let { "${it.x},${it.y}" }

                bestPath = path.first.map { it.position }.toSet()
            }
        }

        return ""
    }
}
