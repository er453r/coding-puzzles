package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Impossible
import com.er453r.codingpuzzles.utils.longs
import com.er453r.codingpuzzles.utils.pow
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 17")
class Day17 : AoCTestBase<String>(
    year = 2024,
    day = 17,
    testTarget1 = "5,7,3,0",
    puzzleTarget1 = "7,1,2,3,2,6,7,2,5",
    testTarget2 = "117440",
    puzzleTarget2 = "202356708354602",
) {
    private var regA: Long = 0
    private var regB: Long = 0
    private var regC: Long = 0

    private fun comboValue(value: Long): Long = when (value) {
        in (0L..3L) -> value
        4L -> regA
        5L -> regB
        6L -> regC
        else -> throw Impossible()
    }

    private fun run(initA: Long, initB: Long, initC: Long, program: List<Long>): String {
        regA = initA
        regB = initB
        regC = initC

        var pointer = 0
        val output = mutableListOf<Long>()

        while (pointer < program.size) {
            val op = program[pointer++]
            val literal = program[pointer++]
            val combo = comboValue(literal)

            when (op) {
                0L -> {
                    regA /= 2.pow(combo.toInt())
                }

                1L -> {
                    regB = regB.xor(literal)
                }

                2L -> {
                    regB = combo % 8
                }

                3L -> if (regA != 0L) pointer = literal.toInt()
                4L -> {
                    regB = regB.xor(regC)
                }

                5L -> {
                    output += combo % 8
                }

                6L -> {
                    regB = regA / 2.pow(combo.toInt())
                }

                7L -> {
                    regC = regA / 2.pow(combo.toInt())
                }

                else -> throw Impossible()
            }
        }

        return output.joinToString(",")
    }

    override fun part1(input: List<String>) = run(
        initA = input[0].longs().first(),
        initB = input[1].longs().first(),
        initC = input[2].longs().first(),
        program = input[4].longs()
    )


    override fun part2(input: List<String>): String {
        val initB = input[1].longs().first()
        val initC = input[2].longs().first()
        val program = input[4].longs()

        var a = 0L

        for (n in 1..program.size) {
            val target = program.takeLast(n).joinToString(",")
            var newA = a * 8

            while (true) {
                val comp = run(
                    initA = newA,
                    initB = initB,
                    initC = initC,
                    program = program,
                )

                if (comp == target) {
                    a = newA
                    break
                }
                newA += 1
            }
        }

        return a.toString()
    }
}
