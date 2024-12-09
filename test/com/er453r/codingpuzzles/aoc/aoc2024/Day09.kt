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
    override fun part1(input: List<String>): Long {
        val dense = input.first().toCharArray().map { it.toString().toLong() }.toList()

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

        return sparse.mapIndexed { index, i -> if(i != -1 ) index.toLong() * i else 0 }.sum()
    }

    override fun part2(input: List<String>): Long {
        val dense = input.first().toCharArray().map { it.toString().toLong() }.toList()

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

        sparse.indices.reversed().forEach { index ->
            val value = sparse[index]

            if (value != -1) {
                val blockSize = sparse.count { it == value }
                val blockStart = sparse.indexOfFirst { it == value }

                val emptyList = mutableListOf<Pair<Int, Int>>()
                var emptyBlock:Pair<Int, Int>? = null

                (0..<index).forEach { i ->
                    if(sparse[i] == -1) {
                        if(emptyBlock == null) {
                            emptyBlock = Pair(i, 1)
                        }
                        else
                            emptyBlock = Pair(emptyBlock!!.first, emptyBlock!!.second+1)
                    }
                    else{
                        emptyBlock?.let { emptyList.add(it) }
                        emptyBlock = null
                    }
                }

                val firstEmptyIndex = emptyList.filter { it.second >= blockSize }.minByOrNull { it.first }

                if (firstEmptyIndex != null && firstEmptyIndex.first < blockStart) {
                    (firstEmptyIndex.first..< firstEmptyIndex.first + blockSize).forEach { i -> sparse[i] = value}
                    (blockStart..< blockStart + blockSize).forEach { i -> sparse[i] = -1}
                }
            }
        }

//        println(sparse.map { if(it == -1) "." else it.toString() }.joinToString(""))

        return sparse.mapIndexed { index, i -> if(i != -1 ) index.toLong() * i else 0 }.sum()
    }
}
