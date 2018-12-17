package day14.q2

fun main(args: Array<String>) {

    val n = 824501
    val str = "824501"

    val elf1 = Recipe(3,1).apply { next = this }.apply { prev = this }
    val elf2 = elf1.add(Recipe(7,2))
    val state = State(elf1, elf2)

//    generateSequence(state) { s -> s.next() }
//        .first{it.last.idx >= n + 10}
//        .let { generateSequence(it.last) { r -> r.prev }
//            .takeWhile { it.idx > n }
//            .fold("") { acc, r -> r.value.toString() + acc }
//            .take(10)
//        }
//        .let{println(it)}

    generateSequence(state) { s -> s.next() }
        .drop(str.length)
        .first {
            generateSequence(it.last) { r -> r.prev }
                .take(str.length + 1)
                .fold("") { acc, r -> r.value.toString() + acc }
                .contains(str, true)
        }
        .let {
            generateSequence(it.last to "${it.last.value}") { (r, s) -> r.prev to r.prev.value.toString() + s }
                .first { it.second.startsWith(str) }
        }
        .let { println(it.first.idx - 1) }
}

data class State(val elf1:Recipe, val elf2:Recipe, val last:Recipe=elf2) {

    fun next(): State {
        val nextLast = "${elf1.value+elf2.value}"
            .mapIndexed { i, c -> Recipe(asInt(c), last.idx+i+1).apply { next = this }.apply { prev = this } }
            .fold (last) { l, r -> l.add(r) }
        val nextElf1 = elf1.next(1 + elf1.value)
        val nextElf2 = elf2.next(1 + elf2.value)
        return State(nextElf1, nextElf2, nextLast)
    }
}

data class Recipe(val value: Int, val idx:Int) {

    lateinit var next: Recipe
    lateinit var prev: Recipe

    fun add(r: Recipe): Recipe {
        this.next.prev = r
        r.next = this.next
        r.prev = this
        this.next = r
        return r
    }

    fun next(n: Int = 1): Recipe = if (n == 1) next else next.next(n - 1)
}

fun asInt(c: Char): Int = Character.getNumericValue(c)