package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Impossible
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 20")
class Day20 : AoCTestBase<Long>(
    year = 2023,
    day = 20,
    testTarget1 = 11687500,
    puzzleTarget1 = 832957356,
    testTarget2 = 1,
    puzzleTarget2 = null,
) {
    data class Gate(val name:String, val type:Char, val outputs:List<String>, val memory:MutableMap<String, Boolean> = mutableMapOf())
    data class Pulse(val from:String, val to:String, val value:Boolean)

    override fun part1(input: List<String>): Long {
        val gates = input.associate { line ->
            val (inputString, outputString ) = line.split(" -> ")

            val type = inputString.first()
            val name = if (type != 'b') inputString.drop(1) else inputString
            val outputs = outputString.split(", ")

            name to Gate(name, type, outputs)
        }

        gates.values.forEach { gate ->
            gate.outputs.forEach { output ->
//                if(!gates.containsKey(output))
//                    println("lol $output")

                if(gates.containsKey(output)){
                    if(gates[output]!!.type == '&')
                        gates[output]!!.memory[gate.name] = false
                    else
                        gates[output]!!.memory[""] = false
                }
            }
        }

        var lowPulses = 0L
        var highPulses = 0L

        for(n in 1 .. 1000){
            val stack  = mutableListOf(Pulse("button", "broadcaster", false))

            while(stack.isNotEmpty()){
                val pulse = stack.removeFirst()

//                println("${pulse.from} -${if (pulse.value) "high" else "low"}-> ${pulse.to}")

                lowPulses += if(!pulse.value) 1 else 0
                highPulses += if(pulse.value) 1 else 0

                if(!gates.containsKey(pulse.to)) {
//                    println("lolz ${pulse.to}")
                    continue
                }

                val gate = gates[pulse.to]!!

                when(gate.type){
                    'b' -> gate.outputs.forEach {
                        stack.add(Pulse(pulse.to, it, pulse.value))
                    }
                    '%' -> if(!pulse.value){
                        gate.memory[""] = !gate.memory[""]!!

                        gate.outputs.forEach {
                            stack.add(Pulse(pulse.to, it, gate.memory[""]!!))
                        }
                    }
                    '&' -> {
                        gate.memory[pulse.from] = pulse.value

                        val output =  gate.memory.values.count { it } == gate.memory.size

                        gate.outputs.forEach {
                            stack.add(Pulse(pulse.to, it, !output))
                        }
                    }
                    else -> throw Impossible()
                }
            }
        }

        println("lowPulses $lowPulses")
        println("highPulses $highPulses")

        return lowPulses * highPulses
    }

    override fun part2(input: List<String>): Long {
        val gates = input.associate { line ->
            val (inputString, outputString ) = line.split(" -> ")

            val type = inputString.first()
            val name = if (type != 'b') inputString.drop(1) else inputString
            val outputs = outputString.split(", ")

            name to Gate(name, type, outputs)
        }

        gates.values.forEach { gate ->
            gate.outputs.forEach { output ->
//                if(!gates.containsKey(output))
//                    println("lol $output")

                if(gates.containsKey(output)){
                    if(gates[output]!!.type == '&')
                        gates[output]!!.memory[gate.name] = false
                    else
                        gates[output]!!.memory[""] = false
                }
            }
        }



        for(n in 1 .. 10000000000){
            val stack  = mutableListOf(Pulse("button", "broadcaster", false))

            if(n % 10000000 == 0L)
                println(n)

            while(stack.isNotEmpty()){
                val pulse = stack.removeFirst()

//                println("${pulse.from} -${if (pulse.value) "high" else "low"}-> ${pulse.to}")




                if(!gates.containsKey(pulse.to)) {
                    if(!pulse.value)
                        return n

//                    println("lolz ${pulse.to}")
                    continue
                }

                val gate = gates[pulse.to]!!

                when(gate.type){
                    'b' -> gate.outputs.forEach {
                        stack.add(Pulse(pulse.to, it, pulse.value))
                    }
                    '%' -> if(!pulse.value){
                        gate.memory[""] = !gate.memory[""]!!

                        gate.outputs.forEach {
                            stack.add(Pulse(pulse.to, it, gate.memory[""]!!))
                        }
                    }
                    '&' -> {
                        gate.memory[pulse.from] = pulse.value

                        val output =  gate.memory.values.count { it } == gate.memory.size

                        gate.outputs.forEach {
                            stack.add(Pulse(pulse.to, it, !output))
                        }
                    }
                    else -> throw Impossible()
                }
            }
        }

        return -1
    }
}
