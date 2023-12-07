//private val input = readFileAsLines("03.1.sample.txt")
private val input = readFileAsLines("03.txt")

fun main() {
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<String>): Int {
    val matrix = input.map { line -> line.map { it } }

    var sum = 0
    var number = ""
    var adjacentToSymbol = false

    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            val element = matrix[i][j]
            if (element.isDigit()) {
                number += element
                if (adjacentToSymbol) continue
                if (checkForSymbol(matrix, i, j)) adjacentToSymbol = true
            } else {
                if (adjacentToSymbol) {
                    sum += number.toInt()
                    adjacentToSymbol = false
                }
                number = ""
            }
        }
    }

    return sum
}

private fun partTwo(input: List<String>): Int {
    val matrix = input.map { line -> line.map { it } }
    var sum = 0

    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            if (matrix[i][j] == '*') {
                sum += checkForAndMultiplyAdjacentNumbers(matrix, i, j)
            }
        }
    }

    return sum
}

fun checkForSymbol(matrix: List<List<Char>>, i: Int, j: Int): Boolean {
    val symbols = "/#?@*%$£^+=-&"
    for (x in -1..1) {
        for (y in -1..1) {
            if (i + x in matrix.indices && j + y in matrix[i].indices) {
                if (symbols.contains(matrix[i + x][j + y])) return true
            }
        }
    }
    return false
}

fun checkForAndMultiplyAdjacentNumbers(matrix: List<List<Char>>, i: Int, j: Int): Int {
    return if (numbersAreHorizontallyAdjacent(matrix, i, j)) {
        multiplyAdjacentNumbers(matrix, i, j)
    } else if (aboveAndBetweenHorizontalAdjacentNumbers(matrix, i, j)) {
        multiplyAdjacentNumbers(matrix, i + 1, j)
    } else if (belowAndBetweenHorizontalAdjacentNumbers(matrix, i, j)) {
        multiplyAdjacentNumbers(matrix, i - 1, j)
    } else if (checkForBetweenOneAboveAndOneBelow(matrix, i, j)) {
        multiplyOneAboveAndOneBelow(matrix, i, j)
    } else if (matrix[i][j - 1].isDigit() || matrix[i][j + 1].isDigit()) {
        multiplyNumbersOnAdjacentLines(matrix, i, j)
    } else {
        0
    }
}


fun numbersAreHorizontallyAdjacent(matrix: List<List<Char>>, i: Int, j: Int): Boolean {
    return (j - 1 in matrix[i].indices && j + 1 in matrix[i].indices)
            && (matrix[i][j - 1].isDigit() && matrix[i][j + 1].isDigit())
}

fun aboveAndBetweenHorizontalAdjacentNumbers(matrix: List<List<Char>>, i: Int, j: Int): Boolean {
    return (i + 1 in matrix.indices && !matrix[i + 1][j].isDigit() && j - 1 in matrix[i].indices && j + 1 in matrix[i].indices)
            && (matrix[i + 1][j - 1].isDigit() && matrix[i + 1][j + 1].isDigit())
}

fun belowAndBetweenHorizontalAdjacentNumbers(matrix: List<List<Char>>, i: Int, j: Int): Boolean {
    return (i - 1 in matrix.indices && !matrix[i - 1][j].isDigit() && j - 1 in matrix[i].indices && j + 1 in matrix[i].indices)
            && (matrix[i - 1][j - 1].isDigit() && matrix[i - 1][j + 1].isDigit())
}

fun multiplyAdjacentNumbers(matrix: List<List<Char>>, i: Int, j: Int): Int {
    return numberToLeft(matrix, i, j) * numberToRight(matrix, i, j)
}

fun numberToLeft(matrix: List<List<Char>>, i: Int, j: Int): Int {
    return if (j - 3 in matrix[i].indices && matrix[i][j - 3].isDigit() && matrix[i][j - 2].isDigit()) {
        "${matrix[i][j - 3]}${matrix[i][j - 2]}${matrix[i][j - 1]}".toInt()
    } else if (j - 2 in matrix[i].indices && matrix[i][j - 2].isDigit()) {
        "${matrix[i][j - 2]}${matrix[i][j - 1]}".toInt()
    } else {
        matrix[i][j - 1].digitToInt()
    }
}

fun numberToRight(matrix: List<List<Char>>, i: Int, j: Int): Int {
    return if (j + 3 in matrix[i].indices && matrix[i][j + 3].isDigit() && matrix[i][j + 2].isDigit()) {
        "${matrix[i][j + 1]}${matrix[i][j + 2]}${matrix[i][j + 3]}".toInt()
    } else if (j + 2 in matrix[i].indices && matrix[i][j + 2].isDigit()) {
        "${matrix[i][j + 1]}${matrix[i][j + 2]}".toInt()
    } else {
        matrix[i][j + 1].digitToInt()
    }
}



