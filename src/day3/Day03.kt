package day3

import readInput

fun main() {
    val input = readInput("day3/Day03")

    fun part1() {
        var resultSet = mutableListOf<Int>()
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c.isSymbol()) {
                    findNumbers(x, y, input).forEach {
                        resultSet.add(it.toInt())
                    }
                }
            }
        }
        println(resultSet.sumOf { it })
    }

    fun part2() {
        var resultSet = mutableListOf<Int>()
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c.isStar()) {
                    val nums = findNumbers(x, y, input)
                    if (nums.size > 1) {
                        val ratio = nums.map {
                            it.toInt()
                        }.reduce { acc, i ->
                            acc * i
                        }
                        resultSet.add(ratio)
                    }
                }
            }
        }
        println(resultSet.sumOf { it })
    }

    part1()
    part2()
}

fun Char.isSymbol() = !this.isDigit() && this != '.'
fun Char.isStar() = this == '*'

fun findNumbers(x: Int, y: Int, input: List<String>): MutableList<String> {
    val numbers = mutableListOf<String>()
    val visitedPoints = mutableListOf<Pair<Int, Int>>()
    val neighbours = listOf(
        Pair(x + 1, y + 1),
        Pair(x - 1, y - 1),
        Pair(x - 1, y + 1),
        Pair(x + 1, y - 1),
        Pair(x, y - 1),
        Pair(x - 1, y),
        Pair(x + 1, y),
        Pair(x, y + 1),
    )

    neighbours.forEach {
        if (it.first >= 0 && it.second <= input.lastIndex) {
            var number = ""
            if (input[it.second][it.first].isDigit() && notVisited(it, visitedPoints)) {
                visitedPoints.add(it)
                val c = input[it.second][it.first]
                number = appendLeft(c, it, input, visitedPoints)
                number += appendRight(c, it, input, visitedPoints)
                numbers.add(number)
            }
        }
    }
    return numbers
}

fun appendLeft(c: Char, point: Pair<Int, Int>, input: List<String>, visitedPoints: MutableList<Pair<Int, Int>>): String {
    var result = c.toString()
    val row = input[point.second]
    var x = point.first - 1
    while (x >= 0 && row[x].isDigit()){
        visitedPoints.add(Pair(x, point.second))
        result = row[x] + result
        --x
    }
    return result
}

fun appendRight(c: Char, point: Pair<Int, Int>, input: List<String>, visitedPoints: MutableList<Pair<Int, Int>>): String {
    var result = ""
    val row = input[point.second]
    var x = point.first + 1
    while (x <= row.lastIndex && row[x].isDigit()){
        visitedPoints.add(Pair(x, point.second))
        result += row[x]
        ++x
    }
    return result
}

fun notVisited(point: Pair<Int, Int>, visitedPoints: List<Pair<Int, Int>>): Boolean {
    return visitedPoints.none {
        it.first == point.first && it.second == point.second
    }
}