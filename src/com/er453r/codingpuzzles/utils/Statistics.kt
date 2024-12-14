package com.er453r.codingpuzzles.utils

fun Iterable<Int>.average(): Double {
    if (this.none()) throw IllegalArgumentException("Cannot calculate average of an empty Iterable")
    
    return this.sum().toDouble() / this.count()
}

fun Iterable<Int>.variance(): Double {
    if (this.none()) throw IllegalArgumentException("Cannot calculate variance of an empty Iterable")

    val mean = this.average()
    return this.sumOf { (it - mean) * (it - mean) } / this.count()
}
