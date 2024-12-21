package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.*
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

    private fun toPath(input:String, start:Vector2d):List<Vector2d> {
        val path = mutableListOf(start)

        input.toCharArray().forEach {
            path += path.last() + when(it){
                '<' -> Vector2d.LEFT
                '>' -> Vector2d.RIGHT
                '^' -> Vector2d.UP
                'v' -> Vector2d.DOWN
                else -> throw Exception("Invalid input: $it")
            }
        }

        return path
    }

//    private val paths = memoize<Vector2d, Vector2d, Map<Char, Vector2d>, Vector2d, String> { from ->
//        if (pattern.isEmpty())
//            1
//        else
//            towels.filter { pattern.startsWith(it) }.sumOf { this(pattern.replaceFirst(it, "")) }
//    }

    private fun generic(input:String, map:Map<Char, Vector2d>, forbidden:Vector2d):String{
        var previousKey = map['A']!!

        return input.toCharArray().joinToString("") { keyCode ->
            val startKey = previousKey
            val nextKey = map[keyCode]!!
            val diff = nextKey - previousKey
            previousKey = nextKey

            var sequence = (if(diff.x > 0) ">" else "<").repeat(diff.x.absoluteValue)
            sequence += (if(diff.y > 0) "v" else "^").repeat(diff.y.absoluteValue)

            if(sequence.contains("<") && !sequence.startsWith("<"))
                sequence = sequence.reversed()

            if(sequence.contains(">") && !sequence.endsWith(">"))
                sequence = sequence.reversed()

            if(forbidden in toPath(sequence, startKey))
                sequence = sequence.reversed()

            sequence + "A"
        }
    }

    private fun numeric2directional(numeric:String) = generic(numeric, numericMap, Vector2d(0, 3))

    private fun directional2directional(directional:String) = generic(directional, directionalMap, Vector2d(0, 0))

    override fun part1(input: List<String>):Int{
        return input.sumOf {
            val directional = numeric2directional(it)
            val directional1 = directional2directional(directional)
            val directional2 = directional2directional(directional1)

//            println(directional)
//            println(directional1)
//            println(directional2)

            println("$it ${directional2.length}")

            directional2.length * it.ints().first()
        }
    }

    override fun part2(input: List<String>):Int{
        return input.sumOf {
            var sequence = numeric2directional(it)

            repeat(25){
                sequence = directional2directional(sequence)
            }

            sequence.length * it.ints().first()
        }
    }
}
