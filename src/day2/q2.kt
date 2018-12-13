package day2

import java.io.File

fun main(args: Array<String>) {

    val lines = File("src/day2/i1.txt").readLines()

    lines.forEachIndexed { i, s1 ->
        lines.subList(i+1, lines.size).forEach { s2 ->
            if (distance(s1, s2) == 1) println(common(s1, s2))
        }
    }
}

fun distance(s1: String, s2: String): Int {
    return s1.zip(s2).count { it.first != it.second }
}

fun common(s1: String, s2: String): String {
    return s1.zip(s2).filter { it.first == it.second}.map { it.first }.joinToString("")
}
