package ict2105.team02.application.utils

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import ict2105.team02.application.model.WashData
import java.text.SimpleDateFormat
import java.util.*

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

        fun createMaterialDatePicker(title: String, onSelect: (Long) -> Unit): MaterialDatePicker<Long> {
            val constrains = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
                .build()
            return MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constrains)
                .build().apply {
                    addOnPositiveButtonClickListener {
                        onSelect(it)
                    }
                }
        }
    }
}
