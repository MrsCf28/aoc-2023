//private val input = readFile("13.sample.txt")
private val input = readFile("13.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: String): Int {
    val listOfPatterns = input.split("\n\r".toRegex()).map { it.trim().lines() }
    var sum = 0

    for (pattern in listOfPatterns) {
        val reflectionLine = getReflectionSum(pattern, true)
        sum += if (reflectionLine != 0) {
            (reflectionLine)
        } else {
            getReflectionSum(transpose(pattern), false)
        }
    }

    return sum
}

private fun partTwo(input: String): Int {
    return 0
}

fun isMirroredAround(i: Int, pattern: List<String>): Boolean {
    for (j in 1..i) {
        if (i + j + 1 >= pattern.size) break
        if (pattern[i - j] != pattern[i + j + 1]) return false
    }
    return true
}

fun getReflectionSum(pattern: List<String>, isHorizontal: Boolean): Int {
    for (i in 0 until pattern.size - 1) {
        if (pattern[i] == pattern[i + 1]) {
            if (isMirroredAround(i, pattern)) {
                return if (isHorizontal) (i + 1) * 100 else i + 1
            }
        }
    }
    return 0
}