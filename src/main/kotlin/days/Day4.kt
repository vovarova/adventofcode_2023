package days

import util.DayInput

class Day4 : Day("4") {

    class Card {
        var id: Int = 0
        val winningNumbers = mutableListOf<Int>()
        val number = mutableListOf<Int>()
    }

    fun cards(dayInput: DayInput): List<Card> {
        //Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        val cards = dayInput.inputList().map { line ->
            val split = line.split(": ", "|")

            Card().apply {
                id = split[0].replace(Regex("Card\\s*"), "").toInt()
                winningNumbers.addAll(split[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() })
                number.addAll(split[2].split(" ").filter { it.isNotEmpty() }.map { it.toInt() })
            }
        }
        return cards
    }

    override fun partOne(dayInput: DayInput): Any {

        return cards(dayInput).map {
            Math.pow(2.0, (it.winningNumbers.intersect(it.number).count() - 1).toDouble()).toInt()
        }.sum()
    }

    override fun partTwo(dayInput: DayInput): Any {
        val results: MutableMap<Int, Int> = mutableMapOf()
        cards(dayInput).map {
            results.putIfAbsent(it.id, 0)
            val times = results.compute(it.id) { k, v -> v!! + 1 }!!

            val count = it.winningNumbers.intersect(it.number).count()
            for (nextNumber in 1..count) {
                results.putIfAbsent(it.id + nextNumber, 0)
                results.compute(it.id + nextNumber) { k, v -> v!! + times }
            }
        }
        return results.map { it.value }.sum()

    }
}

fun main() {
    Day4().run()
}

