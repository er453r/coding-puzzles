package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.aStar
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 20")
class Day20 : AoCTestBase<Int>(
    year = 2024,
    day = 20,
    testTarget1 = 44,
    puzzleTarget1 = 1332,
    testTarget2 = 285,
    puzzleTarget2 = 987695,
) {
    private fun solve(input: List<String>, cheatsAllowed: Int, testCondition: Int): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val start = grid.data.flatten().first { it.value == 'S' }
        val end = grid.data.flatten().first { it.value == 'E' }
        val test = input.size < 20

        val basePath = aStar(
            start = start,
            isEndNode = { it == end },
            neighbours = { cell -> cell.neighbours().filter { it.value != '#' } },
        )

        val path = basePath.first.dropLast(1)
        val pathRest = basePath.first.mapIndexed { index, cell -> cell to path.size - index }.toMap()
        val nonWall = basePath.first.filter { it.value != '#' }

        var counter = 0

        path.forEachIndexed { cheatStartIndex, cheatStart ->
            nonWall.filter { it != cheatStart }
                .map { Pair(it, (it.position - cheatStart.position).manhattan()) }
                .filter { (_, manhattan) -> manhattan < cheatsAllowed + 1 }
                .forEach { (cheatEnd, manhattan) ->
                    val finalPathLength = cheatStartIndex + manhattan + pathRest[cheatEnd]!!
                    val saved = basePath.second - finalPathLength

                    if ((test && saved > testCondition) || saved >= 100)
                        counter++
                }
        }

        return counter
    }

    override fun part1(input: List<String>) = solve(input, 2, 0)

    override fun part2(input: List<String>) = solve(input, 20, 50 - 1)
}
