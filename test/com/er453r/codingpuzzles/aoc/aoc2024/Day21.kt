package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.memoize
import org.junit.jupiter.api.DisplayName
import kotlin.math.absoluteValue

@DisplayName("AoC 2024 - Day 21")
class Day21 : AoCTestBase<Int>(
    year = 2024,
    day = 21,
    testTarget1 = 126384,
    puzzleTarget1 = 109758,
    testTarget2 = 2,
    puzzleTarget2 = null,
) {
    private val numericMap = mapOf(
        '7' to Vector2d(0, 0),
        '8' to Vector2d(1, 0),
        '9' to Vector2d(2, 0),
        '4' to Vector2d(0, 1),
        '5' to Vector2d(1, 1),
        '6' to Vector2d(2, 1),
        '1' to Vector2d(0, 2),
        '2' to Vector2d(1, 2),
        '3' to Vector2d(2, 2),
        '0' to Vector2d(1, 3),
        'A' to Vector2d(2, 3),
    )

    private val directionalMap = mapOf(
        '^' to Vector2d(1, 0),
        'A' to Vector2d(2, 0),
        '<' to Vector2d(0, 1),
        'v' to Vector2d(1, 1),
        '>' to Vector2d(2, 1),
    )

    private fun toPath(input: String, start: Vector2d): List<Vector2d> {
        val path = mutableListOf(start)

        input.toCharArray().forEach {
            path += path.last() + when (it) {
                '<' -> Vector2d.LEFT
                '>' -> Vector2d.RIGHT
                '^' -> Vector2d.UP
                'v' -> Vector2d.DOWN
                else -> throw Exception("Invalid input: $it")
            }
        }

        return path
    }

    private val paths = memoize<Vector2d, Vector2d, Vector2d, Int, String> { from, to, forbidden, depth ->
        if(depth == 0){
            val diff = to - from

            var sequence = (if (diff.x > 0) ">" else "<").repeat(diff.x.absoluteValue)
            sequence += (if (diff.y > 0) "v" else "^").repeat(diff.y.absoluteValue)

            if (sequence.contains("<") && !sequence.startsWith("<"))
                sequence = sequence.reversed()

            if (sequence.contains(">") && !sequence.endsWith(">"))
                sequence = sequence.reversed()

            if (forbidden in toPath(sequence, from))
                sequence = sequence.reversed()

            sequence + "A"
        }
        else{
            val sequence = this(from, to, forbidden, depth - 1)

            sequence
        }
    }

    private fun generic(input: String, map: Map<Char, Vector2d>, forbidden: Vector2d, depth:Int): String {
        var previousKey = map['A']!!

        return input.toCharArray().joinToString("") { keyCode ->
            val sequence = paths(previousKey, map[keyCode]!!, forbidden, depth)

            previousKey = map[keyCode]!!

            sequence
        }
    }


    private fun numeric2directional(numeric: String) = generic(numeric, numericMap, Vector2d(0, 3), 0)

    private fun directional2directional(directional: String, depth:Int) = generic(directional, directionalMap, Vector2d(0, 0), depth)

    private fun solve(input: List<String>, times: Int) = input.sumOf {
        var sequence = numeric2directional(it)

        repeat(times) { iter ->
            println("iter: $iter")

            sequence = directional2directional(sequence, iter)
        }

        sequence.length * it.ints().first()
    }

    override fun part1(input: List<String>) = solve(input, 2)

    override fun part2(input: List<String>) = solve(input, 25)
}
