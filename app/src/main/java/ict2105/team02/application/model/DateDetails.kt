package ict2105.team02.application.model

import android.util.Log
import ict2105.team02.application.ui.schedule.CalendarFragment
import java.text.SimpleDateFormat
import java.util.*

data class DateDetails(val todayDate: Date) {
    private val TAG: String = CalendarFragment::class.simpleName!!
    var day: Int? = null
    var month: Int? = null
    var monthEnglish: String? = null
    var year: Int? = null
    var daysInMonth: Int? = null
    var firstDayOfMonth: Int? = null
    var weekArray: Array<IntArray> = Array(7) { IntArray(3) }

    init {
        day = SimpleDateFormat("dd").format(todayDate).toInt()
        month = SimpleDateFormat("MM").format(todayDate).toInt()
        monthEnglish = SimpleDateFormat("MMMM").format(todayDate)
        year = SimpleDateFormat("yyyy").format(todayDate).toInt()

        // Create a calendar object and set year, month(0 terminated), day(first)
        // Get number of days and first day's int value
        var calendarObject: Calendar = GregorianCalendar(year!!, month!! - 1, 1)
        daysInMonth = calendarObject.getActualMaximum(Calendar.DAY_OF_MONTH)
        firstDayOfMonth = when (SimpleDateFormat("EEE").format(calendarObject.time)) { //
            "Mon" -> 1
            "Tue" -> 2
            "Wed" -> 3
            "Thu" -> 4
            "Fri" -> 5
            "Sat" -> 6
            "Sun" -> 0
            else -> {
                -1
            }
        }

        // Update calendar to current day, then first day of that week
        // Get monday's int value(negated if last month)
        calendarObject = GregorianCalendar(year!!, month!! - 1, day!!)
        Log.d(TAG, String.format("This log is important don't delete: accessed>%s", calendarObject.time.toString()))

        for (i in 0..6) {
            calendarObject.set(Calendar.DAY_OF_WEEK, if (i + 2 <= 7) i + 2 else i + 2 - 7)
            weekArray[i][0] = SimpleDateFormat("dd").format(calendarObject.time).toInt()
            weekArray[i][1] = SimpleDateFormat("MM").format(calendarObject.time).toInt()
            weekArray[i][2] = SimpleDateFormat("yyyy").format(calendarObject.time).toInt()
        }
    }

    override fun toString(): String {
        return String.format(
            "%s || %d-%d(%s)-%d t_days:%d f_day:%d wk_days:%d-%d",
            todayDate,
            day,
            month,
            monthEnglish,
            year,
            daysInMonth,
            firstDayOfMonth,
            weekArray[0][0],
            weekArray[6][0]
        )
    }
}