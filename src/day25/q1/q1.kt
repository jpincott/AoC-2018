package day25.q1

import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {

    val coords = File("src/day25/q1/t1.txt").useLines { l ->
        l.map(String::trim)
            .map { it.split(',') }
            .map { it.map(String::toInt) }
            .map(::Coord)
            .toMutableList()
    }

    coords.forEachIndexed { i, c1 ->
        coords.drop(i + 1).forEach { c2 ->
            if (c1.dist(c2) <= 3) {
                println("|$c1-$c2|=${c1.dist(c2)}")
                c1.add(c2)
            }
        }
    }

    val set = mutableSetOf<MutableSet<Coord>>()
    while (coords.isNotEmpty()) {
        val constellation = coords.first().chain()
        set.add(constellation)
        coords.removeAll(constellation)
    }

    println("There are ${set.size} constellations")
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