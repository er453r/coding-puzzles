package com.er453r.codingpuzzles.aoc

import com.er453r.codingpuzzles.utils.aocTest
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.JVM)
abstract class AoCTestBase<T>(
    val year: Int,
    val day: Int,
    val testTarget1: T,
    val testTarget2: T?,
    val puzzleTarget1: T?,
    val puzzleTarget2: T?,
) {
    abstract fun part1(input: List<String>): T
    abstract fun part2(input: List<String>): T

    @Test
    fun `Part 1 - test data`() {
        aocTest(
            year = year,
            day = day,
            logic = ::part1,
            target = testTarget1,
            useTestInput = true,
        )
    }

    @Test
    fun `Part 1 - puzzle data`() {
        aocTest(
            year = year,
            day = day,
            logic = ::part1,
            target = puzzleTarget1,
            useTestInput = false,
        )
    }

    @Test
    fun `Part 2 - test data`() {
        aocTest(
            year = year,
            day = day,
            logic = ::part2,
            target = testTarget2,
            useTestInput = true,
        )
    }

    @Test
    fun `Part 2 - puzzle data`() {
        aocTest(
            year = year,
            day = day,
            logic = ::part2,
            target = puzzleTarget2,
            useTestInput = false,
        )
    }
}
