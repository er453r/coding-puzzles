package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2024 - Day 22")
class Day22 : AoCTestBase<Long>(
    year = 2024,
    day = 22,
    testTarget1 = 37990510,
    puzzleTarget1 = 14691757043,
    testTarget2 = 23,
    puzzleTarget2 = 1831,
) {
    private fun secretProcess(previousSecret: Long): Long {
        var secret = previousSecret
        // first
        val mul = secret * 64
        secret = mul.xor(secret) // mix
        secret %= 16777216 // prune

        // second
        val div = secret / 32 // round error?
        secret = div.xor(secret) // mix
        secret %= 16777216 // prune

        // third
        val mul2 = secret * 2048
        secret = mul2.xor(secret) // mix
        secret %= 16777216 // prune

        return secret
    }

    override fun part1(input: List<String>): Long {
        return input.map { it.toLong() }.sumOf {
            var secret = it

            repeat(2000) {
                secret = secretProcess(secret)
            }

            secret
        }
    }

    override fun part2(input: List<String>): Long {
        val map = mutableMapOf<List<Long>, Long>()

        input.map { it.toLong() }.forEach { initialSecret ->
            val secrets = mutableListOf(initialSecret)
            var secret = initialSecret

            repeat(2000) {
                secret = secretProcess(secret)
                secrets += secret
            }

            val prices = secrets.map { it % 10 }
            val dp = prices.windowed(2, 1).map { (a, b) -> b - a }

            val sellerMap = mutableMapOf<List<Long>, Long>()

            dp.forEachIndexed { index, _ ->
                if (index > 2) {
                    val list = dp.subList(index - 3, index + 1)
                    val price = prices[index + 1]

                    if (list !in sellerMap)
                        sellerMap[list] = price
                }
            }

            sellerMap.forEach { (list, price) ->
                map[list] = map.getOrDefault(list, 0) + price
            }
        }

        val max = map.values.max()

        map.forEach { (list, price) ->
            if (price == max)
                println("max $max for $list")
        }

        return map.values.max()
    }
}
