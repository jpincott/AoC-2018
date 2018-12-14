package day13

import java.io.File

fun main(args: Array<String>) {

    val lines = File("src/day13/test.txt").readLines()

    val carts = listOf<Cart>()
    val rules = mapOf<Point, Char>()
    val ops = mapOf('/' to fun(from:Direction)={from})
}

enum class Direction {N,S,E,W}
data class Point(val x:Int, val y:Int)
data class Cart(var xy:Point, var orientation:Direction)
