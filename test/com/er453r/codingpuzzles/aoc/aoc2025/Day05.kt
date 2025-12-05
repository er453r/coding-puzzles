package com.er453r.codingpuzzles.aoc.aoc2025

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.intersect
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2025 - Day 05")
class Day05 : AoCTestBase<Long>(
    year = 2025,
    day = 5,
    testTarget1 = 3,
    puzzleTarget1 = 505,
    testTarget2 = 14,
    puzzleTarget2 = null,
) {
    fun LongRange.merge(other: LongRange): LongRange {
        val newStart = minOf(this.first, other.first)
        val newEnd = maxOf(this.last, other.last) // last is equivalent to endInclusive for IntRange
        return newStart..newEnd // Creates the merged IntRange
    }

    override fun part1(input: List<String>): Long {
        val separator = input.indexOf("")

        val ranges = input.take(separator)
            .map { it.split("-") }
            .map { LongRange(it[0].toLong(), it[1].toLong()) }

        val ids = input.drop(separator + 1)

        return ids.count { id -> ranges.any { it.contains(id.toLong()) } }.toLong()
    }

    override fun part2(input: List<String>): Long {
        val separator = input.indexOf("")

        val ranges = input.take(separator)
            .map { it.split("-") }
            .map { LongRange(it[0].toLong(), it[1].toLong()) }.toMutableList()

        while(true){
            var merges = 0

            loop@ for (i in 0 until ranges.size - 1) {
                for (j in i + 1 until ranges.size) {
                    if(ranges[i].intersect(ranges[j]) != null){
                        merges++
                        ranges[i] = ranges[i].merge(ranges[j])
                        ranges.removeAt(j)

                        break@loop
                    }
                }
            }

            if(merges == 0)
                break
        }

//        val sum = ranges.map { it.count().toBigInteger() }
//
//        println(sum)

        return ranges.sumOf { it.last - it.first + 1 }
    }
}
