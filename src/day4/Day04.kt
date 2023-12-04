package day4

import readInput
import kotlin.math.pow

val cards = readInput("day4/Day04")

fun main() {

    fun part1() {
        val winners = getWinners(cards)

        val result = List(winners.size) { index ->
            if (winners[index].size > 1)
                2.toDouble().pow(winners[index].size - 1)
            else 1.0
        }.sum()

        println(result)
    }

    fun part2() {
        val counterPair = MutableList(cards.size) { index ->
            Pair(index + 1, 1)
        }

        cards.forEachIndexed { cardIndex, card ->
            val winnerCount = getWinnerCount(card)
            val increment = counterPair[cardIndex].second
            for (i in cardIndex + 1 .. cardIndex + winnerCount) {
                counterPair[i] = Pair(counterPair[i].first, counterPair[i].second + increment)
            }
        }

        println(counterPair.sumOf { it.second })
    }

    part1()
    part2()
}

fun getWinners(cards: List<String>) = cards.map { card ->
    card.split(" ").mapNotNull { str ->
        str.toIntOrNull()
    }.groupBy { num ->
        num
    }.filter { mapItem ->
        mapItem.value.size > 1
    }.map { item ->
        item.key
    }
}.filter {
    it.isNotEmpty()
}

fun getWinnerCount(card: String) = card.split(" ").mapNotNull { str ->
    str.toIntOrNull()
}.groupBy { num ->
    num
}.filter { mapItem ->
    mapItem.value.size > 1
}.map { item ->
    item.key
}.size

