package com.er453r.codingpuzzles.utils

import java.io.File

fun readInput(path: String) = File(path).readLines()

fun List<String>.split(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    var currentList = mutableListOf<String>()

    for (line in this)
        when {
            line.isBlank() && currentList.isEmpty() -> continue
            line.isBlank() -> {
                result.add(currentList)
                currentList = mutableListOf()
            }

            else -> currentList.add(line)
        }

    if (currentList.isNotEmpty())
        result.add(currentList)

    return result
}

val intLineRegex = """-?\d+""".toRegex()

fun String.ints() = intLineRegex.findAll(this).map { it.value.toInt() }.toList()

fun String.destructured(regex: Regex): MatchResult.Destructured = regex.matchEntire(this)
    ?.destructured
    ?: throw IllegalArgumentException("Incorrect line $this")