package days

class Day1 : Day(1) {

    override fun partOne(): Any {
        val digits = inputListPart1.map { line ->
            line.toCharArray().filter { it.isDigit() }.map { it.digitToInt() }
        }

        return digits.map { 10 * it.first() + it.last() }.sum()

    }

    override fun partTwo(): Any {
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

        val digits = inputListPart2.map { line ->
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

    val example = Day1().also { day1 ->
        day1.example = true
    }
    println("Example Part One: ${example.partOne()}")
    println("Example Part Two: ${example.partTwo()}")

    val real = Day1().also { day1 ->
        day1.example = false
    }

    println("Real Part One: ${real.partOne()}")
    println("Real Part Two: ${real.partTwo()}")
}

