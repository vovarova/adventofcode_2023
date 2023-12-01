package days

import util.InputReader

abstract class Day(dayNumber: Int) {

    var example:Boolean = false;

    // lazy delegate ensures the property gets computed only on first access
    protected val inputList: List<String> by lazy { InputReader.getInputAsList(dayNumber.toString()) }
    protected val inputString: String by lazy { InputReader.getInputAsString(dayNumber.toString()) }


    protected val inputListExamplePart1: List<String> by lazy { InputReader.getInputAsList("${dayNumber}_ex_1") }
    protected val inputListExamplePart2: List<String> by lazy { InputReader.getInputAsList("${dayNumber}_ex_2") }


    protected val inputStringExamplePart1: String by lazy { InputReader.getInputAsString("${dayNumber}_ex_1") }
    protected val inputStringExamplePart2: String by lazy { InputReader.getInputAsString("${dayNumber}_ex_2") }


    val inputListPart1:List<String> by lazy { if (example) inputListExamplePart1 else inputList }
    val inputListPart2:List<String> by lazy { if (example) inputListExamplePart2 else inputList }

    abstract fun partOne(): Any

    abstract fun partTwo(): Any

}
