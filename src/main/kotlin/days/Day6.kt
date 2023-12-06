package days

import util.DayInput

class Day6 : Day("6") {


    /*
    Time:      7  15   30
Distance:  9  40  200
     */
    class RecordTime(val time: Long, val record: Long) {


    }

    override fun partOne(dayInput: DayInput): Any {
        val inputList = dayInput.inputList()
        val time =
            inputList[0].replace("Time:", "").split(" ").map { it.trim() }.filter { it.isNotEmpty() }
                .map { it.toLong() }
        val record = inputList[1].replace("Distance:", "").split(" ").map { it.trim() }.filter { it.isNotEmpty() }
            .map { it.toLong() }

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

    fun raceResult(button: Long, time: Long): Long {
        return button * (time - button)
    }

    override fun partTwo(dayInput: DayInput): Any {
        val inputList = dayInput.inputList()
        val time =
            inputList[0].replace("Time:", "").split(" ").map { it.trim() }.filter { it.isNotEmpty() }.joinToString("")
                .toLong()
        val record = inputList[1].replace("Distance:", "").split(" ").map { it.trim() }.filter { it.isNotEmpty() }
            .joinToString("").toLong()

        val disriminant = time * time - 4 * record
        val x1 = Math.ceil((time.toDouble() - Math.sqrt(disriminant.toDouble())) / 2.toDouble()).toLong()
        val x2 = Math.floor((time.toDouble() + Math.sqrt(disriminant.toDouble())) / 2.toDouble()).toLong()
        var result = x2 - x1 - 1
        if (raceResult(x1, time) > record) result++
        if (raceResult(x2, time) > record) result++
        return result
    }
}


fun main() {
    Day6().run()
}

