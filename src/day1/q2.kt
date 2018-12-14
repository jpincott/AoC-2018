package day1

import java.io.File

fun main(args: Array<String>) {

    var acc = 0
    var dup = 0
    val set = HashSet<Int>()

    val input = File("src/day1/input.txt")
    var lines = input.useLines { it.map { it.toInt() }.toList() }

    while (dup == 0) {
        for (n in lines) {
            acc += n
            if (!set.add(acc)) {
                dup = acc
                break
            }
        }
    }

    println(dup)

}