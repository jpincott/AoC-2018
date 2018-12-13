package day6

import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {

    val points = File("src/day6/input.txt").readLines()
        .map { it.split(", ").map(String::toInt) }
        .map(::Point)

    val bounds = points.map(::Box).reduce(Box::merge)

    bounds.x().forEach { x ->
        bounds.y().forEach { y ->
            points.groupBy { it.distance(x, y) }.toSortedMap().asSequence().first().value.singleOrNull()
                ?.apply { n++ }
                ?.also { if (bounds.borderContains(Point(x, y))) it.unbounded = true }
        }
    }

    println(points.filterNot { it.unbounded }.map { it.n }.max())

    bounds.x().flatMap { x ->
        bounds.y().map { y ->
            points.map { it.distance(x, y) }.sum()
        }
    }.count { it <= 10000 }.let { println(it) }

//

}

data class Point(val x: Int, val y: Int, var n: Int = 0, var unbounded: Boolean = false) {
    constructor(xy: List<Int>) : this(xy[0], xy[1])

    fun distance(x: Int, y: Int): Int = abs(x - this.x) + abs(y - this.y)
}

data class Box(val xMin: Int, val yMin: Int, val xMax: Int, val yMax: Int) {
    constructor(p: Point) : this(p.x, p.y, p.x, p.y)

    fun merge(box: Box): Box = Box(
        min(xMin, box.xMin),
        min(yMin, box.yMin),
        max(xMax, box.xMax),
        max(yMax, box.yMax)
    )

    fun x() = xMin..xMax
    fun y() = yMin..yMax
    fun centre() = Box(xMin + 1, yMin + 1, xMax - 1, yMax - 1)
    fun contains(p: Point): Boolean = x().contains(p.x) && y().contains(p.y)
    fun borderContains(p: Point): Boolean = contains(p) && !centre().contains(p)
}

