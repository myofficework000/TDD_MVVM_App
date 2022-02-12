package com.sampleNote.work.util

import java.time.LocalDateTime
import java.util.*

object LocalDateEx {
    @JvmStatic
    fun getNow(): LocalDateTime = Calendar.getInstance().toLocalDateTime()
}

private fun Calendar.toLocalDateTime(): LocalDateTime = LocalDateTime.of(
    get(Calendar.YEAR),
    get(Calendar.MONTH) + 1,
    get(Calendar.DAY_OF_MONTH),
    get(Calendar.HOUR_OF_DAY),
    get(Calendar.MINUTE),
    get(Calendar.SECOND),
    get(Calendar.MILLISECOND) * 1000000
)