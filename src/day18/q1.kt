package day18

import java.io.File

fun main(args: Array<String>) {

    val lines = File("src/day18/input.txt").readLines()
    val dim = 2 + lines.size

    val grid = Array(dim) { Array(dim) { 'X' } }
    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            grid[y + 1][x + 1] = c
        }
    }

    generateSequence(grid) { grid -> next(grid) }
//        .drop(10000)
//        .take(100)
//        .forEach { show(it) }
        .drop(10)
        .first()
        .flatten()
        .groupingBy { it }
        .eachCount()
        .run {println(getValue('|') * getValue('#'))}
}


fun next(grid: Grid): Grid = Array(grid.size){Array(grid.size){'X'}}.apply {
    (1 until grid.size-1).forEach { x ->
        (1 until grid.size-1).forEach  { y ->
            val map = neighbours(grid, x, y)
            this[x][y] = when (grid[x][y]) {
                '.' -> if (map['|'] ?:0 >= 3) '|' else '.'
                '|' -> if (map['#'] ?:0 >= 3) '#' else '|'
                '#' -> if (map['#'] ?:0 >= 1
                        && map['|'] ?:0 >= 1) '#' else '.'
                else -> grid[x][y]
            }
        }
    }
}

fun neighbours(grid: Grid, x: Int, y: Int): Map<Char, Int> =
    (-1..1).flatMap { dx ->
        (-1..1).map { dy ->
            if (dx == 0 && dy == 0) 'X' else grid[x + dx][y + dy]
        }
    }.groupingBy { it }.eachCount()

typealias Grid = Array<Array<Char>>

fun show(grid:Grid) {
    grid.map { y -> y.joinToString ("") { it.toString() } }.joinToString("\n")
        .let {println(it)}
}