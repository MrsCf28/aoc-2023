//private val input = readFileAsLines("16.sample.txt")
private val input = readFileAsLines("16.txt")

val xRange = 0 until input[0].length
val yRange = input.indices

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int {
    val cavernMap = mutableMapOf<CavernCoordinate, CavernTile>()
    for ((i, line) in input.withIndex()) {
        for ((j, tile) in line.withIndex()) {
            cavernMap[CavernCoordinate(j, i)] = CavernTile(tile)
        }
    }

    val startLocation = CavernCoordinate(0, 0)
    val startDirection = Go.RIGHT
    return countEnergizedTiles(cavernMap, startLocation, startDirection)
}

private fun partTwo(input: List<String>): Int {
    val cavernMap = mutableMapOf<CavernCoordinate, CavernTile>()
    for ((i, line) in input.withIndex()) {
        for ((j, tile) in line.withIndex()) {
            cavernMap[CavernCoordinate(j, i)] = CavernTile(tile)
        }
    }
    val totalEnergizedTiles = mutableListOf<Int>()

    for (i in xRange) {
        totalEnergizedTiles.add(countEnergizedTiles(cavernMap, CavernCoordinate(i, yRange.min()), Go.DOWN))
        totalEnergizedTiles.add(countEnergizedTiles(cavernMap, CavernCoordinate(i, yRange.max()), Go.UP))
    }
    for (j in yRange) {
        totalEnergizedTiles.add(countEnergizedTiles(cavernMap, CavernCoordinate(xRange.min(), j), Go.RIGHT))
        totalEnergizedTiles.add(countEnergizedTiles(cavernMap, CavernCoordinate(xRange.max(), j), Go.LEFT))
    }

    return totalEnergizedTiles.max()
}

data class CavernCoordinate(val x: Int, val y: Int) {
    fun nextCoordinate(direction: Go): CavernCoordinate {
        return when (direction) {
            Go.RIGHT -> CavernCoordinate(x + 1, y)
            Go.LEFT -> CavernCoordinate(x - 1, y)
            Go.DOWN -> CavernCoordinate(x, y + 1)
            Go.UP -> CavernCoordinate(x, y - 1)
        }
    }
}

class CavernTile(private val mirror: Char) {

    fun whereFrom(direct: Go): List<Go> {
        return when (this.mirror) {
            '.' -> listOf(direct)
            '/' -> dirFromForwardDiagonal(direct)
            '\\' -> dirFromBackwardDiagonal(direct)
            '-' -> dirFromHorizontalSplitter(direct)
            '|' -> dirFromVerticalSplitter(direct)
            else -> error("no direction")
        }
    }
}

enum class Go {
    UP, DOWN, LEFT, RIGHT;
}

data class LocationAndDirection(val location: CavernCoordinate, val direction: Go)

fun dirFromForwardDiagonal(direction: Go): List<Go> {
    val newDirection = when (direction) {
        Go.UP -> Go.RIGHT
        Go.LEFT -> Go.DOWN
        Go.RIGHT -> Go.UP
        Go.DOWN -> Go.LEFT
    }
    return listOf(newDirection)
}

fun dirFromBackwardDiagonal(direction: Go): List<Go> {
    val newDirection = when (direction) {
        Go.UP -> Go.LEFT
        Go.LEFT -> Go.UP
        Go.RIGHT -> Go.DOWN
        Go.DOWN -> Go.RIGHT
    }
    return listOf(newDirection)
}

fun dirFromHorizontalSplitter(direction: Go): List<Go> = when (direction) {
    Go.LEFT, Go.RIGHT -> listOf(direction)
    Go.UP, Go.DOWN -> listOf(Go.LEFT, Go.RIGHT)
}

fun dirFromVerticalSplitter(direction: Go): List<Go> = when (direction) {
    Go.LEFT, Go.RIGHT -> listOf(Go.UP, Go.DOWN)
    Go.UP, Go.DOWN -> listOf(direction)
}

fun countEnergizedTiles(
    cavernMap: MutableMap<CavernCoordinate, CavernTile>,
    startLocation: CavernCoordinate,
    startDirection: Go
): Int {
    val currentPositions = mutableListOf(LocationAndDirection(startLocation, startDirection))
    val visitedPositions = mutableSetOf<LocationAndDirection>()
    val nextPositions = mutableListOf<LocationAndDirection>()

    while (currentPositions.isNotEmpty()) {

        for (position in currentPositions) {
            if (visitedPositions.contains(position)) {
                continue
            } else visitedPositions.add(position)
            val currentTile = cavernMap.getValue(position.location)
            val nextDirections = currentTile.whereFrom(position.direction)
            for (direction in nextDirections) {
                val nextLocation = position.location.nextCoordinate(direction)

                if (nextLocation.x in xRange && nextLocation.y in yRange) {
                    nextPositions.add(LocationAndDirection(nextLocation, direction))
                } else {
                    continue
                }
            }
        }
        currentPositions.clear()
        currentPositions.addAll(nextPositions)
        nextPositions.clear()
    }
    return visitedPositions.map { it.location }.toSet().count()
}