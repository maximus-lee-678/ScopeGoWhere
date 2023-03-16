package ict2105.team02.application.utils

import java.text.SimpleDateFormat
import java.util.*

// Extension to provide TAG string using the calling class's name
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun Date.toDateString(pattern: String = "dd/MM/yyyy"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun String.parseDateString(pattern: String = "dd/MM/yyyy"): Date? {
    return SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
}