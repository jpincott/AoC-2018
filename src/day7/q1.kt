package day7

import java.io.File

fun main(args: Array<String>) {

    val lines = File("src/day7/input.txt").readLines()

    val tasks = mutableMapOf<String, Task>()

    lines.forEach {
        val (dep, task) = it.split(' ').slice(listOf(1, 7)).map { tasks.computeIfAbsent(it, ::Task) }
        task.dependsOn(dep)
    }

    val order = evaluate(emptyList(), tasks.values).joinToString("") { it.id }
    println(order)
}

tailrec fun evaluate(acc: Collection<Task>, tasks: Collection<Task>): Collection<Task> {
    if (tasks.isEmpty()) {
        return acc
    }

    val next = tasks.filter { it.isAvailable() }.minBy { it.id }!!.apply { done = true }
    return evaluate(acc.plus(next), tasks.minus(next))
}

class Task(val id: String) {

    val deps = mutableListOf<Task>()
    var done = false

    fun dependsOn(dep: Task) {
        deps.add(dep)
    }

    fun isAvailable(): Boolean {
        return !done && deps.all { it.done }
    }
}