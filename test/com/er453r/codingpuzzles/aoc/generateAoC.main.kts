#!/usr/bin/env kotlin

import java.io.File
import kotlin.system.exitProcess

if (args.size != 2) {
    println("Usage: <YEAR> <DAYS>")
    exitProcess(0)
}

val year = args[0].toIntOrNull()
val days = args[1].toIntOrNull()

if (year == null || year < 2000) {
    println("Invalid year: '${args[0]}'")

    exitProcess(0)
}
if (days == null || days !in 1..25) {
    println("Invalid days: '${args[1]}' (expected 1..25)")

    exitProcess(0)
}

val baseDir = File("aoc$year")

println("Generating AOC $year scaffolding for $days day(s) in ${baseDir.absolutePath}...")

if (!baseDir.exists())
    baseDir.mkdirs()

for (d in 1..days!!) {
    val dayStr = d.toString().padStart(2, '0')

    val ktFile = File(baseDir, "Day${dayStr}.kt")
    val txtFile = File(baseDir, "Day${dayStr}.txt")
    val testTxtFile = File(baseDir, "Day${dayStr}_test.txt")

    if (!ktFile.exists()) {
        ktFile.writeText(dayKtTemplate(year!!, dayStr))
        println("Created: ${ktFile.path}")
    } else {
        println("Skipped (exists): ${ktFile.path}")
    }

    if (!txtFile.exists()) {
        txtFile.writeText("")
        println("Created: ${txtFile.path}")
    } else {
        println("Skipped (exists): ${txtFile.path}")
    }

    if (!testTxtFile.exists()) {
        testTxtFile.writeText("")
        println("Created: ${testTxtFile.path}")
    } else {
        println("Skipped (exists): ${testTxtFile.path}")
    }
}

println("Done. AOC $year scaffolding generated for $days day(s).")

fun dayKtTemplate(year: Int, dayStr: String): String = """
package com.er453r.codingpuzzles.aoc.aoc$year

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC $year - Day $dayStr")
class Day$dayStr : AoCTestBase<Int>(
    year = $year,
    day = ${dayStr.toInt()},
    testTarget1 = null,
    puzzleTarget1 = null,
    testTarget2 = null,
    puzzleTarget2 = null,
) {
    override fun part1(input: List<String>): Int {
        return 0
    }

    override fun part2(input: List<String>): Int {
        return 0
    }
}

""".trimMargin()
