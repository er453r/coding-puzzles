package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.Vector2d
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 03")
class Day03 : AoCTestBase<Int>(
    year = 2023,
    day = 3,
    testTarget1 = 4361,
    puzzleTarget1 = 539713,
    testTarget2 = 467835,
    puzzleTarget2 = 84159075,
) {
    data class NumberWithNeighbours(
        val number: Int,
        val neighbours: Set<GridCell<Char>>,
    )

    private fun numbersParts(grid: Grid<Char>): List<NumberWithNeighbours> {
        val numberParts = mutableListOf<MutableList<GridCell<Char>>>()

        grid.data.flatten().filter { it.value.isDigit() }.forEach { digit ->
            if (grid.contains(digit.position + Vector2d.LEFT))
                numberParts.firstOrNull { it.contains(grid[digit.position + Vector2d.LEFT]) }?.add(digit) ?: numberParts.add(mutableListOf(digit))
            else
                numberParts.add(mutableListOf(digit))
        }

        return numberParts.map {
            NumberWithNeighbours(
                number = it.map { cell -> cell.value }.joinToString("").toInt(),
                neighbours = it.flatMap { cell -> grid.getAll(cell.position.neighbours8()) }.toSet() - it.toSet()
            )
        }.filter { it.neighbours.any { n -> n.value != '.' } }
    }

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        return numbersParts(grid).sumOf { it.number }
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val numbersParts = numbersParts(grid)

        return grid.data.flatten()
            .filter { it.value == '*' }
            .filter { cell -> numbersParts.count { cell in it.neighbours } == 2 }
            .sumOf { cell -> numbersParts.filter { cell in it.neighbours }.map { it.number }.reduce(Int::times) }
    }
}
