package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.intersect
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
    private fun solve(input: List<String>, longsToRanges: (List<Long>) -> List<LongRange>): Long {
        val chunks = input.split()
        val seeds = longsToRanges(chunks.first().first().longs())
        val transforms = chunks.drop(1).map { transform ->
            transform.drop(1)
                .map { it.longs() }
                .map { (destination, source, length) ->
                    LongRange(source, source + length - 1) to destination - source
                }
        }

        var unprocessed = seeds.toMutableList()

        transforms.forEach { transformMap ->
            val processed = mutableListOf<LongRange>()

            loop@ while (unprocessed.isNotEmpty()) {
                val range = unprocessed.removeLast()

                for (transform in transformMap) {
                    val intersection = range.intersect(transform.first)

                    if (intersection != null) {
                        listOf(
                            LongRange(range.first, intersection.first - 1),
                            LongRange(intersection.last + 1, range.last)
                        ).filter { !it.isEmpty() }.forEach { unprocessed.add(it) }

                        processed.add(LongRange(intersection.first + transform.second, intersection.last + transform.second))

                        continue@loop
                    }
                }

                processed.add(range)
            }

            unprocessed = processed
        }

        return unprocessed.minOf { it.first }
    }

    override fun part1(input: List<String>) = solve(input) { longs ->
        longs.map { LongRange(it, it) }
    }

    override fun part2(input: List<String>) = solve(input) { longs ->
        longs.chunked(2).map { LongRange(it.first(), it.first() + it.last() - 1) }
    }
}
