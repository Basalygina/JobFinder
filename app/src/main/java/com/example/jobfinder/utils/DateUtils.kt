package com.example.jobfinder.utils

import java.time.LocalDate

object DateUtils {

    // Форматирование даты в формате "1 января"
    fun formatDateWithGenitive(isoDate: String): String {
        val date = LocalDate.parse(isoDate)
        val months = listOf(
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
        )
        return "${date.dayOfMonth} ${months[date.monthValue - 1]}"
    }
}