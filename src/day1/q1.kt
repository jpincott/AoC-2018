package day1

import java.io.File

fun main(args: Array<String>) {

    val input = File("src/day1/input.txt")
    println(input.useLines { it.map { it.toInt() }.sum() })

}