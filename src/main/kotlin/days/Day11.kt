package days

import util.DayInput
import util.GridCell
import util.Matrix
import java.util.*

class Day11 : Day("11") {

    override fun partOne(dayInput: DayInput): Any {
        return calculateDustanceSum(dayInput, 2)
    }

    fun distance(star1: GridCell<Char>, star2: GridCell<Char>): Int {
        return Math.abs(star1.cell.column - star2.cell.column) + Math.abs(star1.cell.row - star2.cell.row)
    }

    override fun partTwo(dayInput: DayInput): Any {
        return calculateDustanceSum(dayInput, 1000000)
    }

    private fun calculateDustanceSum(dayInput: DayInput, emptyRowDistance: Int): Long {
        val inputList = LinkedList(dayInput.inputList().map { LinkedList(it.toList()) }.toList())

        val matrix = Matrix.fromList(inputList)

        val emptyRows = matrix.rows().mapIndexed { index, list ->
            index to list.all { it.value == '.' }
        }.filter { it.second }.map { it.first }

        val emptyColumns = matrix.columns().mapIndexed { index, list ->
            index to list.all { it.value == '.' }
        }.filter { it.second }.map { it.first }

        val stars = matrix.filter { it.value == '#' }

        val starsToCompare =
            stars.flatMapIndexed { index, value -> stars.subList(index + 1, stars.size).map { value to it } }


        return starsToCompare.map {
            val emptyRow = emptyRows.filter { row ->
                row < Math.max(it.first.cell.row, it.second.cell.row) &&
                        row > Math.min(it.first.cell.row, it.second.cell.row)
            }.count()
            val emptyColumn = emptyColumns.filter { row ->
                row < Math.max(it.first.cell.column, it.second.cell.column) &&
                        row > Math.min(it.first.cell.column, it.second.cell.column)
            }.count()
            (emptyRowDistance - 1).toLong() * emptyColumn.toLong() + (emptyRowDistance - 1).toLong() * emptyRow.toLong() + distance(
                it.first,
                it.second
            ).toLong()
        }.sum()
    }
}

fun main() {
    Day11().run()
}

