package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.destructured
import com.er453r.codingpuzzles.utils.lcm
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 08")
class Day08 : AoCTestBase<Long>(
    year = 2023,
    day = 8,
    testTarget1 = 6,
    puzzleTarget1 = 11911,
    testTarget2 = 6,
    puzzleTarget2 = 10151663816849,
) {
    private fun parseInput(input: List<String>): Pair<List<Int>, Map<String, List<String>>> {
        val directions = input.first().toCharArray().map { it.toString() }.map { if (it == "L") 0 else 1 }
        val map = input.drop(2).associate { line ->
            val (node, left, right) = line.destructured("(\\w+) = \\((\\w+), (\\w+)\\)".toRegex())

            node to listOf(left, right)
        }

        return Pair(directions, map)
    }

    private fun nodePath(startNode: String, directions: List<Int>, map: Map<String, List<String>>, endCondition: (String) -> Boolean): Long {
        var node = startNode
        var steps = 0L

        while (!endCondition(node)) {
            directions.forEach { dir ->
                node = map[node]!![dir]

                steps++
            }
        }

        return steps
    }

    override fun part1(input: List<String>) = parseInput(input).let { (directions, map) ->
        nodePath("AAA", directions, map) { endNode ->
            endNode == "ZZZ"
        }
    }

    override fun part2(input: List<String>) = parseInput(input).let { (directions, map) ->
        map.keys.filter { it.endsWith("A") }.map {
            nodePath(it, directions, map) { endNode ->
                endNode.endsWith("Z")
            }
        }.reduce { a, b -> a.lcm(b) }
    }
}
