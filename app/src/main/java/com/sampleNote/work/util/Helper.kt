package com.sampleNote.work.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

/*Here we are formatting date using library*/
object Helper {
    @JvmStatic
    fun formatDate(dateTime: LocalDateTime, format: String): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        return dateTime.format(formatter)
    }
}