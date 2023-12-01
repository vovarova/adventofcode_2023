package days

class Day1 : Day(1) {

    override fun partOne(): Any {
        val digits = inputList.map { line ->
            line.toCharArray().filter { it.isDigit() }.map { it.digitToInt() }
        }
        return digits.map { 10 * it.first() + it.last() }.sum()

    }

    override fun partTwo(): Any {
        return inputString.split("\n")
            .filterNot { it.isEmpty() }
            .map { it.uppercase() }
            .last()
    }
}

fun main() {
    val partOne = Day1().partOne()
    println("Part One: $partOne")

    val partTwo = Day1().partTwo()
    println("Part One: $partTwo")
}
