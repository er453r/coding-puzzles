package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName
import java.math.BigInteger

// this is a template for this year
@DisplayName("AoC 2023 - Day 04")
class Day04 : AoCTestBase<Int>(
    year = 2023,
    day = 4,
    testTarget1 = 13,
    puzzleTarget1 = 21138,
    testTarget2 = 30,
    puzzleTarget2 = 7185540,
) {
    fun Int.pow(exp: Int): Long {
        return BigInteger.valueOf(this.toLong()).pow(exp).toLong()
    }

    override fun part1(input: List<String>): Int {
        return input.map { line ->
            val parts = line.split(":")
            val parts2 = parts.last().split("|")
            val winning = parts2[0].ints().toSet()
            val all = parts2[1].ints().toSet()
            val matches = all.intersect(winning)

//            println(matches.size)
//            println(2.pow(matches.size - 1))

            if(matches.size > 0)
                2.pow(matches.size - 1)
            else 0
        }.sum().toInt()
    }

    override fun part2(input: List<String>): Int {
        val production = input.associate { line ->
            val parts = line.split(":")
            val id = parts[0].ints().first()
            val parts2 = parts.last().split("|")
            val winning = parts2[0].ints().toSet()
            val all = parts2[1].ints().toSet()
            val matches = all.intersect(winning)

            id to (id + 1 .. id + matches.size).toSet()
        }

        val stack = production.keys.toMutableList()
        var counter = 0

        while(stack.isNotEmpty()){
            counter++
            val top = stack.removeLast()

            if(production.contains(top))
                stack.addAll(production[top]!!)
        }

        return counter
    }
}
