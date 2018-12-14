package day13

import day13.Direction.*
import java.io.File

fun main(args: Array<String>) {

    val lines = File("src/day13/input.txt").readLines()
    val carts = mutableListOf<Cart>()
    val track = lines.asSequence().withIndex().flatMap { y ->
        y.value.asSequence().withIndex().filterNot { it.value.isWhitespace() }.map { x ->
            with(Point(x.index, y.index)) {
                this to when (x.value) {
                    '^' -> '|'.also { carts += Cart(this, N) }
                    'v' -> '|'.also { carts += Cart(this, S) }
                    '<' -> '-'.also { carts += Cart(this, W) }
                    '>' -> '-'.also { carts += Cart(this, E) }
                    else -> x.value
                }
            }
        }
    }.toMap()

//    println(firstCollision(carts, track))
    println(lastSurvivor(carts, track))
}

 fun firstCollision(carts: MutableList<Cart>, track: Map<Point, Char>): Cart {

    while (true) {
        carts.sortWith(compareBy<Cart> { c -> c.xy.y }.thenBy { c -> c.xy.x })
        for (cart in carts) {
            cart.tick(track)
            if (carts.any { it !== cart && it.xy == cart.xy }) {
                return cart
            }
        }
    }
}

fun lastSurvivor(carts: MutableList<Cart>, track: Map<Point, Char>): Cart {

    while (carts.count { !it.crashed } > 1) {
        carts.removeIf{it.crashed}
        carts.sortWith(compareBy<Cart> { c -> c.xy.y }.thenBy { c -> c.xy.x })
        for (cart in carts) {
            cart.tick(track)
            if (carts.any { it !== cart && it.xy == cart.xy && !it.crashed}) {
                carts.filter { it.xy == cart.xy }.forEach { it.crashed = true }
            }
        }
    }
    return carts.first {!it.crashed}
}

fun right(heading: Direction): Direction = when (heading) {
    N -> E
    S -> W
    E -> N
    W -> S
}

fun left(heading: Direction): Direction = when (heading) {
    N -> W
    S -> E
    E -> S
    W -> N
}

fun turnLeft(heading: Direction): Direction = when (heading) {
    N -> W
    S -> E
    E -> N
    W -> S
}

fun turnRight(heading: Direction): Direction = when (heading) {
    N -> E
    S -> W
    E -> S
    W -> N
}

fun ahead(heading: Direction): Direction = heading

enum class Direction { N, E, S, W }

data class Point(val x: Int, val y: Int) {
    fun next(direction: Direction): Point = when (direction) {
        N -> Point(x, y - 1)
        E -> Point(x + 1, y)
        S -> Point(x, y + 1)
        W -> Point(x - 1, y)
    }
}

data class Cart(var xy: Point, var heading: Direction, var crossings: Int = 0, var crashed: Boolean = false) {
    fun tick(track: Map<Point, Char>) {
        heading = when (track[xy]) {
            '\\' -> ::left
            '/' -> ::right
            '+' -> when (crossings % 3) {
                0 -> ::turnLeft
                2 -> ::turnRight
                else -> ::ahead
            }.also { crossings+=1 }
            else -> ::ahead
        }(heading)
        xy = xy.next(heading)
    }
}
