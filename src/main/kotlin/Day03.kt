import kotlin.math.max
import kotlin.math.min

private fun hasSymbol(input: String): Boolean {
    return input.any { it != '.' }
}

private fun findGears(input: String, startIndex: Int, row: Int): List<Pair<Int, Int>> {
    return input
        .mapIndexed { index, c -> if (c == '*') Pair(row, index + startIndex) else null }
        .filterNotNull()
}

private fun hasAdjacentSymbol(input: List<String>, row: Int, line: String, range: IntRange): Boolean {
    val above = row != 0
    val below = row != input.size - 1

    val left = range.first != 0
    val right = range.last != line.length - 1

    if (above) {
        val aboveLine =
            input[row - 1].subSequence(max(0, range.first - 1), min(range.last + 2, line.length)).toString()

        if (hasSymbol(aboveLine)) {
            return true
        }
    }

    if (below) {
        val belowLine =
            input[row + 1].subSequence(max(0, range.first - 1), min(range.last + 2, line.length)).toString()
        if (hasSymbol(belowLine)) {
            return true
        }
    }

    if (left) {
        val leftChar = line[range.first - 1]
        if (leftChar != '.') {
            return true
        }
    }

    if (right) {
        val rightChar = line[range.last + 1]
        if (rightChar != '.') {
            return true
        }
    }
    return false
}

val gearMap = mutableMapOf<Pair<Int, Int>, List<Int>>()

private fun scanAdjacentGears(input: List<String>, row: Int, line: String, range: IntRange, value: Int) {
    val above = row != 0
    val below = row != input.size - 1
    val left = range.first != 0
    val right = range.last != line.length - 1

    if (above) {
        val aboveLineStart = max(0, range.first - 1)
        val aboveLineEnd = min(range.last + 2, line.length)
        val aboveLine = input[row - 1].subSequence(aboveLineStart, aboveLineEnd).toString()

        findGears(aboveLine, aboveLineStart, row - 1)
            .forEach {
                gearMap[it] = gearMap.getOrDefault(it, listOf()) + listOf(value)
            }
    }

    if (below) {
        val belowLineStart = max(0, range.first - 1)
        val belowLineEnd = min(range.last + 2, line.length)
        val belowLine = input[row + 1].subSequence(belowLineStart, belowLineEnd).toString()

        findGears(belowLine, belowLineStart, row + 1)
            .forEach {
                gearMap[it] = gearMap.getOrDefault(it, listOf()) + listOf(value)
            }
    }

    if (left) {
        val leftChar = line[range.first - 1]
        if (leftChar != '.') {
            gearMap[Pair(row, range.first - 1)] =
                gearMap.getOrDefault(Pair(row, range.first - 1), listOf()) + listOf(value)
        }
    }

    if (right) {
        val rightChar = line[range.last + 1]
        if (rightChar != '.') {
            gearMap[Pair(row, range.last + 1)] =
                gearMap.getOrDefault(Pair(row, range.last + 1), listOf()) + listOf(value)
        }
    }
}

private fun part1(input: List<String>): Int {
    return input.mapIndexed { row, line ->
        Regex("""\d+""")
            .findAll(line)
            .sumOf {
                val range = it.range
                val value = it.value.toInt()

                if (hasAdjacentSymbol(input, row, line, range)) {
                    value
                } else {
                    0
                }
            }
    }.sum()
}

private fun part2(input: List<String>): Int {
    input.mapIndexed { row, line ->
        Regex("""\d+""")
            .findAll(line)
            .forEach {
                val range = it.range
                val value = it.value.toInt()
                scanAdjacentGears(input, row, line, range, value)
            }
    }

    return gearMap.map { it.value }.sumOf {
        if (it.size == 2) {
            it[0] * it[1]
        } else {
            0
        }
    }
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day03.txt")
    println(part1(input))
    println(part2(input))
}
