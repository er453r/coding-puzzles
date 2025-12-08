package com.er453r.codingpuzzles.utils

import kotlin.math.abs
import kotlin.math.sqrt

data class Vector3d(val x: Int = 0, val y: Int = 0, val z: Int = 0) {
    operator fun plus(vector3d: Vector3d) = Vector3d(x + vector3d.x, y + vector3d.y, z + vector3d.z)
    operator fun minus(vector3d: Vector3d) = Vector3d(x - vector3d.x, y - vector3d.y, z - vector3d.z)
    operator fun times(times: Int) = Vector3d(x * times, y * times, z * times)

    fun normalized() = Vector3d(if (x != 0) x / abs(x) else 0, if (y != 0) y / abs(y) else 0, if (z != 0) z / abs(z) else 0)

    fun length() = sqrt(length2().toDouble())
    fun length2() = x.toLong() * x.toLong() + y.toLong() * y.toLong() + z.toLong() * z.toLong()
}
