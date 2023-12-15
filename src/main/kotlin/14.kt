private val input = readFileAsLines("14.sample.txt")
//private val input = readFileAsLines("14.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int {
    val boulderMap = transpose(input)
    val distanceToSouth = boulderMap[0].length
    var sum = 0

    for (line in boulderMap) {
        var dist = distanceToSouth
        for (i in line.indices) {
            if (line[i] == 'O') sum += dist - i
            if (line[i] == '.') dist++
            if (line[i] == '#') dist = distanceToSouth
        }
    }
    return sum
}

private fun partTwo(input: List<String>): Int {

    return 0
}