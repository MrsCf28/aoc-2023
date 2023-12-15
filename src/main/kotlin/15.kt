//private val input = readFile("15.sample.txt")
private val input = readFile("15.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: String): Int = input.split(",").sumOf { getHash(it) }

private fun partTwo(input: String): Int {
    val instructions = input.split(",")
    val boxes = mutableMapOf<Int, MutableList<Lens>>()

    loop@
    for (instruction in instructions) {

        if (instruction.contains("=")) {
            val (label, focalLength) = instruction.split("=")
            val lens = Lens(label, focalLength.toInt())
            val boxNumber = lens.boxNumber

            if (boxes.keys.contains(boxNumber)) {
                val box = boxes.getValue(boxNumber)
                for (existingLens in box) {
                    if (existingLens.label == label) {
                        existingLens.focalLength = lens.focalLength
                        continue@loop
                    }
                }
                box.add(lens)
            } else {
                boxes[boxNumber] = mutableListOf(lens)
            }
        } else {
            val label = instruction.dropLast(1)
            val boxNumber = getHash(label) + 1
            if (boxes.keys.contains(boxNumber)) {
                val box = boxes.getValue(boxNumber)
                for (i in box.indices) {
                    if (box[i].label == label) {
                        box.removeAt(i)
                        break
                    }
                }
            }
        }
    }

    return boxes.values.sumOf {
        it.mapIndexed { i, lens -> (lens.boxNumber) * (i + 1) * lens.focalLength }.sum()
    }
}

private fun getHash(sequence: String): Int = sequence.fold(0) { acc, next -> ((acc + next.code) * 17) % 256 }

class Lens(val label: String, var focalLength: Int) {
    val boxNumber = getHash(label) + 1
}
