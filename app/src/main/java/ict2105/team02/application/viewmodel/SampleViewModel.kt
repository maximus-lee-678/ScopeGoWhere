package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.ResultData
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.toDateString
import kotlinx.coroutines.launch
import java.util.Date

class SampleViewModel: ViewModel() {
    var sampleData = MutableLiveData<ResultData>()
    var scopeData = MutableLiveData<Endoscope>()

    private val repo = DataRepository()

    fun makeScope(brand: String, model: String, serial: Int) {
        scopeData.value = Endoscope(scopeBrand = brand, scopeModel = model, scopeSerial = serial)
    }

    fun makeSampleData() {
        sampleData.value = ResultData()
    }

    fun setSample1Fluid(result: Boolean, action: String, cultureComment: String) {
        sampleData.postValue(
            sampleData.value?.copy(
                fluidResult = result,
                fluidAction = action,
                fluidComment = cultureComment
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

    fun setSample4Atp(waterATPRLU: Int?, swabATPRLU: Int?) {
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
                repo.getAuthenticatedUserData {
                    repo.insertSampleData(scopeData.value?.scopeSerial.toString(), docName, sampleDataVal.copy(
                        resultDate = Date(),
                        doneBy = it.name
                    ))
                }
            }
        }
    }
}