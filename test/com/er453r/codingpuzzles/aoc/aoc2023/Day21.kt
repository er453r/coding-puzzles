package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 21")
class Day21 : AoCTestBase<Long>(
    year = 2023,
    day = 21,
    testTarget1 = 42,
    puzzleTarget1 = 3820,
    testTarget2 = 1111,
    puzzleTarget2 = null,
) {
    override fun part1(input: List<String>): Long {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val start = grid.data.flatten().first { it.value == 'S' }

        var starts = mutableListOf(start)
        val steps = 64

        for(n in 1 .. steps){
            val plots = mutableSetOf<GridCell<Char>>()

            while(starts.isNotEmpty()){
                val stepStart = starts.removeLast()

                val neighbours = grid.getAll(stepStart.position.neighboursCross()).filter { it.value != '#' }

                plots += neighbours
            }

            starts = plots.toMutableList()
        }


        return starts.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val start = grid.data.flatten().first { it.value == 'S' }

        var starts = mutableListOf(start)
        val steps = 26501365

        for(n in 1 .. steps){
            val plots = mutableSetOf<GridCell<Char>>()

            while(starts.isNotEmpty()){
                val stepStart = starts.removeLast()

                val neighbours = grid.getAll(stepStart.position.neighboursCross()).filter { it.value != '#' }

                plots += neighbours
            }

            starts = plots.toMutableList()
        }


        return starts.size.toLong()
    }
}
