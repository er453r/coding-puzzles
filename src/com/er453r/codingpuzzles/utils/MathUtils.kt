package com.er453r.codingpuzzles.utils

import java.math.BigInteger

fun Int.pow(exp: Int) = BigInteger.valueOf(this.toLong()).pow(exp).toLong()

fun LongRange.intersect(other: LongRange): LongRange? = if (this.first <= other.last && other.first <= this.last)
    maxOf(this.first, other.first).rangeTo(minOf(this.last, other.last))
else
    null

fun Long.lcm(b: Long): Long {
    val larger = if (this > b) this else b
    val maxLcm = this * b
    var lcm = larger

    while (lcm <= maxLcm) {
        if (lcm % this == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }

    return maxLcm
}

fun Int.lcm(b: Int): Int {
    val larger = if (this > b) this else b
    val maxLcm = this * b
    var lcm = larger

    while (lcm <= maxLcm) {
        if (lcm % this == 0 && lcm % b == 0) {
            return lcm
        }
        lcm += larger
    }

    return maxLcm
}
