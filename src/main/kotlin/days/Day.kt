package days

import util.InputReader
import java.io.File


abstract class Day(val dayNumber: Int) {
    inner class Input(val useExample: Boolean, val input: InputPart) {
        fun getInputString(): String {
            return getInputFile().readText()
        }

        fun getInputList(): List<String> {
            return getInputFile().readLines()
        }

        fun getInputFile(): File {
            return getInputFile(input, useExample)
        }
    }

    enum class InputPart {
        PART1, PART2
    }

    fun getInputFile(input: InputPart, useExample: Boolean): File {
        if (!useExample) {
            return InputReader.file(dayNumber, InputReader.Input.TASK)
        }
        return when (input) {
            InputPart.PART1 -> InputReader.file(dayNumber, InputReader.Input.EXAMPLE1)
            InputPart.PART2 -> InputReader.file(dayNumber, InputReader.Input.EXAMPLE1)
        }
    }

    abstract fun partOne(input: Input): Any

    abstract fun partTwo(input: Input): Any

    fun partOne(): Any {
        return partOne(Input(useExample = false, InputPart.PART1))
    }


    fun partTwo(): Any {
        return partTwo(Input(useExample = false, InputPart.PART2))
    }

    fun run() {
        println("Example Part One: ${partOne(Input(useExample = true, InputPart.PART1))}")
        println("Real Part One: ${partOne(Input(useExample = false, InputPart.PART1))}")

        println("Example Part Two: ${partTwo(Input(useExample = true, InputPart.PART2))}")
        println("Real Part Two: ${partTwo(Input(useExample = false, InputPart.PART2))}")
    }
}
