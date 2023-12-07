package days

import util.DayInput

class Day7 : Day("7") {


    class HandBet(val hand: Hand, val bet: Int)

    class Hand(val handString: String)

    class HandRulesPart1(val hand: Hand) {

        val map = hand.handString.groupingBy { it }.eachCount()
        val cardRate = mapOf(
            Pair('2', 2),
            Pair('3', 3),
            Pair('4', 4),
            Pair('5', 5),
            Pair('6', 6),
            Pair('7', 7),
            Pair('8', 8),
            Pair('9', 9),
            Pair('T', 10),
            Pair('J', 11),
            Pair('Q', 12),
            Pair('K', 13),
            Pair('A', 14)
        )

        fun fiveOfKind(): Int? {
            if (map.size == 1) {
                return 9
            }
            return null
        }

        fun fourOfKind(): Int? {
            if (map.size == 2 && map.any { it.value == 4 }) {
                return 8
            }
            return null
        }

        fun fullHouse(): Int? {
            if (map.size == 2 && map.any { it.value == 3 }) {
                return 7
            }
            return null
        }

        fun threeOfKind(): Int? {
            if (map.any { it.value == 3 }) {
                return 6
            }
            return null
        }

        fun twoPairs(): Int? {
            if (map.filter { it.value == 2 }.count() == 2) {
                return 5
            }
            return null
        }

        fun onePair(): Int? {
            if (map.filter { it.value == 2 }.count() == 1) {
                return 4
            }
            return null
        }

        fun highCard(): Int? {
            return 1
        }


        fun rate(): String {
            val combination =
                listOf(
                    fiveOfKind(),
                    fourOfKind(),
                    fullHouse(),
                    threeOfKind(),
                    twoPairs(),
                    onePair(),
                    highCard()
                ).filterNotNull()
                    .first()
            return combination.toString() + hand.handString.map {
                Char(('A'.code - 1) + cardRate[it]!!)
            }.joinToString("")
        }

    }

    class HandRulesPart2(val hand: Hand) {

        val map = hand.handString.groupingBy { it }.eachCount()

        val mapWithoutJokers = map.filter { it.key != 'J' }

        val jokers = map['J'] ?: 0
        val cardRate = mapOf(
            Pair('2', 2),
            Pair('3', 3),
            Pair('4', 4),
            Pair('5', 5),
            Pair('6', 6),
            Pair('7', 7),
            Pair('8', 8),
            Pair('9', 9),
            Pair('T', 10),
            Pair('J', 1),
            Pair('Q', 12),
            Pair('K', 13),
            Pair('A', 14)
        )

        fun fiveOfKind(): Int? {
            if (map.size == 1 || mapWithoutJokers.any { it.value == 5 - jokers }) {
                return 9
            }
            return null
        }

        fun fourOfKind(): Int? {
            if (mapWithoutJokers.any { it.value == 4 - jokers }) {
                return 8
            }
            return null
        }

        fun fullHouse(): Int? {
            if (jokers == 0 && map.size == 2 && map.any { it.value == 3 } ||
                jokers == 1 && mapWithoutJokers.filter { it.value == 2 }.count() == 2
            ) {
                return 7
            }
            return null
        }

        fun threeOfKind(): Int? {
            if (map.any { it.value == 3 - jokers }) {
                return 6
            }
            return null
        }

        fun twoPairs(): Int? {
            if (map.filter { it.value == 2 }.count() == 2) {
                return 5
            }
            return null
        }

        fun onePair(): Int? {
            if (map.filter { it.value == 2 - jokers }.count() > 0) {
                return 4
            }
            return null
        }

        fun highCard(): Int? {
            return 1
        }


        fun rate(): String {
            val combination =
                listOf(
                    fiveOfKind(),
                    fourOfKind(),
                    fullHouse(),
                    threeOfKind(),
                    twoPairs(),
                    onePair(),
                    highCard()
                ).filterNotNull()
                    .first()
            return combination.toString() + hand.handString.map {
                Char(('A'.code - 1) + cardRate[it]!!)
            }.joinToString("")
        }
    }
    override fun partOne(dayInput: DayInput): Any {

        val sortedBy = dayInput.inputList().map {
            val split = it.split(" ")
            HandBet(Hand(split[0]), split[1].toInt())
        }.map {
            Pair(it, HandRulesPart1(it.hand).rate())
        }.sortedBy { it.second }


        return sortedBy.mapIndexed { index, pair ->
            (index + 1) * pair.first.bet
        }.sum()
    }

    override fun partTwo(dayInput: DayInput): Any {
        val sortedBy = dayInput.inputList().map {
            val split = it.split(" ")
            HandBet(Hand(split[0]), split[1].toInt())
        }.map {
            Pair(it, HandRulesPart2(it.hand).rate())
        }.sortedBy { it.second }


        return sortedBy.mapIndexed { index, pair ->
            (index + 1) * pair.first.bet
        }.sum()
    }
}

fun main() {
    Day7().run()

}

