import kotlin.math.max

private fun part1(input: List<String>): Int {
    val redMax = 12
    val greenMax = 13
    val blueMax = 14

    return input.mapIndexed { index, line ->
        val gameId = index + 1
        val sets = line.split(";")
        val possible = sets.flatMap { set ->
            Regex("""(\d+) ([a-z]+)""").findAll(set).map { match ->
                val (count, color) = match.destructured
                when (color) {
                    "red" -> count.toInt() <= redMax
                    "green" -> count.toInt() <= greenMax
                    "blue" -> count.toInt() <= blueMax
                    else -> throw Exception("Unknown color $color")
                }
            }
        }.all { it }

        if (possible) {
            gameId
        } else {
            0
        }
    }.sum()
}

private fun part2(input: List<String>): Int {
    return input.sumOf { line ->
        var red = 0
        var green = 0
        var blue = 0
        val sets = line.split(";")
        sets.forEach { set ->
            Regex("""(\d+) ([a-z]+)""").findAll(set).forEach { match ->
                val (count, color) = match.destructured
                when (color) {
                    "red" -> red = max(red, count.toInt())
                    "green" -> green = max(green, count.toInt())
                    "blue" -> blue = max(blue, count.toInt())
                    else -> throw Exception("Unknown color $color")
                }
            }
        }
        red * green * blue
    }
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day02.txt")
    println(part1(input))
    println(part2(input))
}
