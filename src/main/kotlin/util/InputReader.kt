package util

import java.io.File

object InputReader {

    fun getInputAsString(day: String): String {
        return fromResources(day).readText()
    }

    fun getInputAsList(day: String): List<String> {
        return fromResources(day).readLines()
    }

    private fun fromResources(day: String): File {
        return File(javaClass.classLoader.getResource("input_day_$day.txt").toURI())
    }
}
