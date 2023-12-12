package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 12")
class Day12 : AoCTestBase<Int>(
    year = 2023,
    day = 12,
    testTarget1 = 21,
    puzzleTarget1 = 8419,
    testTarget2 = 525152,
    puzzleTarget2 = null,
) {
    private fun countRow(row: List<Char>, groups: List<Int>): Int {
        val groupsString = groups.toString()
        val groupsSum = groups.sum()
        val maxGroup = groups.max()
        val okSum = row.size - groupsSum

        val stack = mutableListOf(row)
        val ok = mutableListOf<List<Char>>()

        val gRegex = "(#+)".toRegex()

        while (stack.isNotEmpty()) {
            val top = stack.removeLast()
            val firstUnknown = top.indexOfFirst { it == '?' }

            val topGroups = gRegex.findAll(top.joinToString("")).map { it.value.length }.toList()

            if (firstUnknown == -1){
                if(topGroups.toString() == groupsString)
                    ok.add(top)
                else
                    continue
                }
            else if(top.count { it == '#' } > groupsSum)
                continue
            else if(top.count { it == '.' } > okSum)
                continue
//            else if(topGroups.size > groups.size )
//                continue
//            else if(topGroups.indices.indexOfFirst { topGroups[it] > groups[it] } != -1)
//                continue
            else {
                stack.add(top.toMutableList().also { l -> l[firstUnknown] = '#' })
                stack.add(top.toMutableList().also { l -> l[firstUnknown] = '.' })
            }
        }

        return ok.size
    }

    override fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val row = line.split(" ").first().toCharArray().toList()
            val groups = line.ints()

            countRow(row, groups)
        }
    }

    override fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val r = line.split(" ").first()
            val g = line.ints()
            val row = (r + r + r + r + r).toCharArray().toList()
            val groups = (g + g + g + g + g)

            countRow(row, groups)
        }
    }
}
