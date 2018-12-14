package day14

fun main(args: Array<String>) {

    generateSequence(State(listOf(0, 1), "37")) { s -> s.next() }
        .first { it.recipies.length == 824501 + 10 }
        .let{ println(it.recipies.takeLast(10)) }

    // 1031816654

//    generateSequence(State(listOf(0, 1), "37")) { s -> s.next() }
//        .first { it.recipies.endsWith("824501") }
//        .also { println(it.recipies) }
//        .also { println(it.recipies.dropLast(6)) }
//        .also { println(it.recipies.dropLast(6).length) }
}

data class State(val elves: List<Int>, val recipies: String) {
    fun next(): State = with(recipies + recipies.slice(elves).sumBy(::asInt)) {
        State(elves.map { (it + 1 + asInt(get(it))) % length }, this)
    }

}

fun asInt(c: Char): Int = Character.getNumericValue(c)