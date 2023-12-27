package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.GridCell
import com.er453r.codingpuzzles.utils.Vector2d
import org.junit.jupiter.api.DisplayName
import kotlin.math.max

@Suppress("unused")
@DisplayName("AoC 2023 - Day 23")
class Day23 : AoCTestBase<Int>(
    year = 2023,
    day = 23,
    testTarget1 = 94,
    puzzleTarget1 = 2402,
    testTarget2 = 154,
    puzzleTarget2 = null,
) {
    data class Hike(val steps: List<Vector2d>, val allSteps: List<Vector2d>)
    data class Edge(val from: Vector2d, val to: Vector2d, val length: Int)
    data class HikeCollapsed(val steps: List<Edge>)

    private fun hike(input: List<String>, filterCandidates: (Grid<Char>, Vector2d, GridCell<Char>) -> Boolean): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val start = Vector2d(1, 0)
        val finish = Vector2d(grid.width - 2, grid.height - 1)

        val paths = mutableListOf(Hike(listOf(start), listOf(start)))
        val allowed = grid.data.flatten().filter { it.value != '#' }.map { it.position }.toSet()
        val map = mutableMapOf<Vector2d, MutableList<Edge>>()
        val edges = mutableSetOf<Edge>()

        while (paths.isNotEmpty()) {
            val hike = paths.removeLast()
            val steps = hike.steps

            if (steps.last() == finish) {
                edges += Edge(steps.first(), steps.last(), steps.size)

                continue
            }

            val candidates = steps.last().neighboursCross()
                .filter { it in allowed && it !in hike.allSteps }
                .filter { filterCandidates(grid, steps.last(), grid[it]) }

            if (candidates.size == 1)
                paths += Hike(steps + candidates.first(), hike.allSteps + candidates.first())
            else {
                edges += Edge(steps.first(), steps.last(), steps.size)

                candidates.forEach { next ->
                    paths += Hike(listOf(steps.last(), next), hike.allSteps + next)
                }
            }
        }

        for (edge in edges)
            map.getOrPut(edge.from) { mutableListOf() } += edge

        println("collapsed ${map.size}")

//        map.entries.forEach { entry ->
//            entry.value.forEach {
//                println("\"${entry.key}\" -> \"${it.to}\"")
//            }
//        }

        var longest = 0
        val stack = map[start]!!.map { HikeCollapsed(listOf(it)) }.toMutableList()

        while (stack.isNotEmpty()) {
            val hike = stack.removeLast()

            if (hike.steps.last().to == finish) {
                longest = max(longest, hike.steps.sumOf { it.length } - (hike.steps.size))

                continue
            }

            val edge = hike.steps.last().to

            if(map.containsKey(edge))
                stack += map[hike.steps.last().to]!!.map { HikeCollapsed(hike.steps + it) }
        }

        return longest
    }

    override fun part1(input: List<String>) = hike(input) { grid, lastStep, candidate ->
        when (grid[lastStep].value) {
            '^' -> candidate.position == lastStep + Vector2d.UP
            '>' -> candidate.position == lastStep + Vector2d.RIGHT
            'v' -> candidate.position == lastStep + Vector2d.DOWN
            '<' -> candidate.position == lastStep + Vector2d.LEFT
            else -> when (candidate.value) {
                '^' -> candidate.position != lastStep + Vector2d.DOWN
                '>' -> candidate.position != lastStep + Vector2d.LEFT
                'v' -> candidate.position != lastStep + Vector2d.UP
                '<' -> candidate.position != lastStep + Vector2d.DOWN
                else -> true
            }
        }
    }

    override fun part2(input: List<String>) = hike(input) { _, _, _ -> true }
}
