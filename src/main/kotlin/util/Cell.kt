package util

import java.util.stream.IntStream


data class Cell(val row: Int, val column: Int) {
    fun down(): Cell = Cell(row + 1, column)
    fun up(): Cell = Cell(row - 1, column)
    fun left(): Cell = Cell(row, column - 1)
    fun right(): Cell = Cell(row, column + 1)
    fun toPair() = Pair(row, column)
}


interface GridConfig<T> {
    fun valid(cell: Cell): Boolean
    fun value(cell: Cell): T
    fun addValue(cell: Cell, value: T)
}

data class GridCell<T>(private val cell: Cell, private val config: GridConfig<T>) {
    var value
        get() = config.value(cell)
        set(value) = config.addValue(cell, value)

    fun up(): GridCell<T> = GridCell(cell.up(), config)
    fun down(): GridCell<T> = GridCell(cell.down(), config)
    fun left(): GridCell<T> = GridCell(cell.left(), config)
    fun right(): GridCell<T> = GridCell(cell.right(), config)
    fun valid(): Boolean = config.valid(cell)

    fun straightNeighbours(): List<GridCell<T>> = listOf(up(), down(), left(), right()).filter { it.valid() }
    fun diagonalNeighbours(): List<GridCell<T>> =
        listOf(up().left(), up().right(), down().right(), down().left()).filter { it.valid() }


    fun neighbours(): List<GridCell<T>> = straightNeighbours() + diagonalNeighbours()


    override fun toString(): String {
        return "(row:${cell.row},column:${cell.column},value:${value})"
    }
}

class Matrix<T>(val matrixGrid: Array<Array<T>>) : Iterable<GridCell<T>> {

    inner class Config : GridConfig<T> {
        override fun valid(cell: Cell): Boolean {
            return cell.row >= 0
                    && cell.row < matrixGrid.size
                    && cell.column >= 0
                    && cell.column < matrixGrid[0].size
        }

        override fun value(cell: Cell): T {
            return matrixGrid[cell.row][cell.column]
        }

        override fun addValue(cell: Cell, value: T) {
            matrixGrid[cell.row][cell.column] = value
        }
    }

    private val config = Config()
    fun cell(row: Int, column: Int): GridCell<T> = GridCell(Cell(row, column), config)
    override fun iterator(): Iterator<GridCell<T>> {
        return IntStream.range(0, matrixGrid.size).mapToObj { i -> i }.flatMap { row ->
            IntStream.range(0, matrixGrid[0].size).mapToObj { column ->
                GridCell(Cell(row, column), config)
            }
        }.iterator()
    }

    fun column(column: Int): List<GridCell<T>> {
        return IntStream.range(0, matrixGrid.size).mapToObj { i -> i }.map { row ->
            GridCell(Cell(row, column), config)
        }.toList()
    }


    fun firstRow(): List<GridCell<T>> {
        return row(0)
    }

    fun lastRow(): List<GridCell<T>> {
        return row(matrixGrid.size - 1)
    }

    fun firstColumn(): List<GridCell<T>> {
        return column(0)
    }

    fun lastColumn(): List<GridCell<T>> {
        return column(matrixGrid[0].size - 1)
    }

    fun row(row: Int): List<GridCell<T>> {
        return IntStream.range(0, matrixGrid[0].size).mapToObj { i -> i }.map { column ->
            GridCell(Cell(row, column), config)
        }.toList()
    }

    companion object {
        inline fun <reified T> fromList(value: List<List<T>>): Matrix<T> {
            return Matrix(value.map { it.toTypedArray() }.toTypedArray())
        }
    }

}