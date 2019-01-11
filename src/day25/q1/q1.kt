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
        val containing = constellations.filter { constellation -> constellation.any { it.dist(star) <= 3 } }.toSet()
        val union = containing.flatten().plus(star).toMutableSet()
        constellations.removeAll(containing)
        constellations.add(union)
    }

    println("There are ${constellations.size} constellations")
}

data class Coord(val v: List<Int>) {
    fun dist(c: Coord): Int = v.zip(c.v).sumBy { abs(it.first - it.second) }
}