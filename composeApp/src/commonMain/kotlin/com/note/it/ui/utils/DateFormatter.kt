package com.note.it.ui.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateFormatter {

    fun format(isoString: String?): String {

        if (isoString.isNullOrEmpty()) return ""

        return try {
            val instant = Instant.parse(isoString)

            val localDateTime =
                instant.toLocalDateTime(TimeZone.currentSystemDefault())

            val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
            val month = localDateTime.monthNumber.toString().padStart(2, '0')
            val year = localDateTime.year.toString().takeLast(2)
            val hour = localDateTime.hour.toString().padStart(2, '0')
            val minute = localDateTime.minute.toString().padStart(2, '0')

            "$day/$month/$year $hour:$minute"

        } catch (e: Exception) {
            ""
        }
    }
}