package com.common

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by wl on 16/8/4.
 */

fun formatDate(value: Any): String {
    val date = value as Date
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}