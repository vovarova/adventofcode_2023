package days

import util.DayInput

class Day2 : Day("2") {


    class Line {
        var game: Int = 0
        var sets: List<BallSet> = mutableListOf()
    }

    class BallSet {
        var red: Int = 0
        var blue: Int = 0
        var green: Int = 0
    }

    fun lines(input: List<String>): List<Line> {
        return input.map {
            val split = it.split(": ")
            Line().apply {
                game = split[0].replace("Game ", "").toInt()
                sets = split[1].split("; ").map {
                    BallSet().apply {
                        it.split(", ").forEach { setEelement ->
                            val elementsSplitted = setEelement.split(" ")
                            when (elementsSplitted[1]) {
                                "red" -> red = elementsSplitted[0].toInt()
                                "blue" -> blue = elementsSplitted[0].toInt()
                                "green" -> green = elementsSplitted[0].toInt()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun partOne(dayInput: DayInput): Any {
        val template = BallSet().apply {
            red = 12
            blue = 14
            green = 13
        }

        val lines = lines(dayInput.inputList())

        val filter = lines.filter {
            it.sets.all {
                it.red <= template.red && it.blue <= template.blue && it.green <= template.green
            }
        }


        return filter.map { it.game }.sum()
    }

    override fun partTwo(dayInput: DayInput): Any {
        val lines = lines(dayInput.inputList())

        return lines.map {
            it.sets.map { it.green }.max() * it.sets.map { it.blue }.max() * it.sets.map { it.red }.max()
        }.sum()

    }
}

fun main() {
    Day2().run()
}

