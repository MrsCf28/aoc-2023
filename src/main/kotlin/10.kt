//private val input = readFileAsLines("10.sample.txt")
private val input = readFileAsLines("10.txt")

fun main() {
    val pipeMap = mutableMapOf<Coordinate, Pipe>()
    for ((i, line) in input.withIndex()) {
        for ((j, pipe) in line.withIndex()) {
            pipeMap[Coordinate(j, i)] = Pipe(pipe, Coordinate(j, i))
        }
    }

    println(partOne(pipeMap))
    println(partTwo(pipeMap))
}

private fun partOne(pipeMap: MutableMap<Coordinate, Pipe>): Int {
    var count = 0
    
    var prevPipe = pipeMap.filterValues { it.type == 'S' }.values.first()
    var nextPipe = prevPipe.getNext(pipeMap)

    do {
        nextPipe.loopEdge = true
        val currentPipe = nextPipe
        nextPipe = pipeMap[currentPipe.getNextPipeCoordinate(prevPipe)]!!
        prevPipe = currentPipe
        count++
    } while (nextPipe.type != 'S')

    return (count + 1) / 2
}

private fun partTwo(pipeMap: MutableMap<Coordinate, Pipe>): Int {
    var count2 = 0

    for (i in input.indices) {
        var inside = false
        var wonkyLines = ""
        var numOfWonkyLines = 0
        for (j in 0 until input[0].length) {
            val pipe = pipeMap.getValue(Coordinate(j, i))
            if (pipe.loopEdge) {
                if (pipe.type == '-') continue
                if ("L7JFS".contains(pipe.type)) {
                    wonkyLines += pipe.type
                    if (wonkyLines.length == 2) {
                        if ("L7 FJ".contains(wonkyLines)) numOfWonkyLines++
                        wonkyLines = ""
                    }
                }
                inside = !inside
                continue
            }
            if (numOfWonkyLines % 2 == 1) {
                inside = !inside
                numOfWonkyLines = 0
            }
            if (inside) {
                count2++
            }
        }
    }

    return count2
}

data class Coordinate(val x: Int, val y: Int) {
    fun nextCoordinate(toDirection: Direction): Coordinate {
        return when (toDirection) {
            Direction.LEFT -> Coordinate(this.x - 1, this.y)
            Direction.RIGHT -> Coordinate(this.x + 1, this.y)
            Direction.ABOVE -> Coordinate(this.x, this.y - 1)
            Direction.BELOW -> Coordinate(this.x, this.y + 1)
        }
    }
}

class Pipe(var type: Char, private val position: Coordinate) {

    var loopEdge = false

    private fun nextDirection(from: Direction): Coordinate {
        val direction = when (this.type) {
            'J' -> if (from == Direction.ABOVE) Direction.LEFT else Direction.ABOVE
            'L' -> if (from == Direction.ABOVE) Direction.RIGHT else Direction.ABOVE
            'F' -> if (from == Direction.BELOW) Direction.RIGHT else Direction.BELOW
            '7' -> if (from == Direction.BELOW) Direction.LEFT else Direction.BELOW
            else -> from.opposite()
        }
        return this.position.nextCoordinate(direction)
    }

    fun getNextPipeCoordinate(prevPipe: Pipe): Coordinate {
        val thisX = this.position.x
        val thisY = this.position.y
        val prevX = prevPipe.position.x
        val prevY = prevPipe.position.y
        val fromDirection = if (thisX - prevX != 0) {
            if (thisX - prevX == 1) Direction.LEFT else Direction.RIGHT
        } else {
            if (thisY - prevY == 1) Direction.ABOVE else Direction.BELOW
        }
        return this.nextDirection(fromDirection)
    }

    fun getNext(pipeMap: MutableMap<Coordinate, Pipe>): Pipe {
        this.loopEdge = true
        val start = this.position

        val right = pipeMap.getValue(Coordinate(start.x + 1, start.y))
        val down = pipeMap.getValue(Coordinate(start.x, start.y + 1))
        val left = pipeMap.getValue(Coordinate(start.x - 1, start.y))

        return if ("J7-".contains(right.type)) right
        else if ("JL|".contains(down.type)) down
        else left
    }
}

enum class Direction {
    ABOVE, BELOW, LEFT, RIGHT;

    fun opposite(): Direction {
        return when (this) {
            ABOVE -> BELOW
            BELOW -> ABOVE
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
    }
}

