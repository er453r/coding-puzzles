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
    puzzleTarget2 = null,
) {
    private val cardOrder = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2".split(", ")
    private val cardOrder2 = "A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J".split(", ")
    private val types = "5oK, 4oK, FH, 3oK, 2p, 1p, HC".split(", ")

    data class Hand(
        val handCards: List<String>,
        val typeIndex: String,
        val rank: Int,
        val score: Long,
    )

    private fun score(handCards: List<String>, type: String): Long {
        return ((cardOrder.indexOf(handCards[4]) + 1)
                + 100L * (cardOrder.indexOf(handCards[3]) + 1)
                + 10000L * (cardOrder.indexOf(handCards[2]) + 1)
                + 1000000L * (cardOrder.indexOf(handCards[1]) + 1)
                + 100000000L * (cardOrder.indexOf(handCards[0]) + 1)
                + 10000000000L * (types.indexOf(type) + 1))
    }

    private fun score2(handCards: List<String>, type: String): Long {
        return ((cardOrder2.indexOf(handCards[4]) + 1)
                + 100L * (cardOrder2.indexOf(handCards[3]) + 1)
                + 10000L * (cardOrder2.indexOf(handCards[2]) + 1)
                + 1000000L * (cardOrder2.indexOf(handCards[1]) + 1)
                + 100000000L * (cardOrder2.indexOf(handCards[0]) + 1)
                + 10000000000L * (types.indexOf(type) + 1))
    }

    override fun part1(input: List<String>) =
        input.map { line ->
            val (hand, rankString) = line.split(" ")

            val handCards = hand.toCharArray().map { it.toString() }

            val cardMap = handCards.distinct().associateWith { handCards.count { card -> card == it } }

            val type = when {
                cardMap.containsValue(5) -> "5oK"
                cardMap.containsValue(4) -> "4oK"
                cardMap.containsValue(3) && cardMap.containsValue(2) -> "FH"
                cardMap.containsValue(3) -> "3oK"
                cardMap.values.count { it == 2 } == 2 -> "2p"
                cardMap.containsValue(2) -> "1p"
                else -> "HC"
            }

            Hand(handCards, type, rankString.toInt(), score(handCards, type))
        }
            .sortedBy { it.score }
            .reversed()
            .mapIndexed { index, hand ->
                println("${index + 1} $hand")
                (index + 1) * hand.rank.toLong()
            }.sum()

    private fun jokers(cardMap:Map<String, Int>):Int = cardMap["J"] ?: 0

    override fun part2(input: List<String>) =
        input.map { line ->
            val (hand, rankString) = line.split(" ")

            val handCards = hand.toCharArray().map { it.toString() }

            val cm = handCards.distinct().associateWith { handCards.count { card -> card == it } }
            val cardMap  = cm.toMutableMap()
            cardMap.remove("J")
            val jokers = jokers(cm)

            val type = when {
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

            if(type == "HC" && handCards.distinct().size != 5)
                println(handCards.sorted().joinToString("") )

            Hand(handCards, type, rankString.toInt(), score2(handCards, type))
        }
            .sortedBy { it.score }
            .reversed()
            .mapIndexed { index, hand ->
//                println("${index + 1} $hand")
                (index + 1) * hand.rank.toLong()
            }.sum() // 250665479 too high
                    // 250665248
}
