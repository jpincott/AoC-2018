package day3

import java.io.File

fun main(args: Array<String>) {

    // #1 @ 916,616: 21x29
    val regex = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")

    val claims = File("src/day3/input.txt").useLines {
        it.flatMap { regex.findAll(it) }
            .map { it.groupValues.drop(1) }
            .map { Claim(it) }
            .toList()
    }

    val claim = claims.filter { c1 -> claims.none { c2 -> c1 != c2 && c1.intersects(c2) } }.first()
    println(claim.id)

}