package ict2105.team02.application.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.WashData
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class WashViewModel() : ViewModel() {
    var washData = MutableLiveData<WashData>()
    var washDataMap = HashMap<String, Any?>()
    private val repo = DataRepository()
    lateinit var ScopeSerial: String
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
        null,
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
}