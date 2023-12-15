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
    fun hash(string: String):Int{
        var sum = 0

        string.forEach {
            sum += it.code
            sum *= 17
            sum %= 256
        }

        return sum
    }

    override fun part1(input: List<String>): Int {
        val steps = input.first().split(",")

        return steps.map { hash(it) }.sum()
    }

    override fun part2(input: List<String>): Int {
        val boxes = MutableList(256){ mutableListOf<String>() }
        val steps = input.first().split(",")

        steps.forEach { step ->
            if(step.endsWith("-")){
                val (label,_) = step.split("-")
                val box = hash(label)

                boxes[box].removeIf {
                    it.startsWith(label)
                }
            }
            else{
                val (label, focus) = step.split("=")
                val box = hash(label)

                val key = "$label $focus"

                val existing = boxes[box].indexOfFirst { it.startsWith(label) }

                if(existing == -1)
                    boxes[box].add(key)
                else
                    boxes[box][existing] = key
            }
        }

        return boxes.flatMapIndexed { boxIndex, box ->
            box.mapIndexed { lensIndex, lens  ->
                val (label, focus) = lens.split(" ")

                (boxIndex + 1) * (lensIndex + 1) * focus.toInt()
            }
        }.sum()
    }
}
