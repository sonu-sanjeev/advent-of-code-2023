package day8

import readInput

fun main() {
    val input = readInput("day8/Day08")

    val directions = input.first()
    val map = input
        .drop(2)
        .filter { it.isNotBlank() }
        .map { line ->
            line.split(" ")
                .let { (element, _, left, right) ->
                    val leftElement = left.removePrefix("(")
                    val rightElement = right.removeSuffix(")")
                    return@map element to Pair(leftElement.removeSuffix(","), rightElement)
                }
        }.toMap()

    fun part1(start: String = "AAA"): Int {
        var current = start
        var steps = 0
        while (!current.endsWith('Z')) {
            current = map[current].let { next ->
                when(directions[steps++ % directions.length]) {
                    'L' -> next!!.first
                    else -> next!!.second
                }
            }
        }
        return steps
    }

    fun part2(): Long {
        var startPositions = map.keys.filter { key -> key[2] == 'A' }
        return startPositions.map {
            part1(it).toLong()
        }.reduce { acc, l ->
            acc.lcm(l)
        }
    }

    println(part1())
    println(part2())

}

tailrec fun Long.gcd(other: Long): Long =
    if(other == 0L) this
    else other.gcd(this % other)

fun Long.lcm(other: Long): Long =
    (this * other) / this.gcd(other)