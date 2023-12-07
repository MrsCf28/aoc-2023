import day7.CompareHands
import day7.CompareHands2

//private val input = readFileAsLines("07.sample.txt")
private val input = readFileAsLines("07.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Long {
    val hands = getHands(input, false)
    return calculateWinnings(hands)
}

private fun partTwo(input: List<String>): Long {
    val hands = getHands(input, true)
    return calculateWinnings(hands)
}

fun getHands(input: List<String>, wildCard: Boolean): List<List<String>> {
    return input
        .map {
            val (hand, bid) = it.split(" ")
            listOf(hand, bid, getGeneralHandRanking(hand, wildCard))
        }
        .sortedWith(if (wildCard) CompareHands2 else CompareHands)
        .sortedBy { it[2] }
}

fun getGeneralHandRanking(hand: String, wildCard: Boolean): String {
    return if (wildCard && hand.contains("J")) {
        when (hand.toSet().size) {
            5 -> "1"
            4 -> "3"
            3 -> if (hasOneJ(hand) && isTwoPair(hand)) "4" else "5"
            else -> "6"
        }
    } else {
        when (hand.toSet().size) {
            5 -> "0"
            4 -> "1"
            3 -> if (isTwoPair(hand)) "2" else "3"
            2 -> if (isFourOfAKind(hand)) "5" else "4"
            else -> "6"
        }
    }
}

fun isTwoPair(hand: String): Boolean {
    return hand.count { it == hand.toSet().first() } == 2
            || hand.count { it == hand.toSet().last() } == 2
}

fun isFourOfAKind(hand: String): Boolean {
    return hand.count { it == hand.toSet().first() } == 4
            || hand.count { it == hand.toSet().first() } == 1
}

fun hasOneJ(hand: String): Boolean {
    return hand.count { it == 'J' } == 1
}

fun calculateWinnings(hands: List<List<String>>): Long {
    return hands.mapIndexed { index, it -> it[1].toLong() * (index + 1) }.sum()
}


//251806792
//252113488