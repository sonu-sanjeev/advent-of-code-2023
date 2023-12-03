fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { string ->
            "${string.first { it.isDigit() }}${string.last { it.isDigit() }}".toInt()
        }
    }

    /*------------Part 2 begins here---------------------------------*/

    val numbers = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    /*1. Go forward until you hit a number
    * 2. Or go forward until you get a digit word
    * 3. Go backward until you hit a number
    * 4. Or go backward until you get a digit word
    */
    fun part2(input: List<String>): Int {
        val numberInput = input.map { string ->
            var firstDigit = ""
            run lit@{
                string.forEach { char ->
                    // check if char is a digit, then return
                    char.digitToIntOrNull()?.let {
                        firstDigit = char.toString()
                        return@lit
                    }

                    //other-wise continue with char append and compare
                    firstDigit += char
                    numbers.keys.forEach {
                        if (firstDigit.contains(it)) {
                            firstDigit = firstDigit.replace(it, numbers[it]!!)
                            return@lit
                        }
                    }
                }
            }

            var lastDigit = ""
            run lit@{
                string.reversed().forEach { char ->
                    // check if char is a digit, then return
                    char.digitToIntOrNull()?.let {
                        lastDigit = char.toString()
                        return@lit
                    }

                    //other-wise continue with char append and compare
                    lastDigit = char + lastDigit
                    numbers.keys.forEach {
                        if (lastDigit.contains(it)) {
                            lastDigit = lastDigit.replace(it, numbers[it]!!)
                            return@lit
                        }
                    }
                }
            }

            return@map firstDigit + lastDigit
        }
        return part1(numberInput)
    }

    val input = readInput("day1/Day01")
    part1(input).println()
    part2(input).println()
}