package day2

import java.io.File

fun main(args: Array<String>) {

    val lines = File("src/day2/input.txt").readLines()

    val hasDoubles = hasCount(2)
    val hasTriples = hasCount(3)

    val checksum = lines.count(hasDoubles) * lines.count(hasTriples)
    println(checksum)

}

fun hasCount(n: Int): (String) -> Boolean {
    return { s -> s.groupingBy { it }.eachCount().filterValues { it == n }.isNotEmpty() }
}