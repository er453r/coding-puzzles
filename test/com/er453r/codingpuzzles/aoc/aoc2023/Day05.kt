package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.longs
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 05")
class Day05 : AoCTestBase<Long>(
    year = 2023,
    day = 5,
    testTarget1 = 35,
    puzzleTarget1 = 265018614,
    testTarget2 = 46,
    puzzleTarget2 = 63179500,
) {
    override fun part1(input: List<String>): Long {
        val chunks = input.split()
        val seeds = chunks.first().first().longs()
        val transforms = chunks.drop(1).map {
            it.drop(1).map {
                val (destination, source, length) = it.longs()

                LongRange(source, source + length - 1) to destination
            }
        }

        return seeds.map {
//            println("ssed $it")
            var value = it

            transforms.forEach {
                for (ranges in it) {
                    if (ranges.first.contains(value)) {
                        value = ranges.second + value - ranges.first.first
                        break
                    }
                }
            }
//            println("location $value")
            value

        }.min()
    }

    private fun LongRange.intersect(other: LongRange): LongRange? = if (this.first <= other.last && other.first <= this.last)
        maxOf(this.first, other.first).rangeTo(minOf(this.last, other.last))
    else
        null

    override fun part2(input: List<String>): Long {
        val chunks = input.split()
        val seeds = chunks.first().first().longs().chunked(2).map { LongRange(it.first(), it.first() + it.last() - 1) }
        val transforms = chunks.drop(1).map {
            it.drop(1).map {
                val (destination, source, length) = it.longs()

                LongRange(source, source + length - 1) to destination - source
            }
        }

        var unprocessed = seeds.toMutableList()

        transforms.forEach {
            val processed = mutableListOf<LongRange>()

            derp@ while (unprocessed.isNotEmpty()) {
                val range = unprocessed.removeLast()

                for (transform in it) {
                    val intersection = range.intersect(transform.first)

                    if (intersection != null) {
                        val rangeBefore = LongRange(range.first, intersection.first - 1)

                        if (!rangeBefore.isEmpty())
                            unprocessed.add(rangeBefore)

                        val rangeAfter = LongRange(intersection.last + 1, range.last)

                        if (!rangeAfter.isEmpty())
                            unprocessed.add(rangeAfter)

                        processed.add(LongRange(intersection.first + transform.second, intersection.last + transform.second))

                        continue@derp
                    }
                }

                processed.add(range)
            }

            unprocessed = processed
        }

        return unprocessed.map { it.min() }.min()
    }
}
