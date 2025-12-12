package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Grid
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 12")
class Day12 : AoCTestBase<Int>(
    year = 2025,
    day = 12,
    testTarget1 = 2,
    puzzleTarget1 = null,
    testTarget2 = null,
    puzzleTarget2 = null,
) {
    data class Shape(
        val width: Int,
        val height: Int,
        val points: Set<Vector2d>,
    ) {
        fun mirror() = Shape(width, height, points.map { Vector2d(width - it.x, it.y) }.toSet())
        fun rotate() = Shape(height, width, points.map { Vector2d(width - it.y, it.x) }.toSet())
    }

    data class State(
        val width: Int,
        val height: Int,
        val shapesCount: List<Int>,
        val points: Set<Vector2d>,
    )

    override fun part1(input: List<String>): Int {
        val parts = input.split()
        val shapes = parts.dropLast(1).map {
            val grid = Grid(it.drop(1).map { line -> line.toCharArray().toList() })

            Shape(grid.width, grid.height, grid.data.flatten().filter { it.value == '#' }.map { it.position }.toSet())
        }

        val variations = shapes.indices.associateWith {
            listOf(shapes[it], shapes[it].mirror()).flatMap { shape ->
                listOf(shape, shape.rotate(), shape.rotate().rotate(), shape.rotate().rotate().rotate())
            }
        }

        val regions = parts.last().map {
            val parts = it.split(": ")
            val (width, height) = parts.first().ints()
            val shapesCount = parts.last().ints()

            State(width, height, shapesCount, (0..<width).flatMap { x -> (0..<height).map { y -> Vector2d(x, y) } }.toSet())
        }

        var count = 0

        println("Starting region loop")

        region@for(region in regions){
            println("$count - $region")

            val queue = ArrayDeque(listOf(region))

            while (queue.isNotEmpty()) {
                val current = queue.removeLast()

                val nonZero = current.shapesCount.indices.filter { current.shapesCount[it] > 0 }

                for(index in nonZero){
                    for(shape in variations[index]!!){
                        val maxX = current.width - shape.width
                        val maxY = current.height - shape.height
                        val startPoints = current.points.filter { it.x < maxX && it.y < maxY }

                        for(startPoint in startPoints){
                            val placedShapePoints = shape.points.map { it + startPoint }.toSet()

                            if(current.points.containsAll(placedShapePoints)){ // shape variation fits!
                                val shapesCount = current.shapesCount.toMutableList().also { it[index]-- }

                                if(shapesCount.all { it == 0 }) {
                                    count++
                                    continue@region
                                }

                                queue.add(
                                    State(
                                        width = current.width,
                                        height = current.height,
                                        shapesCount = shapesCount,
                                        points = current.points - placedShapePoints,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        return count
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}
