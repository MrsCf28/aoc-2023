fun readFile(name: String) = object {}::class.java.classLoader.getResource(name)!!.readText().trimEnd()

fun readFileAsLines(name: String) = readFile(name).lines()

fun intList(string: String): List<Int> {
    return string.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
}

fun longList(string: String): List<Long> {
    return string.trim().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
}