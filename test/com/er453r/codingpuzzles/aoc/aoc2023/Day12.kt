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
        val stack = mutableListOf(row)
        var ok = 0

        val gRegex = "(#+)".toRegex()

        while (stack.isNotEmpty()) {
            val top = stack.removeLast()

            if (top.length != row.length)
                println("Something is yesno!")

            val firstUnknown = top.indexOfFirst { it == '?' }

            val topGroups = gRegex.findAll(top).map { it.value.length }.toList()

            if (firstUnknown == -1) {
                if (topGroups.toString() == groupsString)
                    ok++
                else
                    continue
            } else {
                val topGroupsKnown = gRegex.findAll(top.substring(0, max(firstUnknown, 0))).map { it.value.length }.toList()
                val previous = if (firstUnknown > 1) top[firstUnknown - 1] else ""
                val hasNextGroup = topGroupsKnown.size < groups.size

                if (topGroupsKnown.size > groups.size || topGroupsKnown.indices.any { topGroupsKnown[it] > groups[it] }) {
                    continue
                } else if (topGroupsKnown.size > 1 && topGroupsKnown.take(topGroupsKnown.size - 1).indices.any { topGroupsKnown[it] != groups[it] }) {
                    continue
//                } else if (previous == '#') {
//                    val leftInGroup = groups[topGroupsKnown.size - 1] - topGroupsKnown.last()
//
//                    if(firstUnknown + leftInGroup  > top.length)
//                        continue
//
//                    val filledGroup = top.replaceRange(firstUnknown, firstUnknown + leftInGroup, "#".repeat(leftInGroup))
//
//                    if (firstUnknown + leftInGroup < filledGroup.length) {
//                        stack.add(filledGroup.replaceRange(firstUnknown + leftInGroup, firstUnknown + leftInGroup + 1, "."))
//                    }else{
//                        stack.add(filledGroup)
//                    }
//
//                    if (stack.takeLast(1).any { it.length != row.length })
//                        println("Something is yesno!")
                } else if (top.count { it == '#' } == groupsSum) {
                    stack.add(top.replaceRange(firstUnknown, top.length, ".".repeat(top.length - firstUnknown)))

                    if (stack.takeLast(1).any { it.length != row.length })
                        println("Something is yesno!")
//            } else if (previous == '.' && hasNextGroup) {
//                val nextGroup = groups[topGroupsKnown.size]
//
//                if (firstUnknown + nextGroup <= top.length)
//                    stack.add(top.replaceRange(firstUnknown, firstUnknown + nextGroup, "#".repeat(nextGroup)))
//
//                stack.add(top.replaceRange(firstUnknown, firstUnknown + 1, "."))
//
//                if (stack.takeLast(2).any { it.length != row.length })
//                    println("Something is yesno!")
            } else {
                stack.add(top.replaceRange(firstUnknown, firstUnknown + 1, "#"))
                stack.add(top.replaceRange(firstUnknown, firstUnknown + 1, "."))

                if (stack.takeLast(1).any { it.length != row.length })
                    println("Something is yesno!")
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
    return input.sumOf { line ->
        val r = line.split(" ").first()
        val g = line.ints()
        val row = (0 until 5).joinToString("?") { r }
        val groups = (0 until 5).flatMap { g }

        countRow(row, groups)
    }
}
}
