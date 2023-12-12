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
    private fun damagedGroups(line: String) = "(#+)".toRegex().findAll(line).map { it.value.length }.toList()


    private fun addVariants(string: String, maxLength: Int, ok: Int, damaged: Int): List<String> {
        if (string.length == maxLength)
            return listOf(string)

        if (string.count { it == '#' } == damaged)
            return listOf(string + ".".repeat(maxLength - string.length))

        if (string.count { it == '.' } == ok)
            return listOf(string + "#".repeat(maxLength - string.length))

        return addVariants("$string.", maxLength, ok, damaged) + addVariants("$string#", maxLength, ok, damaged)
    }

    private fun gapsGroupsToString(gaps: List<Int>, groups: List<Int>): String {
        return List(groups.size) { index ->
            ".".repeat(gaps[index]) + "#".repeat(groups[index])
        }.joinToString("") + ".".repeat(gaps.last())
    }

    private fun gapPermutations(gaps: List<Int>, toGive: Int, index: Int = 0): List<List<Int>> {
        if (index > gaps.size - 1)
            return emptyList()

        if (toGive == 0)
            return listOf(gaps)

        return (toGive downTo 0).flatMap {
            val newList = gaps.toMutableList()
            newList[index] += it

            gapPermutations(newList, toGive - it, index + 1)
        }
    }

    override fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val r = line.split(" ").first()
            val g = line.ints()
            val row = r
            val groups = g
            val groupsString = groups.toString()

            val unknownIndexes = row.indices.filter { row[it] == '?' }
            val maxDamaged = groups.sum()
            val maxKnownDamaged = row.indices.filter { row[it] == '#' }.count()
            val unknownDamaged = maxDamaged - maxKnownDamaged
            val unknownOk = unknownIndexes.size - unknownDamaged
            val maxOK = row.length - maxDamaged

            val variants = addVariants("", unknownIndexes.size, unknownOk, unknownDamaged)

            variants.map { variant ->
                val realString = row.indices.map {
                    if (it in unknownIndexes)
                        variant[unknownIndexes.indexOf(it)]
                    else
                        row[it]
                }.joinToString("")

                val damaged = damagedGroups(realString)

                damaged.toString()
            }.count { it == groupsString }
        }
    }

    override fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val r = line.split(" ").first()
            val g = line.ints()
            val row = r + r + r + r
            val groups = g + g + g + g + g
            val groupsString = groups.toString()

            val unknownIndexes = row.indices.filter { row[it] == '?' }
            val maxDamaged = groups.sum()
            val maxKnownDamaged = row.indices.filter { row[it] == '#' }.count()
            val unknownDamaged = maxDamaged - maxKnownDamaged
            val unknownOk = unknownIndexes.size - unknownDamaged
            val maxOK = row.length - maxDamaged

            val variants = addVariants("", unknownIndexes.size, unknownOk, unknownDamaged)

            variants.map { variant ->
                val realString = row.indices.map {
                    if (it in unknownIndexes)
                        variant[unknownIndexes.indexOf(it)]
                    else
                        row[it]
                }.joinToString("")

                val damaged = damagedGroups(realString)

                damaged.toString()
            }.count { it == groupsString }
        }
    }
}
