package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName
import kotlin.math.min

@DisplayName("AoC 2024 - Day 05")
class Day05 : AoCTestBase<Int>(
    year = 2024,
    day = 5,
    testTarget1 = 143,
    puzzleTarget1 = 4814,
    testTarget2 = 123,
    puzzleTarget2 = 5448,
) {
    private fun isUpdateCorrect(update: List<Int>, rules: List<List<Int>>) = update.indices.none { n ->
        val after = update.subList(min(update.size, n + 1), update.size).toSet()
        val before = update.subList(0, n).toSet()
        val rulesAfter = rules.filter { it.first() == update[n] }.map { it.last() }.filter { it in update }.toSet()
        val rulesBefore = rules.filter { it.last() == update[n] }.map { it.first() }.filter { it in update }.toSet()

        !(after.containsAll(rulesAfter) && before.containsAll(rulesBefore))
    }

    override fun part1(input: List<String>): Int {
        val rules = input.filter { it.contains("|") }.map { it.ints() }
        val updates = input.filter { it.contains(",") }.map { it.ints() }

        return updates
            .filter { isUpdateCorrect(it, rules) }
            .sumOf { it[it.size / 2] }
    }

    override fun part2(input: List<String>): Int {
        val rules = input.filter { it.contains("|") }.map { it.ints() }
        val updates = input.filter { it.contains(",") }.map { it.ints() }

        return updates.filter { !isUpdateCorrect(it, rules) }.sumOf { update ->
            val ordered = mutableListOf<Int>()

            update.forEach { n ->
                val hasToBeBefore = rules.filter { it.first() == n }.map { it.last() }.filter { it in update }.toSet()
                val hasToBeAfter = rules.filter { it.last() == n }.map { it.last() }.filter { it in update }.toSet()

                for (insertAt in 0..ordered.size) {
                    val after = ordered.subList(insertAt, ordered.size).toSet()
                    val before = ordered.subList(0, insertAt).toSet()

                    if (after.intersect(hasToBeBefore).isEmpty() && before.intersect(hasToBeAfter).isEmpty()) {
                        ordered.add(insertAt, n)

                        break
                    }
                }
            }

            ordered[ordered.size / 2]
        }
    }
}
