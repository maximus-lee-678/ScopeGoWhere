package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.ResultData
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.asHashMap
import ict2105.team02.application.utils.toDateString
import kotlinx.coroutines.launch
import java.util.Date

class SampleViewModel: ViewModel() {
    var sampleData = MutableLiveData<ResultData>()
    var scopeData = MutableLiveData<Endoscope>()

    private val repo = DataRepository()

    private val constraintsBuilder =
        CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(constraintsBuilder.build())

    fun makeScope(brand: String, model: String, serial: Int) {
        scopeData.value = Endoscope(scopeBrand = brand, scopeModel = model, scopeSerial = serial)
    }

    fun makeSampleData() {
        sampleData.value = ResultData()
    }

    fun setSample1Fluid(date: Date?, result: Boolean, action: String, cultureComment: String) {
        sampleData.postValue(
            sampleData.value?.copy(
                resultDate = date,
                fluidResult = result,
                fluidAction = action,
            )
        )
    }

    fun setSample2Swab(date: Date?, result: Boolean, action: String, cultureComment: String) {
        sampleData.postValue(
            sampleData.value?.copy(
                swabDate = date,
                swabResult = result,
                swabAction = action,
                swabCultureComment = cultureComment
            )
        )
    }

    fun setSample3RepeatOfMS(quarantineRequired: Boolean, repeatDateMS: Date?, borescope: Boolean) {
        sampleData.postValue(
            sampleData.value?.copy(
                quarantineRequired = quarantineRequired,
                repeatDateMS = repeatDateMS,
                borescope = borescope
            )
        )
    }

    fun setSample4Atp(waterATPRLU: Int, swabATPRLU: Int) {
        sampleData.postValue(
            sampleData.value?.copy(
                waterATPRLU = waterATPRLU,
                swabATPRLU = swabATPRLU
            )
        )
    }

    fun insertSampleData() {
        val docName = Date().toDateString("yyMMdd") + "-logs"
        val sampleDataVal = sampleData.value
        if (sampleDataVal != null) {
            viewModelScope.launch {
                repo.insertSampleData(scopeData.value?.scopeSerial.toString(), docName, sampleDataVal)
            }
        }
    }
}