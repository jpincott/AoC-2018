package day3

import java.io.File

fun main(args: Array<String>) {

    // #1 @ 916,616: 21x29
    val regex = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")

    val lines = File("src/day3/input.txt").useLines {
        it.flatMap { regex.findAll(it) }
            .map { it.groupValues.drop(1) }
            .map { Claim(it) }
            .toList()
    }

    var n = 0
    (0 until 1000).forEach { x ->
        (0 until 1000).forEach { y ->
            if (lines.filter { it.covers(x, y) }.take(2).count() > 1) n++
        }
    }

    println(n)
}

