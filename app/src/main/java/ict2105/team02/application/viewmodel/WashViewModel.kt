package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.WashData
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.toDateString
import kotlinx.coroutines.launch
import java.util.*

class WashViewModel : ViewModel() {
    var washData = MutableLiveData<WashData>()
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

    fun makeWashData() {
        washData.value = WashData()
    }

    fun setWash2AER(modelAER: String, serialAER: Int?) {
        washData.postValue(
            washData.value?.copy(
                AERModel = modelAER,
                AERSerial = serialAER,
            )
        )
    }

    fun setWash3Detergent(disinfectantUsed: String, lotNo: Int?, filterChangeDate: Date?) {
        washData.postValue(
            washData.value?.copy(
                DetergentUsed = disinfectantUsed,
                DetergentLotNo = lotNo,
                FilterChangeDate = filterChangeDate
            )
        )
    }

    fun setWash4Disinfectant(disinfectantUsed: String, lotNo: Int?, changedDate: Date?) {
        washData.postValue(
            washData.value?.copy(
                DisinfectantUsed = disinfectantUsed,
                DisinfectantLotNo = lotNo,
                DisinfectantChangedDate = changedDate
            )
        )
    }

    fun setWash5Drying(scopeDryer: Int?, dryerLevel: Int?, remarks: String) {
        washData.postValue(
            washData.value?.copy(
                ScopeDryer = scopeDryer,
                DryerLevel = dryerLevel,
                Remarks = remarks
            )
        )
    }

    fun insertWashData() {
        val docName = Date().toDateString("yyMMdd") + "-logs"
        val washDataVal = washData.value
        if (washDataVal != null) {
            viewModelScope.launch {
                repo.insertWashData(scopeData.value?.scopeSerial.toString(), docName, washDataVal)
            }
        }
    }
}