package days

import util.DAY_FILE
import util.DayInput


abstract class Day(private val dayNumber: String) {

    abstract fun partOne(dayInput: DayInput): Any

    abstract fun partTwo(dayInput: DayInput): Any

    fun partOne(): Any {
        return partOne(DayInput(day = dayNumber, DAY_FILE.INPUT))
    }


    fun partTwo(): Any {
        return partTwo(DayInput(day = dayNumber, DAY_FILE.INPUT))
    }

    fun run() {
        println("Example Part One: ${partOne(DayInput(day = dayNumber, DAY_FILE.EXAMPLE1))}")
        println("Real Part One: ${partOne(DayInput(day = dayNumber, DAY_FILE.INPUT))}")

        println("Example Part Two: ${partTwo(DayInput(day = dayNumber, DAY_FILE.EXAMPLE2))}")
        println("Real Part Two: ${partTwo(DayInput(day = dayNumber, DAY_FILE.INPUT))}")
    }
}
