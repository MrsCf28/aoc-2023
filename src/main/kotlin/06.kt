//private val input = readFileAsLines("06.sample.txt")
 private val input = readFileAsLines("06.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int {
    val races = pairTimeAndDistance(input)
    var possibleWins = 1
    for ((time, dist) in races) {
        var count = 0

        for (sec in 1 until time) {
            if (winningCombo(time, dist, sec)) {
                count++
            }
        }
        possibleWins *= count
    }
    return possibleWins
}

private fun partTwo(input: List<String>): Int {
    val time = removeKerning(input[0])
    val dist = removeKerning(input[1])
    var possibleWins = 0

        for (sec in 1 until time) {
            if (winningCombo(time, dist, sec)) {
                possibleWins++
            }
        }

    return possibleWins
}

fun winningCombo(totalTime: Long, recordDist: Long, waitTime: Long): Boolean {
    val raceTime = totalTime - waitTime
    val raceDist = raceTime * waitTime
    return (raceDist > recordDist)
}

fun removeKerning(string: String): Long {
    return string.substringAfter(": ").replace(" ", "").toLong()
}

fun pairTimeAndDistance(input: List<String>): MutableList<List<Long>> {
    val times = longList(input[0].substringAfter(": "))
    val distances = longList(input[1].substringAfter(": "))
    val pairs = mutableListOf<List<Long>>()
    for (i in times.indices) {
        pairs.add(listOf(times[i], distances[i]))
    }
    return pairs
}