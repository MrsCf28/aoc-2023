private val input = readFileAsLines("01.txt")

fun main(args: Array<String>) {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int {
    var sum = 0;
    for (line in input) {
        var numToBe = ""
        numToBe += findFirstDigitInString(line)
        numToBe += findFirstDigitInString(line.reversed())

        sum += numToBe.toInt()
    }
    return sum
}

fun findFirstDigitInString(string: String): Char {
    for (char in string) {
        if (char.isDigit()) return char
    }
    error("invalid input")
}

private fun partTwo(input: List<String>): Int {
    var sum = 0;
    val digits = mapOf("one" to '1', "two" to '2', "three" to '3', "four" to '4', "five" to '5', "six" to '6', "seven" to '7', "eight" to '8', "nine" to '9')
    val digitsReversed = mapOf("eno" to '1', "owt" to '2', "eerht" to '3', "ruof" to '4', "evif" to '5', "xis" to '6', "neves" to '7', "thgie" to '8', "enin" to '9')
    for (line in input) {
        var numToBe = ""
        numToBe += findFirstDigit(line, digits)
        numToBe += findFirstDigit(line.reversed(), digitsReversed)
        println(numToBe)

        sum += numToBe.toInt()
    }
    return sum
}

fun findFirstDigit(string: String, digits: Map<String, Char>): Char? {
    for (i in 0..string.length - 2) {
        if (string[i].isDigit()) return string[i]
        if (string[i + 1].isDigit()) return string[i + 1]
        if (string[i + 2].isDigit()) return string[i + 2]
        val charsLeft = string.length - i

        val substring = if (charsLeft < 5) {
            string.substring(i, i + charsLeft)
        } else {
            string.substring(i, i + 5)
        }

        for (digit in digits.keys) {
            if (substring.contains(digit)) {

                return digits[digit]
            }
        }
    }
    error("invalid input")
}