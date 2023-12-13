fun readFile(name: String) = object {}::class.java.classLoader.getResource(name)!!.readText().trimEnd()

fun readFileAsLines(name: String) = readFile(name).lines()

fun intList(string: String): List<Int> {
    return string.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
}

fun longList(string: String): List<Long> {
    return string.trim().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm < maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) return lcm
        lcm += larger
    }
    return maxLcm
}

fun transpose(lists: List<List<Char>>): List<List<Char>> {
    val transposedLists = mutableListOf<List<Char>>()
    for (i in lists[0].indices) {
        val transposedList = mutableListOf<Char>()
        for (j in lists.indices) {
            transposedList.add(lists[j][i])
        }
        transposedLists.add(transposedList.toList())
    }
    return transposedLists.toList()
}

fun nCr(n: Int, r: Int) = fact(n) / (fact(r) * fact(n - r))

fun fact(n: Int): Int = if (n <= 1) 1 else n * fact(n - 1)

