package ict2105.team02.application.utils

import android.content.Context
import android.widget.Toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
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
            return MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build().apply {
                    addOnPositiveButtonClickListener {
                        onSelect(it)
                    }
                }
        }

        fun createMaterialFutureDatePicker(title: String, onCancel: (() -> Unit)? = null, onSelect: (Long) -> Unit): MaterialDatePicker<Long> {
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
                    addOnNegativeButtonClickListener {
                        onCancel?.invoke()
                    }
                }
        }

        fun createMaterialPastDatePicker(title:String, onCancel: (() -> Unit)? = null, onSelect: (Long) -> Unit): MaterialDatePicker<Long>{
            val constraints = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())
                .build()
            return MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraints)
                .build().apply {
                    addOnPositiveButtonClickListener {
                        onSelect(it)
                    }
                    addOnPositiveButtonClickListener {
                        onCancel?.invoke()
                    }
                }
        }

        fun showToast(context: Context, msg:String){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
