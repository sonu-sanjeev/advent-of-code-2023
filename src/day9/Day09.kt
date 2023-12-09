package day9

import readInput

fun main() {
    val input = readInput("day9/Day09")
        .map { line ->
            line.split(" ")
                .map {
                    it.toLong()
                }
        }

    fun solve(addFirst: Boolean) {
        input.sumOf { numbers ->
            val sequences = mutableListOf(numbers)
            while (sequences.last().any { it != 0L }) {
                val lastList = sequences.last()
                val newList = mutableListOf<Long>()
                for (i in 0 until lastList.lastIndex) {
                    val diff = lastList[i + 1] - lastList[i]
                    newList.add(diff)
                }
                sequences.add(newList)
            }
            sequences
                .map {
                    if (addFirst) {
                        it.first()
                    } else {
                        it.last()
                    }
                }
                .reversed()
                .reduce { acc, l ->
                    if (addFirst) {
                        l - acc
                    } else {
                        acc + l
                    }
                }
        }.also {
            println(it)
        }
    }

    fun part1() = solve(addFirst = false)
    fun part2() = solve(addFirst = true)

    part1()
    part2()
}