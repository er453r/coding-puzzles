package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName
import kotlin.math.max

@DisplayName("AoC 2023 - Day 12")
class Day12 : AoCTestBase<Int>(
    year = 2023,
    day = 12,
    testTarget1 = 21,
    puzzleTarget1 = 8419,
    testTarget2 = 525152,
    puzzleTarget2 = null,
) {
    private fun countRow(row: String, groups: List<Int>): Int {
        val groupsString = groups.toString()
        val groupsSum = groups.sum()
        val dotSum = row.length - groups.sum()
        val stack = mutableListOf(row)
        var ok = 0

        val gRegex = "(#+)".toRegex()

        while (stack.isNotEmpty()) {
            val top = stack.removeLast()

            val firstUnknown = top.indexOfFirst { it == '?' }
            val topGroups = gRegex.findAll(top).map { it.value.length }.toList()

            if (firstUnknown == -1) {
                if (topGroups.toString() == groupsString)
                    ok++
                else
                    continue
            } else {
                val topGroupsKnown = gRegex.findAll(top.substring(0, max(firstUnknown, 0))).map { it.value.length }.toList()
                val dotCount = top.count { it == '.' }
                val groupCount = top.count { it == '#' }

                if (topGroupsKnown.size > groups.size || topGroupsKnown.indices.any { topGroupsKnown[it] > groups[it] }) {
                    continue
                } else if (topGroupsKnown.size > 1 && topGroupsKnown.take(topGroupsKnown.size - 1).indices.any { topGroupsKnown[it] != groups[it] }) {
                    continue
                } else if (dotCount > dotSum) {
                    continue
                } else if (groupCount > groupsSum) {
                    continue
                } else {
                    if(groupCount < groupsSum)
                        stack.add(top.replaceRange(firstUnknown, firstUnknown + 1, "#"))

                    if(dotCount < dotSum)
                        stack.add(top.replaceRange(firstUnknown, firstUnknown + 1, "."))
                }
            }
        }

//        println("$row ${ok}")

        return ok
    }

    override fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val row = line.split(" ").first()
            val groups = line.ints()

            countRow(row, groups)
        }
    }

    override fun part2(input: List<String>): Int {
        return input.mapIndexed { index, line ->
            val r = line.split(" ").first()
            val g = line.ints()
            val row = (0 until 5).joinToString("?") { r }
            val groups = (0 until 5).flatMap { g }

            println(index)

            countRow(row, groups)
        }.sum()
    }
}
