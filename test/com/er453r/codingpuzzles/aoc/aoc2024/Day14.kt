package com.er453r.codingpuzzles.aoc.aoc2024

import com.er453r.codingpuzzles.aoc.AoCTestBase
import com.er453r.codingpuzzles.utils.Vector2d
import com.er453r.codingpuzzles.utils.ints
import com.er453r.codingpuzzles.utils.lcm
import org.junit.jupiter.api.DisplayName


@DisplayName("AoC 2024 - Day 14")
class Day14 : AoCTestBase<Int>(
    year = 2024,
    day = 14,
    testTarget1 = 12,
    puzzleTarget1 = 221655456,
    testTarget2 = 100000,
    puzzleTarget2 = 7858,
) {
    fun print(positions: Set<Vector2d>, w: Int, h: Int, t: Int) {
        println("   ")
        println("   $t")
        println("   ")

        (0..<h).forEach { y ->
            val line = (0..<w).map { x ->
                if (positions.contains(Vector2d(x, y)))
                    'X'
                else
                    '.'
            }.joinToString("")

            println(line)
        }

        println("   ")
        println("   ")
    }

    override fun part1(input: List<String>): Int {
        val test = input.size < 20
        val t = 100
        val h = if (test) 7 else 103
        val w = if (test) 11 else 101

        val robots = input.map { it.ints() }.map { Pair(Vector2d(it[0], it[1]), Vector2d(it[2], it[3])) }

        val positions = robots.map { it.first + it.second * t }.map {
            Vector2d((it.x % w + w) % w, (it.y % h + h) % h)
        }

        val mh = (h - 1) / 2
        val mw = (w - 1) / 2

        val quad1 = positions.count { it.x < mw && it.y < mh }
        val quad2 = positions.count { it.x > mw && it.y < mh }
        val quad3 = positions.count { it.x < mw && it.y > mh }
        val quad4 = positions.count { it.x > mw && it.y > mh }

        print(positions.toSet(), w, h, t)

        return quad1 * quad2 * quad3 * quad4
    }

    fun cycle(pair: Pair<Vector2d, Vector2d>, w: Int, h: Int): Int {
        var t = 0

        while (true) {
            t++

            val position = pair.first + pair.second * t;
            val corrected = Vector2d((position.x % w + w) % w, (position.y % h + h) % h)

            if (corrected == pair.first)
                return t;
        }
    }

    override fun part2(input: List<String>): Int {
        val test = input.size < 20

        val h = if (test) 7 else 103
        val w = if (test) 11 else 101
        val mh = (h - 1) / 2
        val mw = (w - 1) / 2


        val r = 10
        val ms = 2 * r * h;

        val robots = input.map { it.ints() }.map { Pair(Vector2d(it[0], it[1]), Vector2d(it[2], it[3])) }
        val cycles = robots.map { cycle(it, w, h) }
        val maxCycles = cycles.reduce { acc, i -> acc.lcm(i) }

        for (t in 0 until maxCycles) {
            val positions = robots.map { it.first + it.second * t }.map {
                Vector2d((it.x % w + w) % w, (it.y % h + h) % h)
            }.toSet()

            val mc = positions.count { it.x < mw + r && it.x > mw - r }

            if (mc.toFloat() / ms < 0.1)
                continue

            val arrr = IntArray(w * h) { Math.random().toInt() }

            (0..<h).forEach { y ->
                val line = (0..<w).forEach() { x ->
                    if (positions.contains(Vector2d(x, y)))
                        arrr[x + y * w] = 255
                    else
                        arrr[x + y * w] = 0
                }
            }
        }

        return maxCycles
    }

//    override fun part2(input: List<String>): Int {
//        val test = input.size < 20
//        val h = if (test) 7 else 103
//        val w = if (test) 11 else 101
//
//        if (test)
//            return 0
//
//        val robots = input.map { it.ints() }.map { Pair(Vector2d(it[0], it[1]), Vector2d(it[2], it[3])) }
//
//        val orit = robots.toString()
//
//        val spot = mutableSetOf<Vector2d>()
//
//        val mh = (h - 1) / 2
//        val mw = (w - 1) / 2
//        val r = 10
//        (0..<r).forEach { dx ->
//            (0..<r).forEach { dy ->
//                spot.add(Vector2d(w - dx, h - dy))
//            }
//        }
//
//        val middle = mutableSetOf<Vector2d>()
//
//        (-r..<r).forEach { dx ->
//            (-r..<r).forEach { dy ->
//                middle.add(Vector2d(mw + dx, mh + dy))
//            }
//        }
//
//        val initial = robots.map { it.first }
//
//        var candidates = 0
//
//        for (t in 5927 until 10403) {
//            val positionsList = robots.map { it.first + it.second * t }.map {
//                Vector2d((it.x % w + w) % w, (it.y % h + h) % h)
//            }
//
//            val positions = positionsList.toSet()
//
//            if(positionsList.containsAll(initial))
//                println("start SET positions!!!!!!! $t")
//
//            if(positionsList.toString().equals(orit))
//                println("start positions!!!!!!! $t")
//
//            if(positions.intersect(spot).isNotEmpty())
//                continue
//
//            val left = positions.count { it.x < mw }
//            val right = positions.count { it.x > mw }
//
////            print(positions, w, h, t)
//
//            candidates++
//        }
//
//        println("candidates: $candidates")
//
//        return 0
//    }
}
