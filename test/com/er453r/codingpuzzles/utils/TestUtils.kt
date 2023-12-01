package com.er453r.codingpuzzles.utils

import java.io.File

fun <T> assertEquals(value: T, target: T) {
    if (value != target)
        check(false) { "Expected $target got $value" }
}

fun <T> aocTest(
    year: Int,
    day: Int,
    part: Int,
    logic: (List<String>) -> T,
    target: T?,
    useTestInput: Boolean,
) {
    val dayNumber = day.toString().padStart(2, '0')
    val inputFile = "Day${dayNumber}${if (useTestInput) "_test" else ""}"
    val input = if (part == 2 && useTestInput && File("test/com/er453r/codingpuzzles/aoc/aoc$year/${inputFile}2.txt").exists())
        readInput("test/com/er453r/codingpuzzles/aoc/aoc$year/${inputFile}2.txt")
    else
        readInput("test/com/er453r/codingpuzzles/aoc/aoc$year/$inputFile.txt")

    println("[DAY $day]")

    test(
        input = input,
        logic = logic,
        target = target,
    )
}

private fun <T> test(
    input: List<String>,
    logic: (List<String>) -> T,
    target: T?,
) {
    println("target:    $target")

    val startTime = System.currentTimeMillis()
    val result = logic(input)
    val elapsedTime = System.currentTimeMillis() - startTime

    println("result:    $result")
    println("elapsed time ($elapsedTime ms)")

    if (target != null)
        assertEquals(result, target)
    else
        check(false) { "No test target" }
}
