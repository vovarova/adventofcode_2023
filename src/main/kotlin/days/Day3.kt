package days

import util.GridCell
import util.Matrix

class Day3 : Day(3) {

    private fun gridNumbers(input: List<String>): MutableList<Pair<Int, List<GridCell<Char>>>> {
        val matrix = Matrix(input.map {
            it.toCharArray().toTypedArray()
        }.toTypedArray())
        val gridNumbers = mutableListOf<Pair<Int, List<GridCell<Char>>>>()
        val numberParts = mutableListOf<GridCell<Char>>()
        for (row in matrix.matrixGrid.indices) {
            for (column in matrix.matrixGrid[row].indices) {
                if (matrix.cell(row, column).value.isDigit()) {
                    numberParts.add(matrix.cell(row, column))
                } else {
                    if (numberParts.isNotEmpty()) {
                        gridNumbers.add(
                            Pair(
                                numberParts.map { it.value }.joinToString("").toInt(),
                                listOf(*numberParts.toTypedArray())
                            )
                        )
                        numberParts.clear()
                    }
                }
            }
            if (numberParts.isNotEmpty()) {
                gridNumbers.add(
                    Pair(
                        numberParts.map { it.value }.joinToString("").toInt(),
                        listOf(*numberParts.toTypedArray())
                    )
                )
                numberParts.clear()
            }
        }
        return gridNumbers;
    }

    override fun partOne(input: Input): Any {
        val validNumbers = gridNumbers(input.getInputList()).filter {
            it.second.any {
                it.diagonalNeighbours().any {
                    it.value != '.' && !it.value.isDigit()
                } || it.straightNeighbours().any {
                    it.value != '.' && !it.value.isDigit()
                }
            }
        }

        return validNumbers.map { it.first }.sum()
    }

    override fun partTwo(input: Input): Any {
        return gridNumbers(input.getInputList()).flatMap {
            it.second.flatMap {
                mutableListOf<GridCell<Char>>().also { elements ->
                    elements.addAll(it.straightNeighbours())
                    elements.addAll(it.diagonalNeighbours())
                }
            }.distinct().filter { it.value == '*' }.map { start ->
                Pair(start, it.first)
            }
        }.groupBy { it.first }.filter { it.value.size > 1 }.map {
            it.value.map { it.second }.reduce { acc, i -> acc * i }
        }.sum()
    }
}

fun main() {
    Day3().run()
}

