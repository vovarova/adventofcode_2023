package days

import util.DayInput
import java.util.stream.Collectors

class Day8 : Day("8") {

    override fun partOne(dayInput: DayInput): Any {
        val instructions = dayInput.inputList()[0].toCharArray()
        val map = dayInput.inputList().subList(2,dayInput.inputList().size).map {
            val split = it.split(" = ")
            val node = split[0]
            val path = split[1].replace(")", "").replace("(", "").split(", ")
            node to Pair(path[0], path[1])
        }.toMap()

        var current = "AAA"
        var instructionsCount = -1
        var steps = 0
        while (current!= "ZZZ") {
            instructionsCount++
            val instruction = instructions[instructionsCount%instructions.size]
            val pair = map[current]!!
            current = if (instruction == 'L') pair.first else pair.second
        }
        return instructionsCount+1
    }

    override fun partTwo(dayInput: DayInput): Any {
        return dayInput.inputList()
    }
}

fun main() {
    Day8().run()
}

