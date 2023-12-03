fun main() {
    val input = readInput("day2/Day02")
    val gamesInfo = input.map { row ->
        val (gameName, gameInfo) = row.split(":")
        val gameId = gameName.split(" ").last().toInt()

        val cubes = gameInfo
            .split(";")
            .flatMap {
                it.split(",")
            }.map {
                it.trim()
            }

        val maxGreenCount = getMaxCount("green", cubes)
        val maxRedCount = getMaxCount("red", cubes)
        val maxBlueCount = getMaxCount("blue", cubes)
        return@map Game(
            id = gameId,
            maxRedCount = maxRedCount,
            maxBlueCount = maxBlueCount,
            maxGreenCount = maxGreenCount,
        )
    }


    fun part1(): Int {
        return gamesInfo.filter {
            it.maxGreenCount <= 13 && it.maxRedCount <= 12 && it.maxBlueCount <= 14
        }.sumOf {
            it.id
        }
    }

    fun part2(): Int {
        return gamesInfo.sumOf {
            it.maxGreenCount * it.maxBlueCount * it.maxRedCount
        }
    }

    println(part1())
    println(part2())
}

fun getMaxCount(key: String, cubes: List<String>): Int {
    return cubes.filter {
        it.contains(key)
    }.maxOfOrNull {
        it.split(" ")[0].toInt()
    } ?: 0
}


data class Game(
    val id: Int,
    val maxRedCount: Int,
    val maxGreenCount: Int,
    val maxBlueCount: Int
)