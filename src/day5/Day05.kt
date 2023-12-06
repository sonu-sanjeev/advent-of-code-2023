package day5

import readInput

fun main() {
    val input = readInput("day5/Day05").filter { line -> line.isNotBlank() }
    val seeds = input.first().split(" ").mapNotNull { it.toLongOrNull() }

    val map = mutableMapOf<String, MutableList<List<Long>>>()
    var key = ""
    input.forEach { line ->
        if (line.contains("map")) {
            key = line
        } else if (key.isNotEmpty()) {
            val appendList = line.split(" ").map { it.toLong() }
            map[key] = map[key]?.apply {
                add(appendList)
            } ?: run {
                mutableListOf(appendList)
            }
        }
    }

    val almanac = Almanac(
        seeds = seeds,
        seedToSoilMap = getRangeMap("seed-to-soil map:", map),
        soilToFertilizerMap = getRangeMap("soil-to-fertilizer map:", map),
        fertilizerToWaterMap = getRangeMap("fertilizer-to-water map:", map),
        waterToLightMap = getRangeMap("water-to-light map:", map),
        lightToTemperatureMap = getRangeMap("light-to-temperature map:", map),
        temperatureToHumidityMap = getRangeMap("temperature-to-humidity map:", map),
        humidityToLocationMap = getRangeMap("humidity-to-location map:", map),
    )

    fun part1() {
        val seeds = almanac.seeds
        val soilLocations = seeds.map { seed ->
            val seedSoilMap = almanac.seedToSoilMap
            seedSoilMap.forEach {
                if (it.sourceRangeStart <= seed && it.sourceRangeStart + it.rangeLength - 1 >= seed) {
                    return@map it.destinationRangeStart + (seed - it.sourceRangeStart)
                }
            }
            return@map seed
        }

        val fertLocations = soilLocations.map { soil ->
            almanac.soilToFertilizerMap.forEach {
                if (it.sourceRangeStart <= soil && it.sourceRangeStart + it.rangeLength - 1 >= soil) {
                    return@map it.destinationRangeStart + (soil - it.sourceRangeStart)
                }
            }
            return@map soil
        }

        val waterLocations = fertLocations.map { fert ->
            almanac.fertilizerToWaterMap.forEach {
                if (it.sourceRangeStart <= fert && it.sourceRangeStart + it.rangeLength - 1 >= fert) {
                    return@map it.destinationRangeStart + (fert - it.sourceRangeStart)
                }
            }
            return@map fert
        }

        val lightLocations = waterLocations.map { water ->
            almanac.waterToLightMap.forEach {
                if (it.sourceRangeStart <= water && it.sourceRangeStart + it.rangeLength - 1 >= water) {
                    return@map it.destinationRangeStart + (water - it.sourceRangeStart)
                }
            }
            return@map water
        }

        val tempLocations = lightLocations.map { light ->
            almanac.lightToTemperatureMap.forEach {
                if (it.sourceRangeStart <= light && it.sourceRangeStart + it.rangeLength - 1 >= light) {
                    return@map it.destinationRangeStart + (light - it.sourceRangeStart)
                }
            }
            return@map light
        }

        val humLocations = tempLocations.map { temp ->
            almanac.temperatureToHumidityMap.forEach {
                if (it.sourceRangeStart <= temp && it.sourceRangeStart + it.rangeLength - 1 >= temp) {
                    return@map it.destinationRangeStart + (temp - it.sourceRangeStart)
                }
            }
            return@map temp
        }

        val locations = humLocations.map { hum ->
            almanac.humidityToLocationMap.forEach {
                if (it.sourceRangeStart <= hum && it.sourceRangeStart + it.rangeLength - 1 >= hum) {
                    return@map it.destinationRangeStart + (hum - it.sourceRangeStart)
                }
            }
            return@map hum
        }
        println(locations.minOf { it })
    }

    fun part2() {
        val seeds = almanac.seeds
        var minLocation = Long.MAX_VALUE
        seeds.chunked(2)
            .forEach { ss ->
                for (i in 0 until ss[1]) {
                    val seed = ss[0] + i
                    var soilLocation = seed
                    almanac.seedToSoilMap.find {
                        it.sourceRangeStart <= seed && it.sourceRangeStart + it.rangeLength - 1 >= seed
                    }?.let { map ->
                        soilLocation = map.destinationRangeStart + (seed - map.sourceRangeStart)
                    }

                    var fertLocation = soilLocation
                    almanac.soilToFertilizerMap.find {
                        it.sourceRangeStart <= soilLocation && it.sourceRangeStart + it.rangeLength - 1 >= soilLocation
                    }?.let { map ->
                        fertLocation = map.destinationRangeStart + (soilLocation - map.sourceRangeStart)
                    }

                    var waterLocation = fertLocation
                    almanac.fertilizerToWaterMap.find {
                        it.sourceRangeStart <= fertLocation && it.sourceRangeStart + it.rangeLength - 1 >= fertLocation
                    }?.let { map ->
                        waterLocation = map.destinationRangeStart + (fertLocation - map.sourceRangeStart)
                    }

                    var lightLocation = waterLocation
                    almanac.waterToLightMap.find {
                        it.sourceRangeStart <= waterLocation && it.sourceRangeStart + it.rangeLength - 1 >= waterLocation
                    }?.let { map ->
                        lightLocation = map.destinationRangeStart + (waterLocation - map.sourceRangeStart)
                    }

                    var tempLocation = lightLocation
                    almanac.lightToTemperatureMap.find {
                        it.sourceRangeStart <= lightLocation && it.sourceRangeStart + it.rangeLength - 1 >= lightLocation
                    }?.let { map ->
                        tempLocation = map.destinationRangeStart + (lightLocation - map.sourceRangeStart)
                    }

                    var humLocation = tempLocation
                    almanac.temperatureToHumidityMap.find {
                        it.sourceRangeStart <= tempLocation && it.sourceRangeStart + it.rangeLength - 1 >= tempLocation
                    }?.let { map ->
                        humLocation = map.destinationRangeStart + (tempLocation - map.sourceRangeStart)
                    }

                    var loc = humLocation
                    almanac.humidityToLocationMap.find {
                        it.sourceRangeStart <= humLocation && it.sourceRangeStart + it.rangeLength - 1 >= humLocation
                    }?.let { map ->
                        loc = map.destinationRangeStart + (humLocation - map.sourceRangeStart)
                    }

                    if (loc < minLocation) minLocation = loc
                }
            }
        println(minLocation)
    }

    part1()
    part2()
}

data class Almanac(
    val seeds: List<Long>,
    val seedToSoilMap: List<RangeMap>,
    val soilToFertilizerMap: List<RangeMap>,
    val fertilizerToWaterMap: List<RangeMap>,
    val waterToLightMap: List<RangeMap>,
    val lightToTemperatureMap: List<RangeMap>,
    val temperatureToHumidityMap: List<RangeMap>,
    val humidityToLocationMap: List<RangeMap>,
)

data class RangeMap(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long
)

fun getRangeMap(key: String, map: Map<String, List<List<Long>>>) = map[key]!!.map {
    RangeMap(
        destinationRangeStart = it[0],
        sourceRangeStart = it[1],
        rangeLength = it[2]
    )
}
