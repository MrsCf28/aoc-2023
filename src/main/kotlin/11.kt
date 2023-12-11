import kotlin.math.abs

//private val input = readFileAsLines("11.sample.txt")
private val input = readFileAsLines("11.txt")

fun main() {
    println(partOne())
    println(partTwo())
}

private fun partOne() = getSumOfLengths(1)
private fun partTwo() = getSumOfLengths(999_999)

private fun getSumOfLengths(spaceFactor: Long): Long {
    val galaxyList = input.map { it.toList() }

    val ySpaces = findSpaces(galaxyList)
    val xSpaces = findSpaces(transpose(galaxyList))

    val galaxyMap = mutableListOf<Coordinate>()
    for (row in galaxyList.indices) {
        for (col in galaxyList[0].indices) {
            if (galaxyList[row][col] == '#') {
                galaxyMap.add(Coordinate(col, row))
            }
        }
    }

    var sum = 0L
    for (location in galaxyMap) {
        for (otherLocation in galaxyMap) {
            sum += location.addTotalDistTo(otherLocation, xSpaces, ySpaces, spaceFactor)
        }
    }

    return sum / 2
}

private fun findSpaces(galaxyList: List<List<Char>>): List<Int> {
    return galaxyList.mapIndexed { i, it -> if (!it.contains('#')) i else -1 }.filter { it != -1 }
}

data class Coordinate(val x: Int, val y: Int) {
    private fun dist(other: Coordinate): Int {
        return (abs(this.x - other.x) + abs(this.y - other.y))
    }

    private fun xRange(other: Coordinate) = if (this.x > other.x) (other.x..this.x) else (this.x..other.x)

    private fun yRange(other: Coordinate) = if (this.y > other.y) (other.y..this.y) else (this.y..other.y)

    private fun addSpace(other: Coordinate, xSpace: List<Int>, ySpace: List<Int>, spaceFactor: Long): Long {
        var count = 0L
        for (x in xSpace) {
            if (x in this.xRange(other)) count += spaceFactor
        }
        for (y in ySpace) {
            if (y in this.yRange(other)) count += spaceFactor
        }
        return count
    }

    fun addTotalDistTo(other: Coordinate, xSpace: List<Int>, ySpace: List<Int>, spaceFactor: Long): Long {
        return dist(other) + addSpace(other, xSpace, ySpace, spaceFactor)
    }
}