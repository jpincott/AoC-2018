package day11

fun main(args: Array<String>) {

    val power = power(8561)
    val grid = Array(301) { Array(301) { 0 } }

    (1..300).forEach { x ->
        (1..300).forEach { y ->
            grid[x][y] = power(x, y) + grid[x - 1][y] + grid[x][y - 1] - grid[x - 1][y - 1]
        }
    }

    fun maxSquareBySizes(sizes: IntRange): Square = sizes.asSequence()
        .flatMap { s ->
            (1..300 - (s - 1)).asSequence().flatMap { x ->
                (1..300 - (s - 1)).asSequence().map { y ->
                    Square(s, x, y, grid[x + s - 1][y + s - 1] - grid[x - 1][y + s - 1] - grid[x + s - 1][y - 1] + grid[x - 1][y - 1])
                }
            }
        }.maxBy { it.value }!!

    println(maxSquareBySizes(3..3))
    println(maxSquareBySizes(1..300))
}

fun power(serial: Int): (x: Int, y: Int) -> Int = { x, y -> (y * (10 + x) + serial) * (10 + x) / 100 % 10 - 5 }

data class Square(val size: Int, val x:Int, val y:Int, val value:Int)
