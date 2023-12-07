import kotlin.math.pow

//private val input = readFileAsLines("04.1.sample.txt")
 private val input = readFileAsLines("04.txt")

fun main(args: Array<String>) {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int {
    var points = 0;
    for (line in input) {
        val (_, winNumbers, cardNumbers) = line.split(":", "|")
        val wins = intList(winNumbers).intersect(intList(cardNumbers).toSet()).size
        points += 2.0.pow((wins - 1).toDouble()).toInt()
    }
    return points
}



private fun partTwo(input: List<String>): Int {
    val cards = MutableList(input.size) {1}
    for ((index, line) in input.withIndex()) {
        val (_, winNumbers, cardNumbers) = line.split(":", "|")
        val wins = intList(winNumbers).intersect(intList(cardNumbers).toSet()).size
        for (i in 1..wins) {
            cards[index + i] += cards[index]
        }
    }
    return cards.sum()
}
