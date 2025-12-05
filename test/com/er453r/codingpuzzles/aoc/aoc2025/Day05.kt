package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.intersect
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 05")
class Day05 : AoCTestBase<Long>(
    year = 2025,
    day = 5,
    testTarget1 = 3,
    puzzleTarget1 = 505,
    testTarget2 = 14,
    puzzleTarget2 = 344423158480189,
) {
    fun LongRange.merge(other: LongRange): LongRange {
        val newStart = minOf(this.first, other.first)
        val newEnd = maxOf(this.last, other.last) // last is equivalent to endInclusive for IntRange
        return newStart..newEnd // Creates the merged IntRange
    }

    override fun part1(input: List<String>): Long {
        val parts = input.split()
        val ranges = parts.first()
            .map { it.split("-") }
            .map { LongRange(it[0].toLong(), it[1].toLong()) }
        val ids = parts.last().map { it.toLong() }

        return ids.count { id -> ranges.any { it.contains(id) } }.toLong()
    }

    override fun part2(input: List<String>): Long {
        val parts = input.split()
        val ranges = parts.first()
            .map { it.split("-") }
            .map { LongRange(it[0].toLong(), it[1].toLong()) }
            .toMutableList()

        loop@ while (true) {
            for (i in 0 until ranges.size - 1) {
                for (j in i + 1 until ranges.size) {
                    if (ranges[i].intersect(ranges[j]) != null) {
                        ranges[i] = ranges[i].merge(ranges[j])
                        ranges.removeAt(j)

                        continue@loop
                    }
                }
            }

            break
        }

        return ranges.sumOf { it.last - it.first + 1 }
    }
}
