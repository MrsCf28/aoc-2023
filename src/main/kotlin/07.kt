//private val input = readFileAsLines("07.sample.txt")
private val input = readFileAsLines("07.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int = calculateWinnings(input, false)
private fun partTwo(input: List<String>): Int = calculateWinnings(input, true)

fun calculateWinnings(input: List<String>, wildCard: Boolean): Int =
    getHands(input, wildCard).mapIndexed { index, it -> it.bid * (index + 1) }.sum()

fun getHands(input: List<String>, wildCard: Boolean): List<Hand> =
    input
        .map {
            val (hand, bid) = it.split(" ")
            Hand(hand, bid.toInt(), wildCard)
        }
        .sorted()
        .sortedBy { it.rank }

class Hand(private val hand: String, val bid: Int, private val wildCard: Boolean) : Comparable<Hand> {

    val rank = getGeneralHandRanking()

    private fun getGeneralHandRanking(): Int {
        return if (wildCard && hand.contains("J")) {
            when (hand.toSet().size) {
                5 -> 1
                4 -> 3
                3 -> if (hasOneJ(hand) && isTwoPair(hand)) 4 else 5
                else -> 6
            }
        } else {
            when (hand.toSet().size) {
                5 -> 0
                4 -> 1
                3 -> if (isTwoPair(hand)) 2 else 3
                2 -> if (isFourOfAKind(hand)) 5 else 4
                else -> 6
            }
        }
    }

    private fun isTwoPair(hand: String): Boolean = hand.count { it == hand.toSet().first() } == 2
            || hand.count { it == hand.toSet().last() } == 2

    private fun isFourOfAKind(hand: String): Boolean = hand.count { it == hand.toSet().first() } == 4
            || hand.count { it == hand.toSet().first() } == 1

    private fun hasOneJ(hand: String): Boolean = hand.count { it == 'J' } == 1

    override fun compareTo(other: Hand): Int {
        for (i in 0..4) {
            if (this.hand[i] == other.hand[i]) continue
            return cardRank(this.hand[i], wildCard).compareTo(cardRank(other.hand[i], wildCard))
        }
        return 0
    }

    private fun cardRank(label: Char, wildCard: Boolean): Int {
        return when (label) {
            'T' -> 10
            'J' -> if (wildCard) 1 else 11
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> label.digitToInt()
        }
    }
}