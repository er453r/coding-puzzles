package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.destructured
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 08")
class Day08 : AoCTestBase<Long>(
    year = 2023,
    day = 8,
    testTarget1 = 6,
    puzzleTarget1 = 11911,
    testTarget2 = 6,
    puzzleTarget2 = null,
) {
    override fun part1(input: List<String>): Long {
        val directions = input.first().toCharArray().map { it.toString() }.map { if(it == "L") 0 else 1 }

        val map  = input.drop(2).associate { line ->
            val (node, left, right) = line.destructured("([A-Z]+) = \\(([A-Z]+), ([A-Z]+)\\)".toRegex())

            node to listOf(left, right)
        }

        var node = "AAA"
        var steps = 0L

        while(node != "ZZZ"){
            directions.forEach { dir ->
                node = map[node]!![dir]

                steps++
            }
        }

        return steps
    }

    fun checkNode(startNode:String, directions:List<Int>, map:Map<String, List<String>>):Pair<String, Long>{
        var node = startNode
        var steps = 0L

        while(!node.endsWith("Z")){
            directions.forEach { dir ->
                node = map[node]!![dir]

                steps++
            }
        }

        return Pair(node, steps)
    }

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    override fun part2(input: List<String>): Long {
        val directions = input.first().toCharArray().map { it.toString() }.map { if(it == "L") 0 else 1 }

        val map  = input.drop(2).associate { line ->
            val (node, left, right) = line.destructured("([A-Z0-9]+) = \\(([A-Z0-9]+), ([A-Z0-9]+)\\)".toRegex())

            node to listOf(left, right)
        }

        val nodes = map.keys.filter { it.endsWith("A") }.toMutableList()
        var steps = 0L

        steps = nodes.map {
            val check  = checkNode(it, directions, map)
            println("$it -> $check")

            check.second
        }.reduce { a, b -> findLCM(a, b) }

//        while(nodes.count { it.endsWith("Z") } != nodes.size){
//            directions.forEach { dir ->
//               nodes.forEachIndexed { index, node ->
//                   nodes[index] = map[node]!![dir]
//               }
//
//                if(nodes.count { it.endsWith("Z") } == nodes.size)
//                    println("DERP")
//
//                steps++
//            }
//        }

        return steps
    }
}
