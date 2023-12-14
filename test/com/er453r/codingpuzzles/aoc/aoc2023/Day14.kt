package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 14")
class Day14 : AoCTestBase<Int>(
    year = 2023,
    day = 14,
    testTarget1 = 136,
    puzzleTarget1 = 108759,
    testTarget2 = 64,
    puzzleTarget2 = 89089,
) {
    fun tiltN(grid: Grid<Char>) {
        grid.columns().forEach { column ->
            while (true) {
                val canFallDown = column.indices.indexOfFirst { it > 0 && column[it].value == 'O' && column[it - 1].value == '.' }

                if (canFallDown == -1)
                    break
                else {
                    column[canFallDown].value = '.'
                    column[canFallDown - 1].value = 'O'
                }
            }
        }
    }

    fun tiltS(grid: Grid<Char>) {
        grid.columns().forEach { column ->
            while (true) {
                val canFallDown = column.indices.reversed().firstOrNull { it < column.size - 1 && column[it].value == 'O' && column[it + 1].value == '.' }

                if (canFallDown == null)
                    break
                else {
                    column[canFallDown].value = '.'
                    column[canFallDown + 1].value = 'O'
                }
            }
        }
    }

    fun tiltW(grid: Grid<Char>) {
        grid.rows().forEach { row ->
            while (true) {
                val canFallDown = row.indices.indexOfFirst { it > 0 && row[it].value == 'O' && row[it - 1].value == '.' }

                if (canFallDown == -1)
                    break
                else {
                    row[canFallDown].value = '.'
                    row[canFallDown - 1].value = 'O'
                }
            }
        }
    }

    fun tiltE(grid: Grid<Char>) {
        grid.rows().forEach { row ->
            while (true) {
                val canFallDown = row.indices.reversed().firstOrNull { it < row.size - 1 && row[it].value == 'O' && row[it + 1].value == '.' }

                if (canFallDown == null)
                    break
                else {
                    row[canFallDown].value = '.'
                    row[canFallDown + 1].value = 'O'
                }
            }
        }
    }

    fun cycle(grid: Grid<Char>) {
        tiltN(grid)
        tiltW(grid)
        tiltS(grid)
        tiltE(grid)
    }

    fun score(grid: Grid<Char>):Int{
        return grid.columns().map { column ->
            column.filter { it.value == 'O' }
                .map { column.size - it.position.y }
                .sum()
        }.sum()
    }

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        tiltN(grid);

        return score(grid)
    }

    fun <T> List<T>.findLongestSequence(): Pair<Int, Int> {
        val sequences = mutableListOf<Pair<Int, Int>>()

        for (startPos in indices) {
            for (sequenceLength in 1..(this.size - startPos) / 2) {
                var sequencesAreEqual = true

                for (i in 0 until sequenceLength)
                    if (this[startPos + i] != this[startPos + sequenceLength + i]) {
                        sequencesAreEqual = false
                        break
                    }

                if (sequencesAreEqual)
                    sequences += Pair(startPos, sequenceLength)
            }
        }

        return sequences.maxBy { it.second }
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val points = mutableListOf<Int>()

        for(n in 0 until 200) {
            cycle(grid)

            points.add(score(grid))
        }

        val bestSequence = points.findLongestSequence()
        val iters = 1000000000
        val sequence = points.subList(bestSequence.first, bestSequence.first + bestSequence.second)
        val sequenceStartIndex = bestSequence.first
        var index = sequenceStartIndex

        while(index + sequence.size < iters)
            index += sequence.size

        return sequence[iters - index - 1]
    }
}
