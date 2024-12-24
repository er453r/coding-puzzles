package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.pow
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 24")
class Day24 : AoCTestBase<String>(
    year = 2024,
    day = 24,
    testTarget1 = "2024",
    puzzleTarget1 = "46362252142374",
    testTarget2 = "ffh,hwm,kjc,mjb,ntg,rvg,tgd,wpb,z02,z03,z05,z06,z07,z08,z10,z11",
    puzzleTarget2 = "cbd,gmh,jmq,qrh,rqf,z06,z13,z38",
) {
    data class Gate(val a: String, val op: String, val b: String, val o: String)

    private fun calculate(initialValues: Map<String, Boolean>, initialGates: Set<Gate>): Long {
        val values = initialValues.toMutableMap()
        val gates = initialGates.toMutableSet()

        while (gates.isNotEmpty()) {
            val processed = mutableSetOf<Gate>()

            gates.forEach { gate ->
                if (values.containsKey(gate.a) && values.contains(gate.b)) {
                    values[gate.o] = when (gate.op) {
                        "AND" -> values[gate.a]!! && values[gate.b]!!
                        "OR" -> values[gate.a]!! || values[gate.b]!!
                        "XOR" -> values[gate.a]!!.xor(values[gate.b]!!)
                        else -> throw Exception("Unknown operation: ${gate.op}")
                    }
                    processed.add(gate)
                }
            }

            gates.removeAll(processed)
        }

        return values
            .asSequence()
            .filter { it.key.startsWith("z") }
            .toList()
            .sortedBy { it.key }
            .map { it.value }
            .mapIndexed { index, value -> if (value) 2.pow(index) else 0 }
            .sum()
    }

    private fun parse(input: List<String>): Pair<Map<String, Boolean>, Map<String, Gate>> {
        val values = input.filter { it.contains(":") }.map { it.split(": ") }
            .associate { (name, value) -> name to (value == "1") }

        val gates = input.filter { it.contains("->") }.map { it.split(" ") }
            .associate { (a, op, b, _, o) -> o to Gate(a, op, b, o) }

        return Pair(values, gates)
    }

    override fun part1(input: List<String>): String {
        val (values, gates) = parse(input)

        return calculate(values, gates.values.toSet()).toString()
    }



    override fun part2(input: List<String>): String {
        val (_, gates) = parse(input)

        // logic of adder is
        // x[i]          := input
        // y[i]          := input
        // gate_and[i]   := x[i] AND y[i]
        // gate_xor[i]   := x[i] XOR y[i]
        // gate_z[i]     := gate_xor[i] XOR gate_carry[i-1]
        // gate_tmp[i]   := gate_xor[i] AND gate_carry[i-1]
        // gate_carry[i] := gate_tmp[i] OR gate_and[i]
        val highestZ = gates.values.filter { it.o.startsWith("z") }.map { it.o }.maxOf { it }
        val wrong = mutableSetOf<String>()
        val xyz = setOf('x', 'y', 'z')

        gates.values.forEach { gate ->
            if(gate.o.startsWith("z") && gate.op != "XOR" && gate.o != highestZ)
                wrong += gate.o

            if(gate.op == "XOR" && gate.a[0] !in xyz && gate.b[0] !in xyz && gate.o[0] !in xyz)
                wrong += gate.o

            if(gate.op == "AND" && (gate.a != "x00" && gate.b != "x00"))
                for(subgate in gates.values)
                    if((gate.o == subgate.a || gate.o == subgate.b) && subgate.op != "OR")
                        wrong += gate.o

            if(gate.op == "XOR")
                for(subgate in gates.values)
                    if((gate.o == subgate.a || gate.o == subgate.b) && subgate.op == "OR")
                        wrong += gate.o
        }

        return wrong.toList().sorted().joinToString(",")
    }
}
