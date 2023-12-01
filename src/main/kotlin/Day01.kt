private fun part1(input: List<String>) =
    input.sumOf { line ->
        val digits = line.filter { it.isDigit() }
        "${digits.first()}${digits.last()}".toInt()
    }

private fun String.wordToInt(): Int =
    when (this) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> this.toInt()
    }

val regexString = "one|two|three|four|five|six|seven|eight|nine|[1-9]"

private fun part2(input: List<String>): Int {
    return input.sumOf { line ->
        val firstNumber = Regex(regexString).find(line)!!.value.wordToInt()
        val lastNumber = Regex(".*($regexString)").find(line)!!.groupValues[1].wordToInt()
        "$firstNumber$lastNumber".toInt()
    }
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day01.txt")
    println(part1(input))
    println(part2(input))
}
