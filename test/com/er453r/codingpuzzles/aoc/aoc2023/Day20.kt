package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Impossible
import com.er453r.codingpuzzles.utils.lcm
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 20")
class Day20 : AoCTestBase<Long>(
    year = 2023,
    day = 20,
    testTarget1 = 11687500,
    puzzleTarget1 = 832957356,
    testTarget2 = 1,
    puzzleTarget2 = 240162699605221,
) {
    data class Gate(val name: String, val type: Char, val outputs: List<String>, val memory: MutableMap<String, Boolean> = mutableMapOf())
    data class Pulse(val from: String, val to: String, val value: Boolean)

    private fun pushButton(gates: Map<String, Gate>, onPulse: (Pulse) -> Unit) {
        val stack = mutableListOf(Pulse("button", "broadcaster", false))

        while (stack.isNotEmpty()) {
            val pulse = stack.removeFirst()

            onPulse(pulse)

            if (!gates.containsKey(pulse.to))
                continue

            val gate = gates[pulse.to]!!

            when (gate.type) {
                'b' -> gate.outputs.forEach {
                    stack.add(Pulse(pulse.to, it, pulse.value))
                }

                '%' -> if (!pulse.value) {
                    gate.memory[""] = !gate.memory[""]!!
                    gate.outputs.forEach { output ->
                        stack.add(Pulse(pulse.to, output, gate.memory[""]!!))
                    }
                }

                '&' -> {
                    gate.memory[pulse.from] = pulse.value
                    gate.outputs.forEach { output ->
                        stack.add(Pulse(pulse.to, output, gate.memory.values.count { it } != gate.memory.size))
                    }
                }

                else -> throw Impossible()
            }
        }
    }

    private fun parseGates(input: List<String>): Map<String, Gate> {
        val gates = input.associate { line ->
            val (inputString, outputString) = line.split(" -> ")

            val type = inputString.first()
            val name = if (type != 'b') inputString.drop(1) else inputString
            val outputs = outputString.split(", ")

            name to Gate(name, type, outputs)
        }

        gates.values.forEach { gate ->
            gate.outputs.forEach { output ->
                if (gates.containsKey(output)) {
                    if (gates[output]!!.type == '&')
                        gates[output]!!.memory[gate.name] = false
                    else
                        gates[output]!!.memory[""] = false
                }
            }
        }

        return gates
    }

    override fun part1(input: List<String>): Long {
        val gates = parseGates(input)

        var lowPulses = 0L
        var highPulses = 0L

        for (n in 1..1000) {
            pushButton(gates) {
                lowPulses += if (!it.value) 1 else 0
                highPulses += if (it.value) 1 else 0
            }
        }

        return lowPulses * highPulses
    }

    override fun part2(input: List<String>): Long {
        val gates = parseGates(input)

        val lastGate = gates.values.first { it.outputs.contains("rx") }
        val lastGateInputs = lastGate.memory.keys.toMutableList()
        val cycles = mutableListOf<Long>()
        var pushes = 1L

        while (lastGateInputs.isNotEmpty()) {
            pushButton(gates) { pulse ->
                lastGateInputs.filter { it == pulse.from && pulse.value }.forEach {
                    cycles += pushes
                    lastGateInputs.remove(it)
                }
            }

            pushes++
        }

        return cycles.reduce { a, b -> a.lcm(b) }
    }
}
