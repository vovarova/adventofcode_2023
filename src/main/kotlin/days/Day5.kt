package days

import util.DayInput

class Day5 : Day("5") {


    class Range(val start: Long, val length: Long) {
        val end = start + length - 1
        fun validValue(value: Long): Boolean {
            return start <= value && value - start <= length - 1
        }

        fun intersect(otherRange: Range): Range? {
            val start = Math.max(start, otherRange.start)
            val end = Math.min(end, otherRange.end)
            return fromStartEnd(start, end)
        }

        companion object {
            fun fromStartEnd(start: Long, end: Long): Range? {
                if (start <= end) {
                    return Range(start, end - start + 1)
                }
                return null
            }
        }
    }

    class MapRanges {
        var sourceToTargetMapRanges = mutableListOf<MapRange>()
        fun targetFromSource(source: Long): Long {
            return sourceToTargetMapRanges.map { it.targetFromSource(source) }.filterNotNull().firstOrNull() ?: source
        }

        fun targetFromSource(source: Range): List<Range> {
            val fold = sourceToTargetMapRanges.fold(Pair(listOf<Range>(source), listOf<Range>())) { acc, mapRange ->
                val map = acc.first.map { mapRange.targetFromSource(it) }
                val mapedRange = map.map { it.mappedRange }.filterNotNull()
                val notFoundRange = map.flatMap { it.notFoundRange }
                Pair(notFoundRange, acc.second + mapedRange)
            }
            return fold.first + fold.second
        }
    }


    data class MapRange(val sourceStart: Long, val targetStart: Long, val length: Long) {
        fun targetFromSource(source: Long): Long? {
            if (sourceStart <= source && source - sourceStart <= length - 1) {
                return targetStart + (source - sourceStart)
            }
            return null
        }


        class ConversionResult(val mappedRange: Range?, val notFoundRange: List<Range>)

        fun targetFromSource(source: Range): ConversionResult {
            val intersect = source.intersect(Range(sourceStart, length))
            return if (intersect == null) {
                ConversionResult(null, listOf(source))
            } else {
                ConversionResult(
                    Range(targetFromSource(intersect.start)!!, intersect.length),
                    listOf(
                        Range.fromStartEnd(source.start, intersect.start - 1),
                        Range.fromStartEnd(intersect.end + 1, source.end)
                    )
                        .filterNotNull()
                )
            }
        }
    }

    class Map(val id: String, val source: String, val target: String) {
        val mapRanges = MapRanges()
    }


    fun generateMap(dayInput: DayInput): MutableList<Map> {
        return dayInput.inputList().subList(1, dayInput.inputList().size).fold(mutableListOf<Map>()) { acc, line ->
            if (line.contains("map:")) {
                val split = line.replace(" map:", "").split("-to-")
                acc.add(Map(id = line, target = split[1], source = split[0]))
            } else if (line.isNotEmpty()) {
                val split = line.split(" ").map { it.toLong() }
                acc.last().mapRanges.sourceToTargetMapRanges.add(
                    MapRange(
                        sourceStart = split[1],
                        targetStart = split[0],
                        length = split[2]
                    )
                )
            }
            acc
        }
    }

    override fun partOne(dayInput: DayInput): Any {

        val inputList = dayInput.inputList()
        val seeds = inputList[0].replace("seeds: ", "").split(" ").map { it.toLong() }

        val map = generateMap(dayInput)


        val result = seeds.map { seed ->
            map.fold(seed) { acc, map ->

                val targetFromSource = map.mapRanges.targetFromSource(acc)
                println("seed $seed map ${map.id} source $acc target $targetFromSource")
                targetFromSource

            }
        }


        return result.min()
    }

    override fun partTwo(dayInput: DayInput): Any {

        val seeds = dayInput.inputList()[0].replace("seeds: ", "").split(" ")
            .map { it.toLong() }
            .windowed(2, 2) {
                Range(it[0], it[1])
            }

        val map = generateMap(dayInput)


        val result = seeds.map { seed ->
            map.fold(listOf(seed)) { acc, map ->
                val flatMap = acc.flatMap { map.mapRanges.targetFromSource(it) }
                flatMap

            }
        }
        return result.flatMap { it }.map { it.start }.min()
    }
}

fun main() {
    Day5().run()
}

