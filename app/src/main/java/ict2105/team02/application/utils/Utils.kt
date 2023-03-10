package ict2105.team02.application.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class Utils {
    companion object {
        fun getTodayStartDate(): Date {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.time
        }

        fun getTodayEndDate(): Date {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            return calendar.time
        }

        fun strToDate(dateStr: String):Date{
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            return formatter.parse(dateStr)
        }
    }
}
