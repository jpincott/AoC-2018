package day3

class Claim(spec: List<String>) {

    val id: String = spec[0]
    val x = toRange(spec[1], spec[3])
    val y = toRange(spec[2], spec[4])

    fun toRange(a: String, b: String): IntRange {
        val start = Integer.parseInt(a)
        val end = start + Integer.parseInt(b)
        return start until end
    }

    fun covers(x: Int, y: Int): Boolean {
        return x in this.x && y in this.y
    }

    fun intersects(claim: Claim): Boolean {
        return x.intersect(claim.x).isNotEmpty() && y.intersect(claim.y).isNotEmpty()
    }
}