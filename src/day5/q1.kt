package day5

import java.io.File

fun main(args: Array<String>) {

    val text = File("src/day5/input.txt").readText().trim()

    val regex = ('a'..'z').zip('A'..'Z').map { it.first.toString() + it.second }.flatMap { listOf(it, it.reversed()) }.joinToString("|").toRegex()

    println(text.shrink(regex).length)

    println(('a'..'z').map {
        text.replace(it.toString(), "", true).shrink(regex).length
    }.min())
}

tailrec fun String.shrink(regex:Regex) : String {
    val shrunk = this.replace(regex, "")
    val removed = this.length - shrunk.length
    return when (removed) {
        0 -> shrunk
        else -> shrunk.shrink(regex)
    }
}