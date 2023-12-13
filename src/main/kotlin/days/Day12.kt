package days

import util.DAY_FILE
import util.DayInput

class Day12 : Day("12") {

    class Line(val symbols: List<Char>, val numbers: List<Int>) {
        fun validSimple(resultIndexes: List<Int>): Boolean {
            return if (resultIndexes.isEmpty()) {
                true
            } else {
                val lastNumber = numbers[resultIndexes.size - 1]
                IntRange(0, resultIndexes.last() + lastNumber - 1).filter {
                    symbols[it] == '#'
                }.count() == numbers.subList(0, resultIndexes.size).sum()
                        && resultIndexes.last() + lastNumber + numbers.subList(resultIndexes.size, numbers.size)
                    .sum() <=symbols.size

            }
        }

        fun valid(): Boolean {
            return numbers.sum() == symbols.count { it == '#' }
        }
    }

    override fun partOne(dayInput: DayInput): Any {
        val lines = dayInput.inputList().map { it.split(" ") }.map {
            Line(it[0].toList(), it[1].split(",").map { it.toInt() })
        }


        val map = lines.map {
            it to findArrangmentsRecirsively(it)
        }
        return map.map { it.second.size }.sum()
    }


    fun constructLine(line: Line, resultIndexes: List<Int>): Line {
        val mutableListOf = mutableListOf(*line.symbols.toTypedArray())
        resultIndexes.mapIndexed { index, value -> IntRange(value, value + line.numbers[index] - 1) }.flatten()
            .map { mutableListOf[it] = '#' }
        return Line(mutableListOf, line.numbers)
    }

    fun findArrangmentsRecirsively(
        line: Line,
        numbers: List<Int> = line.numbers,
        startFrom: Int = 0,
        resultIndexes: List<Int> = listOf()
    ): List<Line> {
        if (numbers.isEmpty()) {
            val constructLine = constructLine(line, resultIndexes)
            return if (constructLine.valid()) {
                listOf(constructLine)
            } else {
                println("Not valid lines ${constructLine.symbols}, ${constructLine.numbers}")
                listOf()
            }
        }
        val constructLine = constructLine(line, resultIndexes)
        if (!constructLine.validSimple(resultIndexes)) {
            //println("Not valid lines ${constructLine.symbols}, ${constructLine.numbers}")
            return listOf()
        }

        val number = numbers.first()
        return line.symbols.windowed(number, 1).mapIndexed { index, value ->
            index to value.all { it == '?' || it == '#' }
        }.filter { it.second }.filter { it.first >= startFrom }.map { it.first }
            .map {
                findArrangmentsRecirsively(
                    line,
                    numbers.subList(1, numbers.size),
                    it + number + 1,
                    resultIndexes + listOf(it)
                )
            }.flatten()

    }

    override fun partTwo(dayInput: DayInput): Any {
        val lines = dayInput.inputList().map { it.split(" ") }.map {
            Line(it[0].toList(), it[1].split(",").map { it.toInt() })
        }.map {
            Line(
                it.symbols + listOf('?') + it.symbols + listOf('?') + it.symbols + listOf('?') + it.symbols + listOf('?') + it.symbols,
                it.numbers + it.numbers + it.numbers + it.numbers + it.numbers
            )
        }


        val map = lines.subList(2,5).mapIndexed { index, line ->
            println("Line ${index}")
            line to findArrangmentsRecirsively(line)
        }
        return map.map { it.second.size }.sum()
    }
}

fun main() {
    println(Day12().partTwo(DayInput("12", DAY_FILE.INPUT)))
}

