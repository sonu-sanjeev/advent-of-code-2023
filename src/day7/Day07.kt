package day7

import readInput

fun main() {
    val input = readInput("day7/Day07")
        .map { line ->
            val (card, bid) = line.split(" ")
            Pair(card, bid)
        }

    val fiveOfAKind = mutableListOf<Pair<String, String>>()
    val fourOfAKind = mutableListOf<Pair<String, String>>()
    val fullHouse = mutableListOf<Pair<String, String>>()
    val threeOfAKind = mutableListOf<Pair<String, String>>()
    val twoPair = mutableListOf<Pair<String, String>>()
    val onePair = mutableListOf<Pair<String, String>>()
    val highCards = mutableListOf<Pair<String, String>>()

    fun part1() {
        input.map { pair ->
            pair.first.replace('J', pair.first.findCharToReplace()).groupBy { c ->
                c
            }.values.map {
                it.size
            }.sorted().reversed()
        }.map {
            var string = ""
            it.forEach {
                string += it
            }
            string
        }.forEachIndexed { index, type ->
            when (type) {
                "5" -> fiveOfAKind.add(input[index])
                "41" -> fourOfAKind.add(input[index])
                "32" -> fullHouse.add(input[index])
                "311" -> threeOfAKind.add(input[index])
                "221" -> twoPair.add(input[index])
                "2111" -> onePair.add(input[index])
                "11111" -> highCards.add(input[index])
            }
        }

        val cardTypes = CardTypes(
            fiveOfAKind.apply { sortSameTypes() },
            fourOfAKind.apply { sortSameTypes() },
            fullHouse.apply { sortSameTypes() },
            threeOfAKind.apply { sortSameTypes() },
            twoPair.apply { sortSameTypes() },
            onePair.apply { sortSameTypes() },
            highCards.apply { sortSameTypes() }
        ).arrangeInOrder()

        cardTypes.mapIndexed { index, pair ->
            (index + 1) * pair.second.toInt()
        }.sum().also(::println)

    }

    part1()
}

data class CardTypes(
    val fiveOfAKind: MutableList<Pair<String, String>>,
    val fourOfAKind: MutableList<Pair<String, String>>,
    val fullHouse: MutableList<Pair<String, String>>,
    val threeOfAKind: MutableList<Pair<String, String>>,
    val twoPair: MutableList<Pair<String, String>>,
    val onePair: MutableList<Pair<String, String>>,
    val highCards: MutableList<Pair<String, String>>
)

fun String.findCharToReplace(): Char {
    val grouped = this.groupBy { it }.filterNot {
        it.key == 'J'
    }
    var maxSize = 0
    var charToReplace = '0'
    grouped.values.forEach {
        if (it.size > maxSize) maxSize = it.size
    }
    grouped.filter {
        it.value.size == maxSize
    }.forEach {
        if (it.key.strength() > charToReplace.strength()) {
            charToReplace = it.key
        }
    }
    return charToReplace
}

fun CardTypes.arrangeInOrder() =
    highCards + onePair + twoPair + threeOfAKind + fullHouse + fourOfAKind + fiveOfAKind


fun MutableList<Pair<String, String>>.sortSameTypes() {
    for (i in 0 until size - 1) {
        for (j in 0 until size - i - 1) {
            if (shouldSwap(this[j].first, this[j + 1].first)) {
                val temp = this[j]
                this[j] = this[j + 1]
                this[j + 1] = temp
            }
        }
    }
}

fun shouldSwap(firstItem: String, secondItem: String): Boolean {
    var shouldSwap = false
    for (i in 0..firstItem.lastIndex) {
        if (firstItem[i].strength() > secondItem[i].strength()) {
            shouldSwap = true
            break
        } else if (firstItem[i].strength() < secondItem[i].strength()) {
            shouldSwap = false
            break
        }
    }
    return shouldSwap
}

fun Char.strength() = when (this) {
    'A' -> 14
    'K' -> 13
    'Q' -> 12
    'J' -> 1
    'T' -> 10
    else -> this.digitToInt()
}

