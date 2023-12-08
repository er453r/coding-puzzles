package com.er453r.codingpuzzles.aoc.aoc2023

import com.er453r.codingpuzzles.aoc.AoCTestBase
import org.junit.jupiter.api.DisplayName

@DisplayName("AoC 2023 - Day 07")
class Day07 : AoCTestBase<Long>(
    year = 2023,
    day = 7,
    testTarget1 = 6440,
    puzzleTarget1 = 250120186,
    testTarget2 = 5905,
    puzzleTarget2 = 250665248,
) {
    private val cardOrderPart1 = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2".split(", ")
    private val cardOrderPart2 = "A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J".split(", ")
    private val types = "5oK, 4oK, FH, 3oK, 2p, 1p, HC".split(", ")

    data class Hand(
        val handCards: List<String>,
        val typeIndex: String,
        val rank: Int,
        val score: Long,
    )

    private fun score(handCards: List<String>, type: String, cardOrder: List<String>): Long {
        return ((cardOrder.indexOf(handCards[4]) + 1)
                + 100L * (cardOrder.indexOf(handCards[3]) + 1)
                + 10000L * (cardOrder.indexOf(handCards[2]) + 1)
                + 1000000L * (cardOrder.indexOf(handCards[1]) + 1)
                + 100000000L * (cardOrder.indexOf(handCards[0]) + 1)
                + 10000000000L * (types.indexOf(type) + 1))
    }

    private fun solve(input: List<String>, cardOrder: List<String>, typeLogic: (Map<String, Int>) -> String) = input.map { line ->
        val (hand, rankString) = line.split(" ")
        val handCards = hand.toCharArray().map { it.toString() }
        val cardMap = handCards.distinct().associateWith { handCards.count { card -> card == it } }
        val type = typeLogic(cardMap)

        Hand(handCards, type, rankString.toInt(), score(handCards, type, cardOrder))
    }
        .sortedBy { it.score }
        .reversed()
        .mapIndexed { index, hand ->
            (index + 1) * hand.rank.toLong()
        }.sum()

    override fun part1(input: List<String>) = solve(input, cardOrderPart1) { cardMap ->
        when {
            cardMap.containsValue(5) -> "5oK"
            cardMap.containsValue(4) -> "4oK"
            cardMap.containsValue(3) && cardMap.containsValue(2) -> "FH"
            cardMap.containsValue(3) -> "3oK"
            cardMap.values.count { it == 2 } == 2 -> "2p"
            cardMap.containsValue(2) -> "1p"
            else -> "HC"
        }
    }

    override fun part2(input: List<String>) = solve(input, cardOrderPart2) { cm ->
        val jokers = cm["J"] ?: 0
        val cardMap = cm.toMutableMap().also { it.remove("J") }

        when {
            cm.containsValue(5) -> "5oK"
            cardMap.containsValue(4) && jokers == 1 -> "5oK"
            cardMap.containsValue(3) && jokers == 2 -> "5oK"
            cardMap.containsValue(2) && jokers == 3 -> "5oK"
            cardMap.containsValue(1) && jokers == 4 -> "5oK"

            cm.containsValue(4) -> "4oK"
            cardMap.containsValue(3) && jokers == 1 -> "4oK"
            cardMap.containsValue(2) && jokers == 2 -> "4oK"
            cardMap.containsValue(1) && jokers == 3 -> "4oK"

            cardMap.containsValue(3) && cardMap.containsValue(2) -> "FH"
            cardMap.values.count { it == 2 } == 2 && jokers == 1 -> "FH"

            cm.containsValue(3) -> "3oK"
            cardMap.containsValue(2) && jokers == 1 -> "3oK"
            cardMap.containsValue(1) && jokers == 2 -> "3oK"

            cardMap.values.count { it == 2 } == 2 -> "2p"

            cardMap.containsValue(2) -> "1p"
            cardMap.containsValue(1) && jokers == 1 -> "1p"

            else -> "HC"
        }
    }
}
