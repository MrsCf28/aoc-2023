package day7

class CompareHands2 {

    companion object: Comparator<List<String>> {
        override fun compare(hand1: List<String>, hand2: List<String>): Int {
            for (i in 0..4) {
                if (hand1[0][i] == hand2[0][i]) continue
                return compareCards(hand1[0][i], hand2[0][i])
            }
            return 0
        }

        private fun compareCards(card1: Char, card2: Char): Int {
            val cardOrder = mapOf('2' to 2, '3' to 3, '4' to 4, '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9, 'T' to 10, 'J' to 1, 'Q' to 12, 'K' to 13, 'A' to 14)
            return (cardOrder[card1]!!.compareTo(cardOrder[card2]!!))
        }
    }
}