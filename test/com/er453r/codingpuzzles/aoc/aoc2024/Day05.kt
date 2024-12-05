package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName
import kotlin.math.max
import kotlin.math.min

@DisplayName("AoC 2024 - Day 05")
class Day05 : AoCTestBase<Int>(
    year = 2024,
    day = 5,
    testTarget1 = 143,
    puzzleTarget1 = 4814,
    testTarget2 = 123,
    puzzleTarget2 = null,
) {
    override fun part1(input: List<String>): Int {
        val rules = input.filter { it.contains("|") }.map { it.ints() }
        val updates = input.filter { it.contains(",") }.map { it.ints() }

        var sum = 0

        updates.forEach { update ->
            var incorrect = 0

            update.indices.forEach { n ->
                val rulesAfter = rules.filter { it.first() == update[n] }.map { it.last() }.filter { it in update }

                val correctAfter = update.subList(min(update.size, n + 1), update.size).filter { it in rulesAfter }

                val rulesBefore = rules.filter { it.last() == update[n] }.map { it.first() }.filter { it in update }

                val correctBefore = update.subList(0, n).filter { it in rulesBefore }

                if(rulesAfter.size != correctAfter.size || rulesBefore.size != correctBefore.size)
                    incorrect ++
            }

            if(incorrect == 0)
                sum += update[update.size/2]
        }

        return sum
    }

    override fun part2(input: List<String>): Int {
        val rules = input.filter { it.contains("|") }.map { it.ints() }
        val updates = input.filter { it.contains(",") }.map { it.ints() }



        val inccrrect = mutableListOf<List<Int>>()

        updates.forEach { update ->
            var incorrectCounter = 0

            update.indices.forEach { n ->
                val rulesAfter = rules.filter { it.first() == update[n] }.map { it.last() }.filter { it in update }

                val correctAfter = update.subList(min(update.size, n + 1), update.size).filter { it in rulesAfter }

                val rulesBefore = rules.filter { it.last() == update[n] }.map { it.first() }.filter { it in update }

                val correctBefore = update.subList(0, n).filter { it in rulesBefore }

                if(rulesAfter.size != correctAfter.size || rulesBefore.size != correctBefore.size)
                    incorrectCounter++
            }

            if(incorrectCounter > 0)
                inccrrect.add(update)
        }

        var sum = 0

        inccrrect.forEach { update ->
            val ordered = mutableListOf<Int>()

            update.forEach { n ->
                val hasToBeBefore = rules.filter { it.first() == n }.map { it.last() }.filter { it in update }.toSet()
                val hasToBeAfter = rules.filter { it.last() == n }.map { it.last() }.filter { it in update }.toSet()

                for (insertAt in 0..ordered.size){
                    val after = ordered.subList(insertAt, ordered.size).toSet()
                    val before = ordered.subList(0, insertAt).toSet()

                    if(after.intersect(hasToBeBefore).size == 0 && before.intersect(hasToBeAfter).size == 0) {
                        ordered.add(insertAt, n)
                        break
                    }
                }
            }

            sum += ordered[ordered.size / 2]
        }

        return sum
    }
}
