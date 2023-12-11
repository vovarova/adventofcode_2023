package days

import util.DAY_FILE
import util.DayInput
import util.GridCell
import util.Matrix
import java.util.*

class Day10 : Day("10") {

    /*
        | is a vertical pipe connecting north and south.
        - is a horizontal pipe connecting east and west.
        L is a 90-degree bend connecting north and east.
        J is a 90-degree bend connecting north and west.
        7 is a 90-degree bend connecting south and west.
        F is a 90-degree bend connecting south and east.
        . is ground; there is no pipe in this tile.
        S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
    */

    enum class Item(val value: Char, val direction1: Direction?, val direction2: Direction?) {
        VERTICAL('|', Direction.NORTH, Direction.SOUTH), HORIZONTAL(
            '-',
            Direction.EAST,
            Direction.WEST
        ),
        NORTH_EAST('L', Direction.NORTH, Direction.EAST), NORTH_WEST('J', Direction.NORTH, Direction.WEST), SOUTH_WEST(
            '7',
            Direction.SOUTH,
            Direction.WEST
        ),
        SOUTH_EAST('F', Direction.SOUTH, Direction.EAST), GROUND('.', null, null), START('S', null, null);

        fun directionFrom(direction: Direction): Direction? {
            return when (direction) {
                direction1 -> direction2
                direction2 -> direction1
                else -> null
            }
        }


        companion object {
            fun fromChar(char: Char): Item = entries.first { it.value == char }
        }
    }

    enum class Direction() {
        NORTH, SOUTH, EAST, WEST;

        fun opposite(): Direction {
            return when (this) {
                NORTH -> SOUTH
                SOUTH -> NORTH
                EAST -> WEST
                WEST -> EAST
            }
        }

        fun right(): Direction {
            return when (this) {
                NORTH -> EAST
                SOUTH -> WEST
                EAST -> SOUTH
                WEST -> NORTH
            }
        }

        fun left(): Direction {
            return right().opposite()
        }
    }


    fun cellDirection(cell: GridCell<Item>, direction: Direction): Pair<Direction, GridCell<Item>> {
        val gridCell = when (direction) {
            Direction.NORTH -> cell.up()
            Direction.SOUTH -> cell.down()
            Direction.EAST -> cell.right()
            Direction.WEST -> cell.left()
        }
        return direction to gridCell
    }

    override fun partOne(dayInput: DayInput): Any {
        val map = dayInput.inputList().map {
            it.toCharArray().map { Item.fromChar(it) }.toTypedArray()
        }.toTypedArray()
        val matrix = Matrix<Item>(map)
        val startCell = matrix.find { it.value == Item.START }!!
        val first = listOf(
            cellDirection(startCell, Direction.NORTH),
            cellDirection(startCell, Direction.SOUTH),
            cellDirection(startCell, Direction.EAST),
            cellDirection(startCell, Direction.WEST)
        ).filter { it.second.valid() }.filter { it.second.value != Item.GROUND }.first()
        val goWithPath = goWithPath(first)
        return (goWithPath.size / 2) + (goWithPath.size % 2)
    }

    fun goWithPath(start: Pair<Direction, GridCell<Item>>): MutableList<Pair<Direction, GridCell<Item>>> {
        val result: MutableList<Pair<Direction, GridCell<Item>>> = mutableListOf()
        var count = 0
        var current = start
        while (current.second.value != Item.START) {
            count++
            result.add(current)
            val newDirection = current.second.value.directionFrom(current.first.opposite())!!
            current = cellDirection(current.second, newDirection)
        }
        return result
    }

    fun straightCellDirections(cell: GridCell<Item>): List<Pair<Direction, GridCell<Item>>> {
        return listOf(
            cellDirection(cell, Direction.NORTH),
            cellDirection(cell, Direction.SOUTH),
            cellDirection(cell, Direction.EAST),
            cellDirection(cell, Direction.WEST)
        )
    }

    override fun partTwo(dayInput: DayInput): Any {
        val map = dayInput.inputList().map {
            it.toCharArray().map { Item.fromChar(it) }.toTypedArray()
        }.toTypedArray()
        val matrix = Matrix<Item>(map)
        val startCell = matrix.find { it.value == Item.START }!!
        val first =
            straightCellDirections(startCell).filter { it.second.valid() }.filter { it.second.value != Item.GROUND }
                .first()


        val goWithPath = goWithPath(first)
        val mainTiles = goWithPath + listOf(goWithPath.last().first to startCell)
        val mainTilesSet = mainTiles.map { it.second }.toSet()


        val startFrom = (matrix.lastRow() + matrix.firstRow() + matrix.lastColumn() + matrix.firstColumn()).filterNot {
            mainTilesSet.contains(it)
        }.distinct()
        val visited =
            visit(mainTiles, startFrom)

        val right =false/* mainTiles.map { it to straightCellDirections(it.second).find { pair -> visited.contains(pair.second) } }
                .filter { it.second != null }.map { it.first.first.right() == it.second!!.first }.first()*/




        val hiddentAreas = mainTiles.map {
            val newDirection = if (right) it.first.right() else it.first.left()
            cellDirection(it.second, newDirection)
        }.filter { it.second.valid() }.filterNot { visited.contains(it.second) }.filterNot { mainTilesSet.contains(it.second) }

        val hiddenValues = hiddentAreas.map { it.second }.distinct()

        val gridCells = visit(mainTiles, hiddenValues) + visited

        return matrix.count()-gridCells.size-mainTilesSet.size
    }

    fun visit(
        mainTiles: List<Pair<Direction, GridCell<Item>>>, startFrom: List<GridCell<Item>>
    ): Set<GridCell<Item>> {
        val mainTilesSet = mainTiles.map { it.second }.toSet()
        val visited: MutableSet<GridCell<Item>> = mutableSetOf()
        val linkedList = LinkedList<GridCell<Item>>()
        linkedList.addAll(startFrom)
        while (linkedList.isNotEmpty()) {
            val current = linkedList.removeFirst()
            if (current.valid()&& (visited.contains(current) || mainTilesSet.contains(current))) {
                continue
            }
            visited.add(current)
            linkedList.addAll(current.straightNeighbours())
        }
        return visited
    }
}

fun main() {
    println(Day10().partTwo(DayInput(day = "10", DAY_FILE.INPUT)))
}

