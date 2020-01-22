package dev.futa.tutorial.pesel.generator

import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

fun main() {

    val startDate = LocalDate.of(1851, 12, 21)
    val endDate = LocalDate.of(2021, 1, 1)
    val peselsPerDay = 1234

    val random = Random(Date().time)

    val application = GeneratorApplication(PeselGenerator(random), File("d:\\logs\\pesels.txt"))
    application.run(startDate, endDate, peselsPerDay)

}

class GeneratorApplication(private val peselGenerator: PeselGenerator, private val outputFile: File) {

    fun run(startDate: LocalDate, endDate: LocalDate, peselsPerDay: Int) =
            outputFile.bufferedWriter().use { writer ->
                startDate.datesUntil(endDate).forEach { currentDay ->
                    repeat(peselsPerDay) {
                        writer.write(peselGenerator.generateValid(currentDay) + "\n")
                    }
                }
            }

}

class PeselGenerator(private val random: Random) {

    private val PESEL_WEIGHTS = intArrayOf(1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 0)
    private val CENTURY_MONTH_MAP = mapOf(Pair(18, 80),
            Pair(19, 0),
            Pair(20, 20),
            Pair(21, 40),
            Pair(22, 60))

    fun generateValid(birthDate: LocalDate): String = calculatedWithValidPesel(birthDate.year.rem(100),
            monthWithCentury(birthDate),
            birthDate.dayOfMonth,
            random.nextInt(0, 9),
            random.nextInt(0, 9),
            random.nextInt(0, 9),
            random.nextInt(0, 9))

    private fun monthWithCentury(birthDate: LocalDate): Int =
            birthDate.monthValue + CENTURY_MONTH_MAP[birthDate.year.div(100)]!!


    private fun calculatedWithValidPesel(year: Int, month: Int, day: Int, vararg nums: Int): String {

        val numbers = intArrayOf(
                year.div(10), year.rem(10),
                month.div(10), month.rem(10),
                day.div(10), day.rem(10),
                nums[0], nums[1], nums[2], nums[3],
                0
        )

        val checkSum = PESEL_WEIGHTS.foldIndexed(0) { idx, checkSum, it ->
            checkSum + it * numbers[idx]
        }.rem(10)
        numbers[10] = if (checkSum == 0) 0 else 10 - checkSum

        return numbers.joinToString("")
    }

}
