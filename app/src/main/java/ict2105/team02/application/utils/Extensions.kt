package ict2105.team02.application.utils

import ict2105.team02.application.model.ResultData
import ict2105.team02.application.model.WashData
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

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
    return try {
        SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun WashData.asHashMap(): HashMap<String, Any> {
    val map = HashMap<String, Any>()
    this::class.java.declaredFields.forEach { field ->
        field.isAccessible = true
        val value = field.get(this)
        if (value != null) {
            map[field.name] = value
        }
    }
    return map
}

fun ResultData.asHashMap(): HashMap<String, Any> {
    val map = HashMap<String, Any>()
    this::class.java.declaredFields.forEach { field ->
        field.isAccessible = true
        val value = field.get(this)
        if (value != null) {
            map[field.name] = value
        }
    }
    return map
}