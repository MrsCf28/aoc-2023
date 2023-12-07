private val input = readFileAsLines("05.1.sample.txt")
//private val input = readFileAsLines("05.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Long {
    val (_, seeds) = input[0].split(": ")
    var seedVariable = longList(seeds).toMutableList()
    val rangeDetails: MutableList<List<Long>> = mutableListOf()
    for (line in input.indices) {
        if (line < 2 || input[line].contains(":")) continue
        if (input[line].isBlank()) {
            seedVariable = mapSeedsToNextVariable(seedVariable, rangeDetails)
            rangeDetails.clear()
            continue
        } else {
            rangeDetails.add(longList(input[line]))
        }
    }
    seedVariable = mapSeedsToNextVariable(seedVariable, rangeDetails)
    return seedVariable.min()
}

private fun partTwo(input: List<String>): Long {
    val (_, seedDetails) = input[0].split(": ")
    val seedsRange = getSeedsRanges(longList(seedDetails))
    var location = Long.MAX_VALUE
    var currentVariable: Long
    val rangesList = getRangesList(input)
    for (seedPacket in seedsRange) {
        for (seed in seedPacket) {
            currentVariable = seed
            for (ranges in rangesList) {
                println(ranges)
                for (range in ranges) {
                    if (currentVariable in range[0]) {
                        currentVariable = range[1].first + currentVariable - range[0].first
                        break
                }
            }
            }
            if (currentVariable < location) {
                location = currentVariable
            }
        }
    }

    return location
}

fun mapSeedsToNextVariable(seeds: List<Long>, rangeDetails: List<List<Long>>): MutableList<Long> {
    val seedVariable = mutableListOf<Long>()
    val ranges = ranges(rangeDetails)
    for (seed in seeds) {
        var nextVariable: Long = seed
        for (range in ranges) {
            if (seed in range[0]) {
                nextVariable = seed + range[1].first - range[0].first
            }
        }
        seedVariable.add(nextVariable)
    }

    return seedVariable
}

fun ranges(rangeDetails: List<List<Long>>): List<List<LongRange>> {
    return rangeDetails.map {
        range -> listOf(
            range[1] until range[1] + range[2],
            range[0] until range[0] + range[2])
    }
}

fun getSeedsRanges(seedDetails: List<Long>): MutableList<LongRange> {
    val seeds = mutableListOf<LongRange>()
    for (detail in seedDetails.indices) {
        if (detail % 2 == 1) continue
        seeds.add(seedDetails[detail] until seedDetails[detail] + seedDetails[detail + 1])
    }
    return seeds
}

fun getRangesList(input: List<String>): MutableList<List<List<LongRange>>> {
    val rangesList = mutableListOf<List<List<LongRange>>>()
    val ranges = mutableListOf<List<LongRange>>()
    for (line in input.indices) {
        if (line < 2 || input[line].contains(":")) continue
        if (input[line].isNotBlank()) {
            val range = longList(input[line])
            val sourceRange = range[1] until range[1] + range[2]
            val destinationRange = range[0] until range[0] + range[2]
            ranges.add(listOf(sourceRange, destinationRange))
        }
        if (input[line].isBlank()) {
            rangesList.add(ranges.toList())
            ranges.clear()
        }
    }
    rangesList.add(ranges.toList())
    return rangesList
}