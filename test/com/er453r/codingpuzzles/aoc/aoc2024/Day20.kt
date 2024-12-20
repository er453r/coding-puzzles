package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.*
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 20")
class Day20 : AoCTestBase<Int>(
    year = 2024,
    day = 20,
    testTarget1 = 44,
    puzzleTarget1 = 1332,
    testTarget2 = null,
    puzzleTarget2 = null,
) {
    data class State(val cell: GridCell<Char>, val cheats: Int)

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })
        val start = grid.data.flatten().first { it.value == 'S' }
        val end = grid.data.flatten().first { it.value == 'E' }

        val basePath = aStar(
            start = start,
            isEndNode = { it == end },
            neighbours = { cell ->
                cell.neighbours().filter { it.value != '#' }
            }).second

        val cheatsUsed = mutableSetOf<GridCell<Char>>()
        val cheatsCount = mutableMapOf<Int, Int>()

        while(true){
            val paths = aStarM(
                start = State(cell = start, cheats = 0),
                isEndNode = { it.cell == end },
                neighbours = { state ->
                    state.cell.neighbours()
                        .filter { it.value != '#' || (state.cheats < 2) }
                        .map { State(it, if (it.value == '#') state.cheats + 1 else state.cheats) }
                })

            val save = basePath - paths.second

            if(save <= 0)
                break

            val cheatsUsedNow = cheat.first.mapNotNull { it.last().cheat }.toSet()

            cheatsUsed += cheatsUsedNow
            cheatsCount[save] = cheatsUsedNow.size
        }

        val test = input.size < 20

        return cheatsCount.keys.filter { if(test) it < 100 else it >= 100 }.sumOf { cheatsCount[it]!! }
    }

//    override fun part1(input: List<String>): Int {
//        val grid = Grid(input.map { it.toCharArray().toList() })
//        val start = grid.data.flatten().first { it.value == 'S' }
//        val end = grid.data.flatten().first { it.value == 'E' }
//
//        val basePath = aStar(
//            start = start,
//            isEndNode = { it == end },
//            neighbours = { cell ->
//                cell.neighbours().filter { it.value != '#' }
//            }).second
//
//        val cheatsCount = mutableMapOf<Int, Int>()
//
//        grid.data.flatten().filter { it.value == '#' }.forEach { cheat ->
//            cheat.value = '.'
//
//            val path = aStar(
//                start = start,
//                isEndNode = { it == end },
//                neighbours = { cell ->
//                    cell.neighbours().filter { it.value != '#' }
//                })
//
//            val save = basePath - path.second
//
//            if(save > 0){
//                cheatsCount[save] = cheatsCount.getOrDefault(save, 0) + 1
//            }
//
//            cheat.value = '#'
//        }
//
//        // 5635 too high
//
//        return cheatsCount.keys.filter { it >= 100 }.sumOf { cheatsCount[it]!! }
//    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}
