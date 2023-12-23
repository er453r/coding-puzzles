package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.aoc.aoc2023.Day22.Vector3d.Companion.DOWN
import com.er453r.codingpuzzles.utils.ints
import org.junit.jupiter.api.DisplayName
import kotlin.math.abs

@DisplayName("AoC 2023 - Day 22")
class Day22 : AoCTestBase<Int>(
    year = 2023,
    day = 22,
    testTarget1 = 5,
    puzzleTarget1 = 407,
    testTarget2 = 7,
    puzzleTarget2 = 59266,
) {
    data class Block(val id:Int, val cubes: Set<Vector3d>)

    data class Vector3d(val x: Int = 0, val y: Int = 0, val z: Int = 0) {
        companion object {
            val DOWN = Vector3d(0, 0, -1)
        }

        operator fun plus(vector3d: Vector3d) = Vector3d(x + vector3d.x, y + vector3d.y, z + vector3d.z)
        operator fun minus(vector3d: Vector3d) = Vector3d(x - vector3d.x, y - vector3d.y, z - vector3d.z)
        operator fun times(times: Int) = Vector3d(x * times, y * times, z * times)

        fun normalized() = Vector3d(if (x != 0) x / abs(x) else 0, if (y != 0) y / abs(y) else 0, if (z != 0) z / abs(z) else 0)
    }

    private fun fall(blocks: MutableSet<Block>) {
        while (hasSomethingFallen(blocks)) {
            //
        }
    }

    private fun hasSomethingFallen(blocks: MutableSet<Block>): Boolean {
        var somethingHasFallen = false
        var cubes = blocks.flatMap { it.cubes }.toSet()

        blocks.toList().forEach { block ->
            val below = block.cubes.map { it + DOWN }.toSet() - block.cubes

            if (below.intersect(cubes).isEmpty() && below.all { it.z > 0 }) {
                somethingHasFallen = true

                blocks -= block
                blocks += Block(block.id, block.cubes.map { it + DOWN }.toSet())
                cubes = blocks.flatMap { it.cubes }.toSet()
            }
        }

        return somethingHasFallen
    }

    private fun parseBlocks(input: List<String>) = input.mapIndexed { index, line ->
        val ints = line.ints()

        val start = Vector3d(ints[0], ints[1], ints[2])
        val end = Vector3d(ints[3], ints[4], ints[5])
        val dir = (end - start).normalized()

        val cubes = mutableSetOf(start, end)

        var cube = start + dir

        while (cube != end) {
            cubes += cube
            cube += dir
        }

        Block(index, cubes.toSet())
    }.toSet()

    override fun part1(input: List<String>): Int {
        val blocks = parseBlocks(input).toMutableSet()

        fall(blocks)

        return blocks.count { !hasSomethingFallen((blocks - it).toMutableSet()) }
    }

    override fun part2(input: List<String>): Int {
        val blocks = parseBlocks(input).toMutableSet()

        fall(blocks)

        return blocks.sumOf { block ->
            val reduced = blocks - block
            val falling = reduced.toMutableSet()

            fall(falling)

            reduced.size - reduced.intersect(falling).size
        }
    }
}
