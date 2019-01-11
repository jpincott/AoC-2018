package day25.q1

import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {

    val stars = File("src/day25/input.txt").useLines { l ->
        l.map(String::trim)
            .map { it.split(',') }
            .map { it.map(String::toInt) }
            .map(::Star)
            .toMutableList()
    }

    val constellations = mutableSetOf<Constellation>()

    stars.forEach { s ->
        val subsets = constellations.filter { c -> c.any { it near s } }.toSet()
        val union = subsets.flatten().plus(s).toMutableSet()
        constellations.removeAll(subsets)
        constellations.add(union)
    }

    println("There are ${constellations.size} constellations")
}

data class Star(val coords: List<Int>) {
    fun dist(s: Star): Int = coords.zip(s.coords).sumBy { abs(it.first - it.second) }
    infix fun near(s: Star): Boolean = dist(s) <= 3
}

typealias Constellation = MutableSet<Star>
