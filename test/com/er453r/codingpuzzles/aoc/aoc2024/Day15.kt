package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.*
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 15")
class Day15 : AoCTestBase<Int>(
    year = 2024,
    day = 15,
    testTarget1 = 10092,
    puzzleTarget1 = 1479679,
    testTarget2 = 9021,
    puzzleTarget2 = 1509780,
) {
    private fun tryToMove(cell: GridCell<Char>, direction: Vector2d):Boolean {
        if (cell.value == '#')
            return false

        if (cell.value == '.')
            return true

        val neighbour = cell.neighbour(direction)

        val neighbourMoved = tryToMove(neighbour, direction)

        if (neighbourMoved) {
            neighbour.value = cell.value
            cell.value = '.'
        }

        return neighbourMoved
    }

    override fun part1(input: List<String>): Int {
        val parts = input.split()
        val grid = Grid(parts.first().map { it.toCharArray().toList() })
        val moves = parts.last().joinToString("").toCharArray().map {
            when(it) {
                '^' -> Vector2d.UP
                'v' -> Vector2d.DOWN
                '<' -> Vector2d.LEFT
                '>' -> Vector2d.RIGHT
                else -> throw Impossible()
            }
        }
        var robot = grid.data.flatten().first { it.value == '@' }

        moves.forEach { move ->
            if(tryToMove(robot, move))
                robot = robot.neighbour(move)
        }

        grid.print { grid[it].value }

        return grid.data.flatten().filter { it.value == 'O' }.sumOf { it.position.x + 100 * it.position.y }
    }

    val boxes = mapOf('[' to Vector2d.RIGHT, ']' to Vector2d.LEFT)

    private fun canMove(cell: GridCell<Char>, direction: Vector2d):Boolean {
        if (cell.value == '#')
            return false

        if (cell.value == '.')
            return true

        if (cell.value in boxes.keys && (direction == Vector2d.UP || direction == Vector2d.DOWN)) {
            val other = cell.neighbour(boxes[cell.value]!!)
            val neighbours = listOf(cell.neighbour(direction), other.neighbour(direction))

            val a = canMove(neighbours[0], direction)
            val b = canMove(neighbours[1], direction)

            return a && b
        }

        val neighbour = cell.neighbour(direction)
        return canMove(neighbour, direction)
    }

    private fun tryToMove2(cell: GridCell<Char>, direction: Vector2d) {
        if(cell.value == '.' || cell.value == '#')
            return

        if (cell.value in boxes.keys && (direction == Vector2d.UP || direction == Vector2d.DOWN)) {
            val other = cell.neighbour(boxes[cell.value]!!)
            val neighbours = listOf(cell.neighbour(direction), other.neighbour(direction))

            if (neighbours.all { canMove(it, direction) }) {
                neighbours.forEach { tryToMove2(it, direction) }

                neighbours[0].value = cell.value
                cell.value = '.'
                neighbours[1].value = other.value
                other.value = '.'
            }

            return
        }

        val neighbour = cell.neighbour(direction)

        if(canMove(neighbour, direction)){
            tryToMove2(neighbour, direction)

            neighbour.value = cell.value
            cell.value = '.'
        }
    }

    override fun part2(input: List<String>): Int {
        val parts = input.split()
        val wider = parts.first().map { it
            .replace("#", "##")
            .replace("O", "[]")
            .replace(".", "..")
            .replace("@", "@.")
        }
        val grid = Grid(wider.map { it.toCharArray().toList() })
        val moves = parts.last().joinToString("").toCharArray().map {
            when(it) {
                '^' -> Vector2d.UP
                'v' -> Vector2d.DOWN
                '<' -> Vector2d.LEFT
                '>' -> Vector2d.RIGHT
                else -> throw Impossible()
            }
        }
        var robot = grid.data.flatten().first { it.value == '@' }

//        grid.print { grid[it].value }

        moves.forEach { move ->
            robot = grid.data.flatten().first { it.value == '@' }
            tryToMove2(robot, move)

//            grid.print { grid[it].value }
        }

//        grid.print { grid[it].value }

        return grid.data.flatten().filter { it.value == '[' }.sumOf { it.position.x + 100 * it.position.y }
    }
}
