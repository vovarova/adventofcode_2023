package days

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
        VERTICAL('|', Direction.NORTH, Direction.SOUTH),
        HORIZONTAL('-', Direction.EAST, Direction.WEST),
        NORTH_EAST('L', Direction.NORTH, Direction.EAST),
        NORTH_WEST('J', Direction.NORTH, Direction.WEST),
        SOUTH_WEST('7', Direction.SOUTH, Direction.WEST),
        SOUTH_EAST('F', Direction.SOUTH, Direction.EAST),
        GROUND('.', null, null),
        START('S', null, null);

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
        NORTH,
        SOUTH,
        EAST,
        WEST;

        fun opposite(): Direction {
            return when (this) {
                NORTH -> SOUTH
                SOUTH -> NORTH
                EAST -> WEST
                WEST -> EAST
            }
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

    fun goWithPath(start: Pair<Direction, GridCell<Item>>): List<GridCell<Item>> {
        val result: MutableList<GridCell<Item>> = mutableListOf()
        var count = 0
        var current = start
        while (current.second.value != Item.START) {
            count++
            result.add(current.second)
            val newDirection = current.second.value.directionFrom(current.first.opposite())!!
            current = cellDirection(current.second, newDirection)
        }
        return result
    }


    override fun partTwo(dayInput: DayInput): Any {
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
        val mainTiles = (listOf(startCell.apply { value = Item.VERTICAL }) + goWithPath)
        val toSet = mainTiles.toSet()

        val squizeTiles = findSquizeTiles(mainTiles)


        val visited =
            visit(toSet, squizeTiles, matrix.lastRow() + matrix.firstRow() + matrix.lastColumn() + matrix.firstColumn())

        val filterNot = matrix.filterNot { visited.contains(it) }.filterNot { mainTiles.contains(it) }

        return filterNot
    }

    fun visit(
        mainTiles: Set<GridCell<Item>>,
        squize: Set<GridCell<Item>>,
        startFrom: List<GridCell<Item>>
    ): Set<GridCell<Item>> {
        val visited: MutableSet<GridCell<Item>> = mutableSetOf()
        val linkedList = LinkedList<GridCell<Item>>()
        linkedList.addAll(startFrom)

        while (linkedList.isNotEmpty()) {
            val current = linkedList.removeFirst()
            if (visited.contains(current)) {
                continue
            }
            visited.add(current)
            current.straightNeighbours()
                .filterNot { visited.contains(it) }
                .filterNot { mainTiles.contains(it) }
                .forEach {
                    linkedList.add(it)
                }

            listOf(current.up().left(), current.up().right()).filter { it.valid() }.windowed(2, 1).filter {
                squize.containsAll(it)
            }.map { listOf(Direction.NORTH, Direction.SOUTH) to it }.filter { pair ->
                listOf(
                    pair.second[0].value.direction1,
                    pair.second[0].value.direction2
                ).filter { pair.first.contains(it) }.any()

            }.filter { pair ->
                listOf(
                    pair.second[1].value.direction1,
                    pair.second[1].value.direction2
                ).filter { pair.first.contains(it) }.any()
            }.map { it.second }.forEach {
                linkedList.addAll(it)
            }


            listOf(current.up().left(), current.up().right()).filter { it.valid() }.windowed(2, 1).filter {
                squize.containsAll(it)
            }.map { listOf(Direction.NORTH, Direction.SOUTH) to it }.filter { pair ->
                listOf(
                    pair.second[0].value.direction1,
                    pair.second[0].value.direction2
                ).filter { pair.first.contains(it) }.any()

            }.filter { pair ->
                listOf(
                    pair.second[1].value.direction1,
                    pair.second[1].value.direction2
                ).filter { pair.first.contains(it) }.any()
            }.map { it.second }.forEach {
                linkedList.addAll(it)
            }
        }

        return visited
    }

    fun findSquizeTiles(items: List<GridCell<Item>>): Set<GridCell<Item>> {
        val elementsSet = items.toSet()
        val gridCells = listOf(items.last()) + items + listOf(items.first())

        return gridCells.windowed(3, 1).flatMap { path ->
            val current = path[1]
            current.straightNeighbours()
                .filter { comparingItem ->
                    !path.contains(comparingItem)
                }.filter { elementsSet.contains(it) }

        }.toSet()
    }
}

fun main() {
    Day10().run()
}

