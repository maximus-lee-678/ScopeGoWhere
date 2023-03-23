package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun makeScope(brand: String, model: String, serial: Int) {
        scopeData.value = Endoscope(scopeBrand = brand, scopeModel = model, scopeSerial = serial)
    }

    fun makeWashData() {
        washData.value = WashData()
    }

    fun setWash1AER(modelAER: String, serialAER: Int?) {
        washData.postValue(
            washData.value?.copy(
                AERModel = modelAER,
                AERSerial = serialAER,
            )
        )
    }

    fun setWash2Detergent(disinfectantUsed: String, lotNo: Int?, filterChangeDate: Date?) {
        washData.postValue(
            washData.value?.copy(
                DetergentUsed = disinfectantUsed,
                DetergentLotNo = lotNo,
                FilterChangeDate = filterChangeDate
            )
        )
    }

    fun setWash3Disinfectant(disinfectantUsed: String, lotNo: Int?, changedDate: Date?) {
        washData.postValue(
            washData.value?.copy(
                DisinfectantUsed = disinfectantUsed,
                DisinfectantLotNo = lotNo,
                DisinfectantChangedDate = changedDate
            )
        )
    }

    fun setWash4Drying(scopeDryer: Int?, dryerLevel: Int?, remarks: String) {
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
                repo.getAuthenticatedUserData {
                    repo.insertWashData(scopeData.value?.scopeSerial.toString(), docName, washDataVal.copy(
                        WashDate = Date(),
                        DoneBy = it.name
                    ))
                }
            }
        }
    }
}