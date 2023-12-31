package day7

import println
import readInput

class Day7(input: List<String>) {
    private val hands = input.mapNotNull { line ->
        val (hand, bid) = line.split(' ', limit = 2).takeIf { it.size == 2 } ?: return@mapNotNull null
        IndexedValue(bid.toIntOrNull() ?: return@mapNotNull null, hand)
    }

    private fun solve(cards: String): Int = hands
        .map { (bid, hand) -> IndexedValue(bid, hand.map(cards::indexOf)) }
        .sortedWith(compareBy(comparator, selector = { it.value }))
        .withIndex()
        .sumOf { (i, value) -> (i + 1) * value.index }

    fun part1(): Int = solve("23456789TJQKA")

    fun part2(): Int = solve("23456789TQKA")

    companion object {
        private val comparator = compareBy<List<Int>> { hand ->
            val counts = hand.filter { it >= 0 }.groupingBy { it }.eachCount().values.sortedDescending()
            val jokers = hand.count { it < 0 }
            when {
                counts.getOrElse(0) { 0 } + jokers >= 5 -> 6
                counts.getOrElse(0) { 0 } + jokers >= 4 -> 5
                counts.getOrElse(0) { 0 } + counts.getOrElse(1) { 0 } + jokers >= 5 -> 4
                counts.getOrElse(0) { 0 } + jokers >= 3 -> 3
                counts.getOrElse(0) { 0 } + counts.getOrElse(1) { 0 } + jokers >= 4 -> 2
                counts.getOrElse(0) { 0 } + jokers >= 2 -> 1
                else -> 0
            }
        }.thenComparator { a, b ->
            for (i in 0..minOf(a.lastIndex, b.lastIndex)) {
                if (a[i] != b[i]) return@thenComparator a[i] - b[i]
            }
            a.size - b.size
        }
    }
}

fun main() {
    val input = readInput("day7/Day07")
    val day7 = Day7(input)
    day7.part2().println()
}