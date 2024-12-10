package com.er453r.codingpuzzles.utils

import kotlin.math.abs
import kotlin.math.max

data class GridCell<T>(
    var value: T,
    val position: Vector2d,
    val grid: Grid<T>,
){
    fun neighbours() = grid.getAll(position.neighboursCross())
}

class Grid<T>(data: List<List<T>>) {
    val data = data.mapIndexed { y, line ->
        line.mapIndexed { x, value -> GridCell(value, Vector2d(x, y), this) }
    }

    val width = data.first().size
    val height = data.size

    fun get(x: Int, y: Int) = data[y][x]
    operator fun get(vector2d: Vector2d) = get(vector2d.x, vector2d.y)

    fun contains(x: Int, y: Int) = (x in 0 until width) && (y in 0 until height)

    operator fun contains(vector2d: Vector2d) = contains(vector2d.x, vector2d.y)

    fun getAll(vectors: Set<Vector2d>) = vectors.filter { contains(it) }.map { get(it) }

    fun row(n:Int) = data[n]
    fun column(n:Int) = (0 until height).map { data[it][n] }

    fun rows() = data
    fun columns() = (0 until width).map { column(it) }
}

fun <T> Grid<T>.print(convert:(Vector2d)->Char) {
    println()
    this.data.forEach { row ->
        println(row.joinToString("") { convert(it.position).toString() })
    }
    println()
}

data class Vector2dl(var x: Long = 0, var y: Long = 0) {
    companion object {
        val UP = Vector2dl(0, -1)
        val DOWN = Vector2dl(0, 1)
        val LEFT = Vector2dl(-1, -0)
        val RIGHT = Vector2dl(1, 0)
        val DIRECTIONS = setOf(UP, DOWN, LEFT, RIGHT)
    }

    operator fun plus(vector2d: Vector2dl) = Vector2dl(x + vector2d.x, y + vector2d.y)
    operator fun minus(vector2d: Vector2dl) = Vector2dl(x - vector2d.x, y - vector2d.y)

    operator fun times(scale:Long) = Vector2dl(x * scale, y * scale)

    fun increment(vector2d: Vector2dl): Vector2dl {
        this.x += vector2d.x
        this.y += vector2d.y

        return this
    }
    fun length() = max(abs(x), abs(y))

}

data class Vector2d(val x: Int = 0, val y: Int = 0) {
    companion object {
        val UP = Vector2d(0, -1)
        val DOWN = Vector2d(0, 1)
        val LEFT = Vector2d(-1, -0)
        val RIGHT = Vector2d(1, 0)
        val DIRECTIONS = setOf(UP, DOWN, LEFT, RIGHT)
        val DIRECTIONS_ALL = setOf(UP, UP + RIGHT, RIGHT, RIGHT + DOWN, DOWN, DOWN + LEFT, LEFT, LEFT + UP)
        val DIRECTIONS_X = setOf(UP + RIGHT, RIGHT + DOWN, DOWN + LEFT, LEFT + UP)
    }

    operator fun plus(vector2d: Vector2d) = Vector2d(x + vector2d.x, y + vector2d.y)
    operator fun minus(vector2d: Vector2d) = Vector2d(x - vector2d.x, y - vector2d.y)

    operator fun times(scale:Int) = Vector2d(x * scale, y * scale)



    fun normalized() = Vector2d(if (x != 0) x / abs(x) else 0, if (y != 0) y / abs(y) else 0)

    fun negative() = Vector2d(-x, -y)

    fun length() = max(abs(x), abs(y))

    fun manhattan() = abs(x) + abs(y)

    fun neighbours8() = setOf(
        this + UP,
        this + UP + LEFT,
        this + UP + RIGHT,
        this + LEFT,
        this + RIGHT,
        this + DOWN,
        this + DOWN + LEFT,
        this + DOWN + RIGHT,
    )

    fun neighboursTopBottom() = setOf(
        this + UP,
        this + UP + LEFT,
        this + UP + RIGHT,
        this + DOWN,
        this + DOWN + LEFT,
        this + DOWN + RIGHT,
    )

    fun neighboursCross() = setOf(
        this + UP,
        this + DOWN,
        this + LEFT,
        this + RIGHT,
    )
}
