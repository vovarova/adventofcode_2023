package days

import util.DayInput

class Day1 : Day("1") {

    override fun partOne(dayInput: DayInput): Any {
        val digits = dayInput.inputList().map { line ->
            line.toCharArray().filter { it.isDigit() }.map { it.digitToInt() }
        }
        return digits.map { 10 * it.first() + it.last() }.sum()
    }

    override fun partTwo(dayInput: DayInput): Any {
        val replacement = listOf(
            Pair("one", 1),
            Pair("two", 2),
            Pair("three", 3),
            Pair("four", 4),
            Pair("five", 5),
            Pair("six", 6),
            Pair("seven", 7),
            Pair("eight", 8),
            Pair("nine", 9),
            Pair("1", 1),
            Pair("2", 2),
            Pair("3", 3),
            Pair("4", 4),
            Pair("5", 5),
            Pair("6", 6),
            Pair("7", 7),
            Pair("8", 8),
            Pair("9", 9),
        )

        val digits = dayInput.inputList().map { line ->
            val map = replacement.flatMap {
                val mutableListOf = mutableListOf<Pair<Int, Pair<String, Int>>>()
                var prevIndex = line.indexOf(it.first, 0)
                mutableListOf.add(Pair(prevIndex, it))
                while (prevIndex != -1) {
                    val index = line.indexOf(it.first, prevIndex + 1)
                    if (index != -1) {
                        mutableListOf.add(Pair(index, it))
                    }
                    prevIndex = index
                }
                mutableListOf
            }.filter { it.first != -1 }.sortedBy { it.first }.map { it.second.second }

            map
        }
        return digits.map { 10 * it.first() + it.last() }.sum()
    }
}

fun main() {
    Day1().run()
}