fun checkForBetweenOneAboveAndOneBelow(matrix: List<List<Char>>, i: Int, j: Int): Boolean {
    var oneAbove = false
    var oneBelow = false
    if (i + 1 in matrix.indices && i - 1 in matrix.indices) {
        for (x in -1..1) {
            if (j + x in matrix[i + 1].indices && matrix[i + 1][j + x].isDigit()) {
                oneAbove = true
                break
            }
        }
        for (x in -1..1) {
            if (j + x in matrix[i - 1].indices && matrix[i - 1][j + x].isDigit()) {
                oneBelow = true
                break
            }
        }
    }
    return oneAbove && oneBelow
}

fun multiplyNumbersOnAdjacentLines(matrix: List<List<Char>>, i: Int, j: Int): Int {
    var result = 0
    if (i + 1 in matrix.indices) {
        result = findNumbersOnAdjacentLines(matrix, i, j, i + 1)
    }
    if (i - 1 in matrix.indices && result == 0) {
        result = findNumbersOnAdjacentLines(matrix, i, j, i - 1)
    }
    return result
}

fun findNumbersOnAdjacentLines(matrix: List<List<Char>>, i: Int, j: Int, ind: Int): Int {
    for (x in -1..1) {
        if (j + x in matrix[ind].indices && matrix[ind][j + x].isDigit()) {
            val number1 = findNumber(matrix, ind, j)
            val number2 = if (matrix[i][j - 1].isDigit()) numberToLeft(matrix, i, j) else numberToRight(matrix, i, j)
            return number1 * number2
        }
    }
    return 0
}

fun multiplyOneAboveAndOneBelow(matrix: List<List<Char>>, i: Int, j: Int): Int {
    val numberAbove = findNumber(matrix, i - 1, j)
    val numberBelow = findNumber(matrix, i + 1, j)
    return numberAbove * numberBelow
}

fun findNumber(matrix: List<List<Char>>, i: Int, j: Int): Int {
    return if (j - 3 in matrix[i].indices && matrix[i][j - 3].isDigit() && matrix[i][j - 2].isDigit() && matrix[i][j - 1].isDigit()) {
            "${matrix[i][j - 3]}${matrix[i][j - 2]}${matrix[i][j - 1]}".toInt()
    } else if (j - 2 in matrix[i].indices && matrix[i][j - 2].isDigit() && matrix[i][j - 1].isDigit()) {
        if (matrix[i][j].isDigit()) {
            "${matrix[i][j - 2]}${matrix[i][j - 1]}${matrix[i][j]}".toInt()
        } else {
            "${matrix[i][j - 2]}${matrix[i][j - 1]}".toInt()
        }
    } else if (j - 1 in matrix[i].indices && matrix[i][j - 1].isDigit()) {
        if (j + 1 in matrix[i].indices && matrix[i][j].isDigit() && matrix[i][j + 1].isDigit()) {
            "${matrix[i][j - 1]}${matrix[i][j]}${matrix[i][j + 1]}".toInt()
        } else if (matrix[i][j].isDigit()) {
            "${matrix[i][j - 1]}${matrix[i][j]}".toInt()
        } else {
            matrix[i][j].digitToInt()
        }
    } else if (matrix[i][j].isDigit()) {
        if (j + 2 in matrix[i].indices && j + 1 in matrix[i].indices && matrix[i][j + 2].isDigit() && matrix[i][j + 1].isDigit()) {
            "${matrix[i][j]}${matrix[i][j + 1]}${matrix[i][j + 2]}".toInt()
        } else if (j + 1 in matrix[i].indices && matrix[i][j + 1].isDigit()) {
            "${matrix[i][j]}${matrix[i][j + 1]}".toInt()
        } else {
            matrix[i][j].digitToInt()
        }
    } else if (j + 3 in matrix[i].indices && j + 2 in matrix[i].indices && matrix[i][j + 3].isDigit() && matrix[i][j + 2].isDigit()) {
        "${matrix[i][j + 1]}${matrix[i][j + 2]}${matrix[i][j + 3]}".toInt()
    } else if (j + 2 in matrix[i].indices && matrix[i][j + 2].isDigit()) {
        "${matrix[i][j + 1]}${matrix[i][j + 2]}".toInt()
    }
    else {
        matrix[i][j + 1].digitToInt()
    }
}