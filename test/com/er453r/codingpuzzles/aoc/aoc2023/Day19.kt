package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Impossible
import com.er453r.codingpuzzles.utils.destructured
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.split
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 19")
class Day19 : AoCTestBase<Long>(
    year = 2023,
    day = 19,
    testTarget1 = 19114,
    puzzleTarget1 = 353553,
    testTarget2 = 167409079868000,
    puzzleTarget2 = null,
) {
    data class Logic(val property:String, val operator:String, val value:Int, val target:String)
    data class Workflow(val logic:List<Logic>, val target:String)

    override fun part1(input: List<String>): Long {
        val (workflowLines, partLines) = input.split()

        val parts = partLines.map { it.ints() }.map { mapOf(
            "x" to it[0],
            "m" to it[1],
            "a" to it[2],
            "s" to it[3],
        ) }

        val workflows = workflowLines.associate {
            val (name, stepsParts) = it.split("{")

            val steps = stepsParts.dropLast(1).split(",")

            val workflowTarget = steps.last()
            val logic = steps.dropLast(1).map {
                val (property, operator, value, target,) = it.destructured("(.)(.)(\\d+):(.+)".toRegex())

                Logic(property, operator, value.toInt(), target)
            }

            name to Workflow(logic, workflowTarget)
        }

        val accepted = mutableListOf<Map<String,Int>>()

        for(part in parts){
            var target = "in"

            partLoop@while(true){
                when(target){
                    "A" -> {accepted += part; break}
                    "R" -> break
                    else -> {
                        val workflow = workflows[target]!!

                        for(logic in workflow.logic){
                            val propertyValue = part[logic.property]!!

                            val pass = when(logic.operator){
                                "<" -> propertyValue < logic.value
                                ">" -> propertyValue > logic.value
                                else -> throw Impossible()
                            }

                            if(pass) {
                                target = logic.target

                                continue@partLoop
                            }
                        }

                        target = workflow.target

                        continue@partLoop
                    }
                }
            }
        }

        return accepted.map { it.values.sum() }.sum().toLong()
    }

    override fun part2(input: List<String>): Long {
        val (workflowLines, _) = input.split()

        val workflows = workflowLines.associate {
            val (name, stepsParts) = it.split("{")

            val steps = stepsParts.dropLast(1).split(",")

            val workflowTarget = steps.last()
            val logic = steps.dropLast(1).map {
                val (property, operator, value, target,) = it.destructured("(.)(.)(\\d+):(.+)".toRegex())

                Logic(property, operator, value.toInt(), target)
            }

            name to Workflow(logic, workflowTarget)
        }

        val accepted = mutableListOf<Map<String,IntRange>>()

        val stack = mutableListOf(mapOf(
            "x" to (1 .. 4000),
            "m" to (1 .. 4000),
            "a" to (1 .. 4000),
            "s" to (1 .. 4000),
        ))

        stackLoop@while(stack.isNotEmpty()){
            val part = stack.removeLast()

            var target = "in"

            partLoop@while(true){
                when(target){
                    "A" -> {accepted += part; break}
                    "R" -> break
                    else -> {
                        val workflow = workflows[target]!!

                        for(logic in workflow.logic){
                            val propertyValue = part[logic.property]!!

                            if(logic.value in propertyValue && propertyValue.first != propertyValue.last){
                                val newPart1 = part.toMutableMap()
                                newPart1[logic.property] = IntRange(propertyValue.first, logic.value - 1)
                                stack += newPart1

                                val newPart2 = part.toMutableMap()
                                newPart2[logic.property] = IntRange(logic.value + 1, propertyValue.last)
                                stack += newPart2

                                val newPart3 = part.toMutableMap()
                                newPart3[logic.property] = IntRange(logic.value, logic.value,)
                                stack += newPart3

                                continue@stackLoop
                            }

                            val pass = when(logic.operator){
                                "<" -> propertyValue.last < logic.value
                                ">" -> propertyValue.first > logic.value
                                else -> throw Impossible()
                            }

                            if(pass) {
                                target = logic.target

                                continue@partLoop
                            }
                        }

                        target = workflow.target

                        continue@partLoop
                    }
                }
            }
        }

        return accepted.map { it.values.map { it.last.toLong() - it.first + 1 }.reduce(Long::times) }.sum()
    }
}
