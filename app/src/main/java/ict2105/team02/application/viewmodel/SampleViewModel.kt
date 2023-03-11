package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import ict2105.team02.application.model.SampleData
import ict2105.team02.application.model.WashData
import java.util.Date

class SampleViewModel: ViewModel() {
    var sampleData = MutableLiveData<SampleData>()
    private val constraintsBuilder =
        CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(constraintsBuilder.build())
    init {
            sampleData.value = SampleData(
                Date(),
                "",
                "",
                "",
                Date(),
                "",
                "",
                "",
                "",
                Date(),
                "",
                "",
                ""
            )
    }
}