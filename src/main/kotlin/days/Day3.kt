package days

import util.GridCell
import util.Matrix

class Day3 : Day(3) {

    override fun partOne(): Any {
        val matrix = Matrix(inputListPart1.map {
            it.toCharArray().toTypedArray()
        }.toTypedArray())

        val nonValidNumbers = mutableListOf<Int>()
        val validNumbers = mutableListOf<Int>()

        val numberParts = mutableListOf<GridCell<Char>>()

        fun check() {
            if (numberParts.isNotEmpty()) {

                val checkNumber = numberParts.any {
                    it.diagonalNeighbours().any {
                        it.value != '.' && !it.value.isDigit()
                    } || it.straightNeighbours().any {
                        it.value != '.' && !it.value.isDigit()
                    }
                }
                val number = numberParts.map { it.value }.joinToString("").toInt()
                if (!checkNumber) {
                    nonValidNumbers.add(number)
                } else {
                    validNumbers.add(number)
                }
                numberParts.clear()
            }

        }

        for (row in matrix.matrixGrid.indices) {
            for (column in matrix.matrixGrid[row].indices) {
                if (matrix.cell(row, column).value.isDigit()) {
                    numberParts.add(matrix.cell(row, column))
                } else {
                    check()
                }
            }
            check()
        }

        return validNumbers.sum()
    }

    override fun partTwo(): Any {
        val matrix = Matrix(inputListPart2.map {
            it.toCharArray().toTypedArray()
        }.toTypedArray())

        val starCells = mutableMapOf<GridCell<Char>, MutableList<Int>>()
        val numberParts = mutableListOf<GridCell<Char>>()

        fun check() {
            if (numberParts.isNotEmpty()) {
                val number = numberParts.map { it.value }.joinToString("").toInt()
                val flatMap = numberParts.flatMap {
                    mutableListOf<GridCell<Char>>().also { elements ->
                        elements.addAll(it.straightNeighbours())
                        elements.addAll(it.diagonalNeighbours())
                    }
                }.distinct().filter { it.value == '*' }.forEach {
                    starCells.putIfAbsent(it, mutableListOf())
                    starCells[it]?.add(number)
                }

                numberParts.clear()
            }

        }

        for (row in matrix.matrixGrid.indices) {
            for (column in matrix.matrixGrid[row].indices) {
                if (matrix.cell(row, column).value.isDigit()) {
                    numberParts.add(matrix.cell(row, column))
                } else {
                    check()
                }
            }
            check()
        }

        return starCells.filter { it.value.size>1 }.map { it.value.reduce { acc, i -> acc * i } }.sum()
    }
}

fun main() {

    val example = Day3().also {
        it.example = true
    }
    println("Example Part One: ${example.partOne()}")
    println("Example Part Two: ${example.partTwo()}")

    val real = Day3().also {
        it.example = false
    }

    println("Real Part One: ${real.partOne()}")
    println("Real Part Two: ${real.partTwo()}")
}

