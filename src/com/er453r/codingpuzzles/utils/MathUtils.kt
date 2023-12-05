package com.er453r.codingpuzzles.utils

import java.math.BigInteger

fun Int.pow(exp: Int) = BigInteger.valueOf(this.toLong()).pow(exp).toLong()

fun LongRange.intersect(other: LongRange): LongRange? = if (this.first <= other.last && other.first <= this.last)
    maxOf(this.first, other.first).rangeTo(minOf(this.last, other.last))
else
    null
