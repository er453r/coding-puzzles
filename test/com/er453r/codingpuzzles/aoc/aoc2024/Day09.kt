package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 09")
class Day09 : AoCTestBase<Long>(
    year = 2024,
    day = 9,
    testTarget1 = 1928,
    puzzleTarget1 = 6366665108136,
    testTarget2 = 2858,
    puzzleTarget2 = 6398065450842,
) {
    data class Block(var position: Int, var size: Int)

    private fun sparsify(dense: List<Int>): MutableList<Int> {
        var id = 0
        var isFileBlock = true
        val sparse = mutableListOf<Int>()

        dense.forEach { blockSize ->
            (0..<blockSize).forEach { _ ->
                sparse.add(if (isFileBlock) id else -1)
            }

            if (isFileBlock)
                id++

            isFileBlock = !isFileBlock
        }

        return sparse
    }

    override fun part1(input: List<String>): Long {
        val sparse = sparsify(input.first().toCharArray().map { it.toString().toInt() }.toList())

        val emptyBlocks = sparse.indices.filter { sparse[it] == -1 }.toMutableList()

        sparse.indices.reversed().forEach { index ->
            val value = sparse[index]

            if (value != -1) {
                val firstEmptyIndex = emptyBlocks.first()

                if (firstEmptyIndex < index) {
                    sparse[firstEmptyIndex] = value
                    sparse[index] = -1
                    emptyBlocks.removeFirst()
                }
            }
        }

        return sparse.mapIndexed { index, i -> if (i != -1) index.toLong() * i else 0 }.sum()
    }

    override fun part2(input: List<String>): Long {
        val sparse = sparsify(input.first().toCharArray().map { it.toString().toInt() }.toList())

        val emptyList = mutableListOf<Block>()
        var emptyBlock: Block? = null

        (0..<sparse.size).forEach { i ->
            if (sparse[i] == -1) {
                emptyBlock = if (emptyBlock == null) {
                    Block(i, 1)
                } else
                    Block(emptyBlock!!.position, emptyBlock!!.size + 1)
            } else {
                emptyBlock?.let { emptyList.add(it) }
                emptyBlock = null
            }
        }

        sparse.indices.reversed().forEach { index ->
            val value = sparse[index]

            if (value != -1) {
                val blockSize = sparse.count { it == value }
                val blockStart = sparse.indexOfFirst { it == value }

                val firstEmptyIndex = emptyList.firstOrNull { it.size >= blockSize }

                if (firstEmptyIndex != null && firstEmptyIndex.position < blockStart) {
                    (firstEmptyIndex.position..<firstEmptyIndex.position + blockSize).forEach { i -> sparse[i] = value }
                    (blockStart..<blockStart + blockSize).forEach { i -> sparse[i] = -1 }

                    if (firstEmptyIndex.size == blockSize)
                        emptyList.remove(firstEmptyIndex)
                    else {
                        firstEmptyIndex.position += blockSize
                        firstEmptyIndex.size -= blockSize
                    }
                }
            }
        }

        return sparse.mapIndexed { index, i -> if (i != -1) index.toLong() * i else 0 }.sum()
    }
}
