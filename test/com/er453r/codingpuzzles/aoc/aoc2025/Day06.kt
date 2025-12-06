package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 06")
@Disabled
class Day06 : AoCTestBase<Long>(
    year = 2025,
    day = 6,
    testTarget1 = 4277556,
    puzzleTarget1 = 6172481852142,
    testTarget2 = 3263827,
    puzzleTarget2 = 10188206723429,
) {
    override fun part1(input: List<String>): Long {
        val numbers = input.take(input.size - 1).map { it.trim().split("""\s+""".toRegex()).map { n -> n.toLong()} }

        val operations = input.last().trim().split("""\s+""".toRegex())

        return operations.mapIndexed { index, oper ->
            if(oper == "+")
                numbers.sumOf { it[index] }
            else
                numbers.fold(1L, { a, b -> a * b[index] })
        }.sum()
    }

    override fun part2(input: List<String>): Long {
        val numbers = input.take(input.size - 1).map { it.trim().split("""\s+""".toRegex()).map { n -> n.toLong()} }

        val operations = input.last().trim().split("""\s+""".toRegex())

        val columnSize = numbers.first().indices.map { n ->
            (0..<numbers.size).maxOf { numbers[it][n].toString().length }
        }

        val rows = input.take(input.size - 1).mapIndexed{ index, row ->
            val cols = mutableListOf<String>()
            var col = 0
            var left = row

            while(cols.size != columnSize.size){
                var substring = left.take(columnSize[col])

                if(substring.length < columnSize[col])
                    substring = substring.padEnd(columnSize[col])

               cols.add(substring)
               left = left.drop(columnSize[col] + 1)
                col++
            }

            cols
        }

        val values = rows.first().indices.map { column ->
            (0..<columnSize[column]).map { columnColumn ->
                var number = ""

                (0..<input.size -1).forEach { row ->
                    number += rows[row][column][columnColumn]
                }

                number.trim().toLong()
            }
        }

        return operations.mapIndexed { index, oper ->
            if(oper == "+")
                values[index].sumOf { it }
            else
                values[index].fold(1L, { a, b -> a * b })
        }.sum()
    }
}
