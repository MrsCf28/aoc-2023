//private val input = readFileAsLines("09.sample.txt")
private val input = readFileAsLines("09.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Long = findSumOfTerms(input, true)
private fun partTwo(input: List<String>): Long = findSumOfTerms(input, false)

fun findDiffSequence(sequence: List<Long>): List<Long> = List(sequence.size) { i ->
    if (i + 1 < sequence.size) sequence[i + 1] - sequence[i] else 0L
}.dropLast(1)

fun findSumOfTerms(input: List<String>, next: Boolean): Long =
    input.sumOf { line ->
        val sequence = if (next) longList(line) else longList(line).reversed()
        var sequenceDiff = findDiffSequence(sequence)
        val sequenceHistory = mutableListOf(sequence, sequenceDiff)
        while (sequenceDiff.toSet().size != 1) {
            sequenceDiff = findDiffSequence(sequenceDiff)
            sequenceHistory.add(sequenceDiff)
        }
        sequenceHistory.sumOf { it.last() }
    }