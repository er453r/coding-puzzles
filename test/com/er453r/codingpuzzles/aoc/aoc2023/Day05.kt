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
    private fun parseInput(input: List<String>): Pair<List<Long>, List<List<Pair<LongRange, Long>>>> {
        val chunks = input.split()
        val seeds = chunks.first().first().longs()
        val transforms = chunks.drop(1).map { transform ->
            transform.drop(1)
                .map { it.longs() }
                .map { (destination, source, length) ->
                    LongRange(source, source + length - 1) to destination - source
                }
        }

        return Pair(seeds, transforms)
    }

    override fun part1(input: List<String>) = parseInput(input).let { (seeds, transforms) ->
        seeds.minOf { seed ->
            var value = seed

            transforms.forEach {
                value = it.firstOrNull { range -> range.first.contains(value) }?.let { range -> value + range.second } ?: value
            }

            value
        }
    }

    private fun LongRange.intersect(other: LongRange): LongRange? = if (this.first <= other.last && other.first <= this.last)
        maxOf(this.first, other.first).rangeTo(minOf(this.last, other.last))
    else
        null

    override fun part2(input: List<String>) = parseInput(input).let { (tempSeeds, transforms) ->
        val seeds = tempSeeds.chunked(2).map { LongRange(it.first(), it.first() + it.last() - 1) }
        var unprocessed = seeds.toMutableList()

        transforms.forEach {
            val processed = mutableListOf<LongRange>()

            loop@ while (unprocessed.isNotEmpty()) {
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

                        continue@loop
                    }
                }

                processed.add(range)
            }

            unprocessed = processed
        }

        unprocessed
    }.minOf { it.min() }
}
