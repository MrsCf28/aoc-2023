//private val input = readFileAsLines("08.sample.txt")
private val input = readFileAsLines("08.txt")

fun main() {
    val directions = input[0]
    val locationMap = input.subList(2, input.size).map {
        val (location, left, right) = it.split(" = (", ", ")
        location to Location(left, right.dropLast(1))
    }.toMap()

    println(partOne(directions, locationMap))
    println(partTwo(directions, locationMap))
}

class Location(val left: String, val right: String) {}

private fun partOne(directions: String, locationMap: Map<String, Location>): Long =
    findCount("AAA", directions, locationMap, "ZZZ")

private fun partTwo(directions: String, locationMap: Map<String, Location>): Long =
    locationMap.keys
        .filter { it.endsWith('A') }
        .map { findCount(it, directions, locationMap, "Z") }
        .reduce { acc, next -> findLCM(acc, next) }

fun findCount(start: String, directions: String, locationMap: Map<String, Location>, ending: String): Long {
    var count = 0L
    var location = start

    while (!location.endsWith(ending)) {
        for (direction in directions) {
            location = if (direction == 'L') locationMap[location]!!.left else locationMap[location]!!.right
            count++
        }
    }

    return count
}