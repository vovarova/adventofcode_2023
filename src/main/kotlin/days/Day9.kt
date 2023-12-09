package days

import util.DayInput

class Day9 : Day("9") {

    override fun partOne(dayInput: DayInput): Any {
        val map = dayInput.inputList().map {
            val findDiffs = findDiff(it.split(" ").map {
                it.toInt()
            })

            val reversed = findDiffs.reversed()

            var currentValue = reversed.first().last()
            for (i in 1..reversed.size - 1) {
                currentValue = reversed[i].last() + currentValue
            }
            currentValue
        }
        return map.sum()
    }

    override fun partTwo(dayInput: DayInput): Any {
        val map = dayInput.inputList().map {
            val findDiffs = findDiff(it.split(" ").map {
                it.toInt()
            })

            val reversed = findDiffs.reversed()

            var currentValue = reversed.first().first()
            for (i in 1..reversed.size - 1) {
                currentValue = reversed[i].first() - currentValue
            }
            currentValue
        }
        return map.sum()
    }

    fun findDiff(list: List<Int>): List<List<Int>> {
        val resultDifs = mutableListOf<List<Int>>()
        var currentList = list
        while (!currentList.all { it == 0 }) {
            resultDifs.add(currentList)
            currentList = currentList.windowed(2).map { twoElements ->
                twoElements[1] - twoElements[0]
            }
        }
        return resultDifs
    }
}


fun main() {
    Day9().run()
}

