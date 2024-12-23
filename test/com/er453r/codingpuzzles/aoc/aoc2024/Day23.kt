package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 23")
class Day23 : AoCTestBase<String>(
    year = 2024,
    day = 23,
    testTarget1 = "7",
    puzzleTarget1 = "1284",
    testTarget2 = "co,de,ka,ta",
    puzzleTarget2 = "bv,cm,dk,em,gs,jv,ml,oy,qj,ri,uo,xk,yw",
) {
    private fun expand(input: List<String>, loop: Boolean): Set<Set<String>> {
        val sets = mutableMapOf<String, Set<String>>()
        var nets = mutableSetOf<Set<String>>()

        input.map { it.split("-") }.forEach { (a, b) ->
            nets += setOf(a, b)

            sets[a] = sets.getOrDefault(a, emptySet()) + b
            sets[b] = sets.getOrDefault(b, emptySet()) + a
        }

        while (true) {
            val expanded = mutableSetOf<Set<String>>()

            nets.forEach { net ->
                val ordered = net.sorted()
                var common = sets[ordered[0]]!!.intersect(sets[ordered[1]]!!)

                for (i in 2..<net.size)
                    common = common.intersect(sets[ordered[i]]!!)

                common.forEach { c -> expanded += net + c }
            }

            if (expanded.size == 0)
                break
            else
                nets = expanded

            if(!loop)
                break
        }

        return nets
    }

    override fun part1(input: List<String>) = expand(input, loop = false).count { s -> s.any { it.startsWith("t") } }.toString()

    override fun part2(input: List<String>) = expand(input, loop = true).maxBy { it.size }.sortedBy { it }.joinToString(",")
}
