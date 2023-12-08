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