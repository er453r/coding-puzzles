package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 15")
class Day15 : AoCTestBase<Int>(
    year = 2023,
    day = 15,
    testTarget1 = 1320,
    puzzleTarget1 = 505427,
    testTarget2 = 145,
    puzzleTarget2 = 243747,
) {
    private fun hash(string: String) = string.fold(0) { sum, it ->
        ((sum + it.code) * 17) % 256
    }

    override fun part1(input: List<String>) = input.first().split(",").sumOf { hash(it) }

    override fun part2(input: List<String>): Int {
        val boxes = MutableList(256) { mutableListOf<String>() }

        input.first().split(",").forEach { step ->
            if (step.endsWith("-")) {
                val (label, _) = step.split("-")
                val box = hash(label)

                boxes[box].removeIf {
                    it.startsWith(label)
                }
            } else {
                val (label, focus) = step.split("=")
                val box = hash(label)
                val key = "$label $focus"
                val indexOfFirst = boxes[box].indexOfFirst { it.startsWith(label) }

                if (indexOfFirst == -1)
                    boxes[box].add(key)
                else
                    boxes[box][indexOfFirst] = key
            }
        }

        return boxes.flatMapIndexed { boxIndex, box ->
            box.mapIndexed { lensIndex, lens ->
                val (_, focus) = lens.split(" ")

                (boxIndex + 1) * (lensIndex + 1) * focus.toInt()
            }
        }.sum()
    }
}
