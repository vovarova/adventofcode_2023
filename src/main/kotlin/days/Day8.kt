package days

import util.DAY_FILE
import util.DayInput
import java.math.BigInteger


class Day8 : Day("8") {

    override fun partOne(dayInput: DayInput): Any {
        val instructions = dayInput.inputList()[0].toCharArray()
        val map = dayInput.inputList().subList(2, dayInput.inputList().size).map {
            val split = it.split(" = ")
            val node = split[0]
            val path = split[1].replace(")", "").replace("(", "").split(", ")
            node to Pair(path[0], path[1])
        }.toMap()

        var current = "AAA"
        var instructionsCount = -1
        var steps = 0
        while (current != "ZZZ") {
            instructionsCount++
            val instruction = instructions[instructionsCount % instructions.size]
            val pair = map[current]!!
            current = if (instruction == 'L') pair.first else pair.second
        }
        return instructionsCount + 1
    }


    // Function to calculate the Greatest Common Divisor (GCD) using Euclidean algorithm
    fun calculateGCD(a: Long, b: Long): Long {
        return if (b == 0L) {
            a
        } else {
            calculateGCD(b, a % b)
        }
    }

    // Function to calculate the Least Common Multiple (LCM) given two numbers
    fun calculateLCM(a: Long, b: Long): Long {
        return a * b / calculateGCD(a, b)
    }

    // Function to calculate the LCD for an array of numbers
    fun findLCD(numbers: LongArray): BigInteger {
        var commomnLCD = BigInteger.valueOf(numbers[0])
        numbers.slice(1 until numbers.size).forEach {
            val current = BigInteger.valueOf(it)
            val mult = commomnLCD.multiply(current)
            val gcd: BigInteger = commomnLCD.gcd(current)
            commomnLCD = mult.divide(gcd)
        }
        return commomnLCD
    }

    override fun partTwo(dayInput: DayInput): Any {
        val instructions = dayInput.inputList()[0].toCharArray()
        val map = dayInput.inputList().subList(2, dayInput.inputList().size).map {
            val split = it.split(" = ")
            val node = split[0]
            val path = split[1].replace(")", "").replace("(", "").split(", ")
            node to Pair(path[0], path[1])
        }.toMap()
        /*var current = map.map { it.key }.filter { it.last() == 'A' }*/
        var current = map.map { it.key }.filter { it.last() == 'A' }
        val interations = mutableListOf<Long>()

        for (strart in current) {
            var instructionsCount = -1
            var currentStart = strart
            while (currentStart.last() != 'Z') {
                instructionsCount++
                val instruction = instructions[instructionsCount % instructions.size]
                val pair = map[currentStart]!!
                currentStart = if (instruction == 'L') pair.first else pair.second
            }
            interations.add(instructionsCount + 1L)
        }
        return findLCD(interations.toLongArray())
    }

    fun partTwoM(dayInput: DayInput): Any {
        val instructions = dayInput.inputList()[0].toCharArray()
        val map = dayInput.inputList().subList(2, dayInput.inputList().size).map {
            val split = it.split(" = ")
            val node = split[0]
            val path = split[1].replace(")", "").replace("(", "").split(", ")
            node to Pair(path[0], path[1])
        }.toMap()
        /*var current = map.map { it.key }.filter { it.last() == 'A' }*/

        var current = map.map { it.key }.filter { it.last() == 'A' }

        val occurance: MutableMap<String, MutableMap<String, MutableList<Pair<Int, Int>>>> = mutableMapOf()
        for (strart in current) {
            occurance.put(strart, mutableMapOf())

            var currentStarrt = strart
            var instructionsCount = 0
            while (true) {
                currentStarrt = map[currentStarrt]!!.let {
                    val instruction = instructions[instructionsCount % instructions.size]
                    if (instruction == 'L') it.first else it.second
                }
                instructionsCount++
                if (currentStarrt.last() == 'Z') {
                    occurance[strart]!!.putIfAbsent(currentStarrt, mutableListOf())
                    val firstIndex = occurance.get(strart)!!.get(currentStarrt)!!.lastOrNull()?.first ?: 0
                    occurance.get(strart)!!.get(currentStarrt)!!
                        .add(Pair(instructionsCount, instructionsCount - firstIndex + 1))
                    println("$currentStarrt ${occurance.get(strart)!!.get(currentStarrt)!!}")
                    if (occurance.get(strart)!!.get(currentStarrt)!!.size > 4) {
                        break
                    }
                }
            }
        }
        return ""
    }
}

fun main() {

    val day = Day8().run()

}

