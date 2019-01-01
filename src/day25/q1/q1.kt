package day25.q1

import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {

    val coords = File("src/day25/input.txt").useLines { l ->
        l.map(String::trim)
            .map { it.split(',') }
            .map { it.map(String::toInt) }
            .map(::Coord)
            .toMutableList()
    }

    val constellations = mutableSetOf<MutableSet<Coord>>()

    coords.forEach { star ->
        val nearby = constellations.filter { constellation -> constellation.any {it.dist(star) <= 3} }.toSet()
        constellations.removeAll(nearby)
        val element = nearby.flatten().plus(star).toMutableSet()
        constellations.add(element)
    }

    println("There are ${constellations.size} constellations")
}

data class Coord(val x: Int, val y: Int, val z: Int, val t: Int) {
    constructor(l: List<Int>) : this(l[0], l[1], l[2], l[3])

    val neighbours = mutableSetOf<Coord>()
    fun add(c: Coord): Boolean = neighbours.add(c)

    fun asList(): List<Int> = listOf(x, y, z, t)
    fun dist(c: Coord): Int = asList().zip(c.asList()).sumBy { abs(it.first - it.second) }

    fun chain(): MutableSet<Coord> {
        val elements: List<Coord> = neighbours.map { it.chain() }.flatten()
        return mutableSetOf(this, *elements.toTypedArray())
    }

}