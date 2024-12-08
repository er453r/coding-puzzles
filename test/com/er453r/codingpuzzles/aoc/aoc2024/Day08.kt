package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.Vector2d
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 08")
class Day08 : AoCTestBase<Int>(
    year = 2024,
    day = 8,
    testTarget1 = 14,
    puzzleTarget1 = 327,
    testTarget2 = 34,
    puzzleTarget2 = null,
) {
    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val antinodes = mutableSetOf<Vector2d>()

        val nodes = grid.data.flatten().filter { it.value != '.' }

        nodes.forEach { node ->
           nodes.filter { it.value == node.value && it != node }.forEach { other ->
               antinodes += node.position - (other.position - node.position)
           }
        }

        return antinodes.count { grid.contains(it) }
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val antinodes = mutableSetOf<Vector2d>()

        val nodes = grid.data.flatten().filter { it.value != '.' }

        nodes.forEach { node ->
            nodes.filter { it.value == node.value && it != node }.forEach { other ->
                val diff = other.position - node.position
                var antinode = node.position - diff
                antinodes += other.position

                while(grid.contains(antinode)){
                    antinodes += antinode
                    antinode -= diff
                }
            }
        }

        return antinodes.count { grid.contains(it) }
    }
}