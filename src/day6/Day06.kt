package day6

import readInput

fun main() {
    val races = readInput("day6/Day06")
        .map { line ->
            line.split(" ")
                .mapNotNull {
                    it.toLongOrNull()
                }
        }.let { (t, d) ->
            t.zip(d)
        }.map {
            Race(
                time = it.first,
                record = it.second
            )
        }

    fun part1(races: List<Race>) {
        races.map { race ->
            var leftBoundary = 0L
            for (i in 0..race.time.div(2)) {
                val distanceTravelled = i * (race.time - i)
                if (distanceTravelled > race.record) {
                    leftBoundary = i
                    break
                }
            }
            val rightBoundary = race.time - leftBoundary
            return@map (rightBoundary + 1) - leftBoundary
        }.reduce { acc, i -> acc * i }.also(::println)
    }

    fun part2() {
        val finalRace = races.reduce { acc, race ->
            Race(
                time = "${acc.time}${race.time}".toLong(),
                record = "${acc.record}${race.record}".toLong()
            )
        }
        part1(listOf(finalRace))
    }

    part1(races)
    part2()
}

data class Race(
    val time: Long,
    val record: Long
)

