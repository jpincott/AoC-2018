package day9

fun main(args: Array<String>) {

    println(maxScore(438, 71626))
    println(maxScore(438, 7162600))

}

fun maxScore(players: Int, marbles: Int): Long {

    val elves = (1..players).map { Elf(it) }
    var current = Marble(0).apply { next = this }.apply { prev = this }

    for (m in 1..marbles) {
        if (m % 23 != 0) {
            val marble = Marble(m)
            current.next().add(marble)
            current = marble
        } else {
            val elf = elves[(m - 1) % players]
            val marble = current.prev(7).remove()
            current = marble.next()
            elf.score = elf.score + m + marble.value
        }
    }

    return elves.map { it.score }.max()!!
}

data class Elf(val id: Int, var score: Long = 0)

class Marble(val value: Int) {

    lateinit var next: Marble
    lateinit var prev: Marble

    fun next(n: Int = 1): Marble = if (n == 1) next else next.next(n - 1)
    fun prev(n: Int = 1): Marble = if (n == 1) prev else prev.prev(n - 1)

    fun add(m: Marble) {
        this.next.prev = m
        m.next = this.next
        m.prev = this
        this.next = m
    }

    fun remove(): Marble {
        this.prev.next = this.next
        this.next.prev = this.prev
        return this
    }
}