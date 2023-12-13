//private val input = readFileAsLines("12.sample.txt")
private val input = readFileAsLines("12.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int {
    var count = 0

    for (line in input) {
        val (springs, damaged) = line.split(" ")
        val damagedList: List<Int> = damaged.split(",").map { it.toInt() }
        val damagedListSize = damagedList.size
        val minListLength = damagedListSize + damagedList.sum() - 1
        val inputSprings = SpringList(springs)
        val potentiallyDamagedSprings = potentiallyDamaged(springs)


        if (minListLength == springs.length || potentiallyDamagedSprings == damagedList) {
            count++
            println("$line: PDS = $potentiallyDamagedSprings, DL = $damagedList, count = +1")
            continue
        }

//        if (damagedListSize == potentiallyDamagedSprings.size) {
//            var combinations = 1
//            for (i in damagedList.indices) {
//                combinations *= nCr(potentiallyDamagedSprings[i], damagedList[i])
//            }
//            count += combinations
//            println("$line: PDS = $potentiallyDamagedSprings, DL = $damagedList, count = +$combinations")
//            continue
//        }

//        println("$line: PDS = $potentiallyDamagedSprings, DL = $damagedList")

        val checkSpringLists = mutableListOf(mutableListOf<Char>())

        for (char in springs) {
            if (char == '.' || char == '#') checkSpringLists.forEach { it.add(char) }
            else {
                checkSpringLists.addAll(checkSpringLists.map { it.toMutableList() })
                checkSpringLists.forEachIndexed { index, chars ->
                    if (index < checkSpringLists.size / 2) chars.add('.') else chars.add('#')
                }
            }
        }

        val finalList = checkSpringLists.map { it.joinToString("") }

        for (list in finalList) {
            if (SpringList(list).checkValid(inputSprings, damagedList)) {
                count++
            }
        }
    }

    return count
}

private fun partTwo(input: List<String>): Int {
    return 0
}

class SpringList(private val springs: String) {
    val knownDamaged = springs.count { it == '#' }
    val length = springs.length
    private val workingLocations = springs.mapIndexed { i, it -> if (it == '.') i else -1 }.filter { it > -1 }
    val damagedSprings = springs.split("[.?]+".toRegex()).filter { it.isNotEmpty() }.map { it.length }
    val missing = springs.count { it == '?' }

    fun checkValid(inputSprings: SpringList, damagedList: List<Int>): Boolean {
        return this.workingLocations.containsAll(inputSprings.workingLocations)
                && this.knownDamaged == damagedList.sum()
                && this.damagedSprings == damagedList
    }

}

fun potentiallyDamaged(springs: String) = springs
    .split("\\.+".toRegex()).filter { it.isNotEmpty() }.map { it.count() }