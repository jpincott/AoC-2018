package day11

fun main(args: Array<String>) {

    val ser = 8561
    val cells = Array(300) { x -> Array(300) { y -> Cell(x + 1, y + 1) } }

        (1..298).flatMap { x ->
            (1..298).map { y ->
                cells[x - 1][y - 1] to (x..x+2).flatMap { xx -> (y..y+2).map { yy -> cells[xx - 1][yy - 1] } }.sumBy { it.value(ser) }
            }
        }.maxBy { it.second }!!.let { println(it.first) }
}

data class Cell(val x: Int, val y: Int) {
    fun value(ser:Int) : Int {
        var rackId = 10 + x
        var power = y * rackId
        power += ser
        power *=rackId
        power /= 100
        power %= 10
        power -= 5
        return power
    }
}