package day12

import java.io.File

fun main(args: Array<String>) {

    val lines = File("src/day12/input.txt").readLines()
    val pots = lines[0].drop(15).mapIndexed { i, c -> Pot(i, c.toString()) }
    val rules = lines.drop(2).associateBy({ it.take(5) }, { it.takeLast(1) })

    generateSequence(pots) { evolve(it, rules) }
        .elementAt(20)
        .filter { it.state == "#" }
        .sumBy { it.pos }
        .let {println("Score after 20 iterations is $it")}

    val stable = generateSequence(pots) { evolve(it, rules) }
        .withIndex()
        .zipWithNext()
        .first {it.first.value.asString() == it.second.value.asString()}

    val time0 = stable.first.index
    val score0 = stable.first.value.score()
    val score1 = stable.second.value.score()
    val inc = score1-score0

    println("Score after 50000000000 iterations is ${score0 + inc * (50000000000 - time0)}")
}

fun evolve(pots: List<Pot>, rules: Map<String, String>): List<Pot> {
    return with((-4..-1).map { Pot(pots.first().pos + it) }.plus(pots).plus((1..4).map { Pot(pots.last().pos + it) })) {
        drop(2).dropLast(2).zip(windowed(5)) { p, l -> Pot(p.pos, rules[l.asString()]!!) }
            .dropWhile { it.state == "." }.dropLastWhile { it.state == "." }
    }
}

fun List<Pot>.score():Int = filter { it.state == "#" }.sumBy { it.pos }
fun List<Pot>.asString():String = joinToString("")

data class Pot(val pos: Int, val state: String = ".") {
    override fun toString(): String {
        return state
    }
}