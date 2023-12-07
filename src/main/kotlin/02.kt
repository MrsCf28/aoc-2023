private val input = readFileAsLines("02.txt")

fun main(args: Array<String>) {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int {
    var sum = 0;
    for (line in input) {
        val (gameName, sets) = line.split(":")
        val (_, gameId) = gameName.split(" ")
        if (isEnoughCubes(sets)) {
            sum += gameId.toInt()
        }

    }
    return sum
}

fun isEnoughCubes(sets: String): Boolean {
    for (set in sets.split(";")) {
        for (cube in set.split(",")) {
            val (_, cubes, cubeColour) = cube.split(" ")
            when (cubeColour) {
                "blue" -> if (cubes.toInt() > 14) return false
                "red" -> if (cubes.toInt() > 12) return false
                "green" -> if (cubes.toInt() > 13) return false
                else -> continue
            }
        }
    }
    return true
}

private fun partTwo(input: List<String>): Int {
    var sum = 0
    for (line in input) {
        val (_, sets) = line.split(":")
        sum += powerOfSet(sets)
    }
    return sum
}

fun powerOfSet(sets: String): Int{
    var blue = 1
    var red = 1
    var green = 1
    for (set in sets.split(";")) {
        for (cube in set.split(",")) {
            val (_, cubes, cubeColour) = cube.split(" ")
            when (cubeColour) {
                "blue" -> if (cubes.toInt() > blue) { blue = cubes.toInt() }
                "red" -> if (cubes.toInt() > red) { red = cubes.toInt() }
                "green" -> if (cubes.toInt() > green) { green = cubes.toInt() }
                else -> continue
            }
        }
    }
    return blue * red * green
}