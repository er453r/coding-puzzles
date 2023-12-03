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
    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        var sum = 0;

        for(y in 0 until grid.height){
            var partNumber = ""
            var adjacent = false

            for(x in 0 until grid.width) {
                val cell = grid.get(x, y)

                if(cell.value.isDigit()) {
                    partNumber += cell.value

                    if(!adjacent)
                        adjacent = grid.getAll(cell.position.neighbours8()).any { it.value != '.' && !it.value.isDigit() }
                }

                if((!cell.value.isDigit() || x == (grid.width - 1)) && partNumber.length > 0) {
                    if(adjacent)
                        sum += partNumber.toInt()

                    adjacent = false
                    partNumber = ""
                }
            }
        }

        return sum
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val parts = mutableSetOf<Pair<Int, Set<Vector2d>>>();

        for(y in 0 until grid.height){
            var partNumber = ""
            var adjacent = false
            var neighbours = mutableSetOf<Vector2d>()

            for(x in 0 until grid.width) {
                val cell = grid.get(x, y)

                if(cell.value.isDigit()) {
                    partNumber += cell.value
                    neighbours += cell.position.neighbours8()

                    if(!adjacent)
                        adjacent = grid.getAll(cell.position.neighbours8()).any { it.value != '.' && !it.value.isDigit() }
                }

                if((!cell.value.isDigit() || x == (grid.width - 1)) && partNumber.length > 0) {
                    if(adjacent)
                        parts.add(Pair(partNumber.toInt(), neighbours))

                    adjacent = false
                    partNumber = ""
                    neighbours = mutableSetOf()
                }
            }
        }

        var sum = 0

        for(y in 0 until grid.height){
            for(x in 0 until grid.width) {
                val cell = grid.get(x, y)

                if(cell.value == '*'){
                    val neighbourParts = parts.filter { it.second.contains(cell.position) }

                    if(neighbourParts.size == 2)
                        sum += neighbourParts.first().first * neighbourParts.last().first
                }
            }
        }

        return sum
    }
}
