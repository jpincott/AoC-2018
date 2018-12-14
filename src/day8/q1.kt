package day8

import java.io.File

fun main(args: Array<String>) {

    val seq = File("src/day8/input.txt").readText().trim().split(" ").map { it.toInt() }.listIterator()

    val root = Node(seq)

    println(root.checksum())
    println(root.value())
}

class Node(seq: ListIterator<Int>) {

    val children: List<Node>
    val metadata: List<Int>

    init {
        val c = seq.next()
        val m = seq.next()
        children = (0 until c).map { Node(seq) }.toList()
        metadata = (0 until m).map { seq.next() }.toList()
    }

    fun checksum(): Int = metadata.sum() + children.sumBy(Node::checksum)

    fun value(): Int = when {
        children.isEmpty() -> metadata
        else -> metadata.filter { (1..children.size).contains(it) }.map { children[it - 1].value() }
    }.sum()

}
