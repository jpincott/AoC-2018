package day2

import java.io.File

fun main(args: Array<String>) {

    val lines = File("src/day2/i1.txt").readLines()

    val count2 = lines.filter(hasCount(2)).count()
    val count3 = lines.filter(hasCount(3)).count()

    println(count2)
    println(count3)
    println(count2 * count3)

}

fun hasCount(n: Int): (String) -> Boolean {
    return { s -> s.groupingBy { it }.eachCount().filterValues { it == n }.isNotEmpty() }
}
