package day10

import java.io.File

fun main(args: Array<String>) {

    // position=< 9,  1> velocity=< 0,  2>
    val stars = File("src/day10/input.txt").readLines().map {
        "(-?\\d+)".toRegex().findAll(it)
            .map { it.value.toInt() }
            .toList()
            .let { (x, y, dx, dy) -> Star(x, y, dx, dy) }
    }

    generateSequence (Screen(stars)) { it -> Screen(it.stars.map { it.next() }) }
        .zipWithNext()
        .withIndex()
        .first {it.value.second.size > it.value.first.size}
        .apply{ println(value.first) }
        .apply{ println(index) }
}

data class Star(val x: Int, val y: Int, val dx: Int, val dy: Int) {
    fun next(): Star = Star(x + dx, y + dy, dx, dy)
}

class Screen(val stars: List<Star>) {

    val xMin = stars.map { it.x }.min()!!
    val yMin = stars.map { it.y }.min()!!
    val xMax = stars.map { it.x }.max()!!
    val yMax = stars.map { it.y }.max()!!

    val height = yMax - yMin
    val width = xMax - xMin
    val size = height.toBigInteger() * width.toBigInteger()

    override fun toString(): String {
        val pixels = Array(height + 1) { Array(width + 1) { ' ' } }
        stars.forEach { pixels[it.y - yMin][it.x - xMin] = 'X' }
        return pixels.map { it -> it.joinToString("") }.joinToString("\n")
    }
}