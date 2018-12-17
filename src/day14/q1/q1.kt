package day14.q1

fun main(args: Array<String>) {

    generateSequence(State(listOf(0, 1), "37")) { s -> s.next() }
        .first { it.recipies.length == 824501 + 10 }
        .let{ println(it.recipies.takeLast(10)) }
}

data class State(val elves: List<Int>, val recipies: String) {
    fun next(): State = with(recipies + recipies.slice(elves).sumBy(::asInt)) {
        State(elves.map { (it + 1 + asInt(get(it))) % length }, this)
    }

}

fun asInt(c: Char): Int = Character.getNumericValue(c)