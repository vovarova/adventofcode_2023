package util

import java.io.File

enum class DAY_FILE(val fileName: String) {
    INPUT("input.txt"), EXAMPLE1("ex_1.txt"), EXAMPLE2("ex_2.txt")
}


class DayInput(val day: String, val dayFile: DAY_FILE) {
    fun inputString(): String {
        return inputFile().readText()
    }

    fun inputList(): List<String> {
        return inputFile().readLines()
    }

    fun inputFile(): File {
        return File(javaClass.classLoader.getResource("day${day}/${dayFile.fileName}").toURI())
    }

}