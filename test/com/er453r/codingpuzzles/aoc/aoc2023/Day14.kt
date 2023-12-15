package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.findIndexValueInRepeatedSequence
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
    private fun Grid<Char>.tilt(rows: List<List<GridCell<Char>>>): Grid<Char> {
        rows.forEach { row ->
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

        return this
    }

    private fun Grid<Char>.tiltN() = this.tilt(columns())
    private fun Grid<Char>.tiltS() = this.tilt(columns().map { it.reversed() })
    private fun Grid<Char>.tiltW() = this.tilt(rows())
    private fun Grid<Char>.tiltE() = this.tilt(rows().map { it.reversed() })
    private fun Grid<Char>.cycle() = this.tiltN().tiltW().tiltS().tiltE()

    private fun Grid<Char>.score() = this.columns().sumOf { column ->
        column.filter { it.value == 'O' }.sumOf { column.size - it.position.y }
    }

    override fun part1(input: List<String>) = Grid(input.map { it.toCharArray().toList() }).tiltN().score()

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        return List(200){grid.cycle().score()}.findIndexValueInRepeatedSequence(1000000000)
    }
}
