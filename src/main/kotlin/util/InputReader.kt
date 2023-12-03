package util

import java.io.File

object InputReader {

    enum class Input(val fileName: String) {
        TASK("input.txt"), EXAMPLE1("ex_1.txt"), EXAMPLE2("ex_2.txt")
    }

    fun getInputAsString(day: Int, input: Input): String {
        return file(day, input).readText()
    }

    fun getInputAsList(day: Int, input: Input): List<String> {
        return file(day, input).readLines()
    }

    fun file(day: Int, input: Input): File {
        return File(javaClass.classLoader.getResource("day${day}/${input.fileName}").toURI())
    }
}
