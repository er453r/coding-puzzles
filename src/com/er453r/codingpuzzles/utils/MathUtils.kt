package com.er453r.codingpuzzles.utils

import java.math.BigInteger

fun Int.pow(exp: Int) = BigInteger.valueOf(this.toLong()).pow(exp).toLong()
