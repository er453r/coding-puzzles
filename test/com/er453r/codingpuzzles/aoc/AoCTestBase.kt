package com.er453r.codingpuzzles.aoc

import com.er453r.codingpuzzles.utils.aocTest
import org.junit.jupiter.api.*

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
abstract class AoCTestBase<T>(
    val year: Int,
    val day: Int,
    val testTarget1: T?,
    val testTarget2: T?,
    val puzzleTarget1: T?,
    val puzzleTarget2: T?,
) {
    abstract fun part1(input: List<String>): T
    abstract fun part2(input: List<String>): T

    @Test
    @Order(1)
    @DisplayName("Part 1 - test data")
    fun part1test() {
        Assumptions.assumeTrue(testTarget1 != null)

        aocTest(
            year = year,
            day = day,
            part = 1,
            logic = ::part1,
            target = testTarget1,
            useTestInput = true,
        )
    }

    @Test
    @Order(2)
    @DisplayName("Part 1 - input data")
    fun part1() {
        Assumptions.assumeTrue(testTarget1 != null)

        aocTest(
            year = year,
            day = day,
            part = 1,
            logic = ::part1,
            target = puzzleTarget1,
            useTestInput = false,
        )
    }

    @Test
    @Order(3)
    @DisplayName("Part 2 - test data")
    fun part2test() {
        Assumptions.assumeTrue(testTarget2 != null)

        aocTest(
            year = year,
            day = day,
            part = 2,
            logic = ::part2,
            target = testTarget2,
            useTestInput = true,
        )
    }

    @Test
    @Order(4)
    @DisplayName("Part 2 - input data")
    fun part2() {
        Assumptions.assumeTrue(testTarget2 != null)

        aocTest(
            year = year,
            day = day,
            part = 2,
            logic = ::part2,
            target = puzzleTarget2,
            useTestInput = false,
        )
    }
}
