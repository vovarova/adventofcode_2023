package days

import util.DayInput

class Day6 : Day("6") {


    /*
    Time:      7  15   30
Distance:  9  40  200
     */
    class RecordTime(val time: Int, val record: Int) {


    }

    override fun partOne(dayInput: DayInput): Any {
        val inputList = dayInput.inputList()
        val time =
            inputList[0].replace("Time:", "").split(" ").map { it.trim() }.filter { it.isNotEmpty() }.map { it.toInt() }
        val record = inputList[1].replace("Distance:", "").split(" ").map { it.trim() }.filter { it.isNotEmpty() }
            .map { it.toInt() }

        val recordTimeRecords = time.mapIndexed { index, timeItem ->
            RecordTime(timeItem, record[index])
        }

        val map = recordTimeRecords.map {
            //X^2-xT+R<0
            val disriminant = it.time * it.time - 4 * it.record
            val x1 = Math.ceil((it.time - Math.sqrt(disriminant.toDouble())) / 2).toInt()
            val x2 = Math.floor((it.time + Math.sqrt(disriminant.toDouble())) / 2).toInt()

            var result = x2 - x1 - 1
            if (x1 * x1 - x1 * it.time + it.record < 0) result++
            if (x2 * x2 - x2 * it.time + it.record < 0) result++
            result
        }
        return map.reduce { acc, i -> acc * i }
    }

    override fun partTwo(dayInput: DayInput): Any {
        return dayInput.inputList()
    }
}


fun main() {
    Day6().run()
}

