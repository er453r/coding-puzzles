package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.Vector2d
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 16")
class Day16 : AoCTestBase<Int>(
    year = 2023,
    day = 16,
    testTarget1 = 46,
    puzzleTarget1 = 7477,
    testTarget2 = 51,
    puzzleTarget2 = 7853,
) {
    data class BeamPoint(var position: Vector2d, var direction: Vector2d)

    private fun countEnergized(grid: Grid<Char>, startingBeam: BeamPoint): Int {
        val energized = mutableSetOf<BeamPoint>()
        val beams = mutableListOf(startingBeam)

        while (beams.isNotEmpty()) {
            val beam = beams.removeLast()

            if (!grid.contains(beam.position))
                continue

            val cell = grid[beam.position]

            if (beam in energized)
                continue

            energized += beam.copy()

            when (cell.value) {
                '.' -> beam.position += beam.direction
                '|' -> when (beam.direction) {
                    Vector2d.RIGHT, Vector2d.LEFT -> {
                        beams += BeamPoint(beam.position + Vector2d.UP, Vector2d.UP)
                        beams += BeamPoint(beam.position + Vector2d.DOWN, Vector2d.DOWN)
                        continue
                    }

                    Vector2d.UP, Vector2d.DOWN -> beam.position += beam.direction
                    else -> throw Exception("this should never happen")
                }

                '-' -> when (beam.direction) {
                    Vector2d.UP, Vector2d.DOWN -> {
                        beams += BeamPoint(beam.position + Vector2d.LEFT, Vector2d.LEFT)
                        beams += BeamPoint(beam.position + Vector2d.RIGHT, Vector2d.RIGHT)
                        continue
                    }

                    Vector2d.LEFT, Vector2d.RIGHT -> beam.position += beam.direction
                    else -> throw Exception("this should never happen")
                }

                '/' -> when (beam.direction) {
                    Vector2d.RIGHT -> {
                        beam.direction = Vector2d.UP
                        beam.position += Vector2d.UP
                    }

                    Vector2d.LEFT -> {
                        beam.direction = Vector2d.DOWN
                        beam.position += Vector2d.DOWN
                    }

                    Vector2d.UP -> {
                        beam.direction = Vector2d.RIGHT
                        beam.position += Vector2d.RIGHT
                    }

                    Vector2d.DOWN -> {
                        beam.direction = Vector2d.LEFT
                        beam.position += Vector2d.LEFT
                    }

                    else -> throw Exception("this should never happen")
                }

                '\\' -> when (beam.direction) {
                    Vector2d.RIGHT -> {
                        beam.direction = Vector2d.DOWN
                        beam.position += Vector2d.DOWN
                    }

                    Vector2d.LEFT -> {
                        beam.direction = Vector2d.UP
                        beam.position += Vector2d.UP
                    }

                    Vector2d.UP -> {
                        beam.direction = Vector2d.LEFT
                        beam.position += Vector2d.LEFT
                    }

                    Vector2d.DOWN -> {
                        beam.direction = Vector2d.RIGHT
                        beam.position += Vector2d.RIGHT
                    }

                    else -> throw Exception("this should never happen")
                }

                else -> throw Exception("this should never happen")
            }

            beams += beam
        }

        return energized.distinctBy { it.position }.size
    }

    override fun part1(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        return countEnergized(grid, BeamPoint(Vector2d(0, 0), Vector2d.RIGHT))
    }

    override fun part2(input: List<String>): Int {
        val grid = Grid(input.map { it.toCharArray().toList() })

        val startingPoints = mutableListOf<BeamPoint>()

        for (x in 0 until grid.width) {
            startingPoints += BeamPoint(Vector2d(x, 0), Vector2d.DOWN)
            startingPoints += BeamPoint(Vector2d(x, grid.height - 1), Vector2d.UP)
        }

        for (y in 0 until grid.height) {
            startingPoints += BeamPoint(Vector2d(0, y), Vector2d.RIGHT)
            startingPoints += BeamPoint(Vector2d(grid.width - 1, y), Vector2d.LEFT)
        }


        return startingPoints.maxOf { countEnergized(grid, it) }
    }
}
