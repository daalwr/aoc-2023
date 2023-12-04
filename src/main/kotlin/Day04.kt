private fun numberOfMatches(line: String): Int {
    val parsedLine = line.split(Regex("[:|]"))
    val winningNumbers = extractNumbers(parsedLine[1])
    val cardNumbers = extractNumbers(parsedLine[2])
    return winningNumbers.intersect(cardNumbers).size
}

private fun extractNumbers(parsedLine: String) =
    Regex("""\d+""").findAll(parsedLine).map { it.value.toInt() }.toSet()

fun power(x: Int, n: Int): Int {
    if (n == 0) return 1
    if (n == 1) return x
    return x * power(x, n - 1)
}

private fun part1(input: List<String>): Int =
    input.sumOf { line ->
        val matches = numberOfMatches(line)
        if (matches > 0) {
            power(2, matches - 1)
        } else {
            0
        }
    }

private fun part2(input: List<String>): Int {
    val scratchCards = IntArray(input.size)
    scratchCards.fill(1)
    for (i in input.indices) {
        repeat(numberOfMatches(input[i])) {
            scratchCards[i + 1 + it] = scratchCards[i + 1 + it] + scratchCards[i]
        }
    }
    return scratchCards.sum()
}

fun main() {
    val input = loadFile("./src/main/kotlin/Day04.txt")
    println(part1(input))
    println(part2(input))
}
