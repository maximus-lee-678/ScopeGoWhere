package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.WashData
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class WashViewModel() : ViewModel() {
    var washData = MutableLiveData<WashData>()
    var washDataMap = HashMap<String, Any?>()
    var scopeData = MutableLiveData<Endoscope>()
    private val repo = DataRepository()
    private val constraintsBuilder =
        CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(constraintsBuilder.build())
    // init empty data here
    init{
        washData.value = WashData(
            "",
        0,
        "",
        0,
        null,
        "",
        0,
        null,
        0,
        "",
        "",
        0,
        Date(),
        )

        scopeData.value = Endoscope(
            Date(),
            "",
            "",
            0,
            "",
            "",
        )
    }
    fun convertWashDataToMap(){
        val washData = washData.value ?: return // Get the value of the MutableLiveData or return empty HashMap if null
        washDataMap =  hashMapOf(
            "AERModel" to washData.AERModel,
            "AERSerial" to washData.AERSerial,
            "DetergentUsed" to washData.DetergentUsed,
            "DetergentLotNo" to washData.DetergentLotNo,
            "FilterChangeDate" to washData.FilterChangeDate,
            "DisinfectantUsed" to washData.DisinfectantUsed,
            "DisinfectantLotNo" to washData.DisinfectantLotNo,
            "DisinfectantChangedDate" to washData.DisinfectantChangedDate,
            "ScopeDryer" to washData.ScopeDryer,
            "DoneBy" to washData.DoneBy,
            "Remarks" to washData.Remarks,
            "DryerLevel" to washData.DryerLevel,
            "WashDate" to washData.WashDate
        )
    }

    fun insertIntoDB(serial: String){
        var sdf = SimpleDateFormat("yyMMdd",Locale.getDefault())
        val docuName = sdf.format(Date()).toString() + "-logs"
        viewModelScope.launch {
            convertWashDataToMap()
            repo.insertWashData(serial, docuName,washDataMap)
        }
    }

    fun makeScope(brand: String, model: String, serial: Int){
        scopeData.value = Endoscope(scopeBrand = brand, scopeModel = model, scopeSerial = serial)
    }

    fun getScopeSerial(): String{
        return scopeData.value?.scopeSerial.toString()
    }
}