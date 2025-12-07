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

fun List<String>.splitColumns(): List<List<String>> {
    val minSize = minOf { it.length } - 2

    val splits = (0..minSize).filter { n -> all { it[n] == ' ' } }

    val columns = (0..<splits.size).map { mutableListOf<String>() }.toMutableList()

    splits.forEachIndexed { index, split ->
        val start = if(index == 0) 0 else splits[index - 1] + 1

        forEach {
            columns[index].add(it.substring(start, split))
        }
    }

   val maxLast = maxOf { it.substring(splits.last() + 1).length }

    columns += map {
        it.substring(splits.last() + 1).padEnd(maxLast, ' ')
    }.toMutableList()

    return columns
}

val intLineRegex = """-?\d+""".toRegex()

fun String.ints() = intLineRegex.findAll(this).map { it.value.toInt() }.toList()
fun String.longs() = intLineRegex.findAll(this).map { it.value.toLong() }.toList()

fun String.destructured(regex: Regex) = regex.matchEntire(this)
    ?.destructured
    ?: throw IllegalArgumentException("Incorrect line $this")
