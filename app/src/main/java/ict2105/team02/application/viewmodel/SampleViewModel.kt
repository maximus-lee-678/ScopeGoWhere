package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.ResultData
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
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

    fun setSample1Fluid(action: String, cultureComment: String) {
        sampleData.postValue(
            sampleData.value?.copy(
                fluidAction = action,
                fluidComment = cultureComment
            )
        )
    }

    fun setSample1Result(result: Boolean){
        sampleData.postValue(
            sampleData.value?.copy(
                fluidResult = result
            )
        )
    }

    fun setSample2Swab(date: Date?, action: String, cultureComment: String) {
        sampleData.postValue(
            sampleData.value?.copy(
                swabDate = date,
                swabAction = action,
                swabCultureComment = cultureComment
            )
        )
    }

    fun setSample2Result(result:Boolean){
        sampleData.postValue(
            sampleData.value?.copy(
                swabResult = result
            )
        )
    }

    fun setSample3RepeatOfMS(repeatDateMS: Date?) {
        sampleData.postValue(
            sampleData.value?.copy(
                repeatDateMS = repeatDateMS
            )
        )
    }

    fun setSample3Data(quarantineRequired: Boolean, borescope: Boolean){
        sampleData.postValue(
            sampleData.value?.copy(
                quarantineRequired = quarantineRequired,
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

    fun setAllSample(sampleDataHash: HashMap<String, String>) {
        var resultData = sampleData.value ?: ResultData() // initialize with current value or empty object

        sampleDataHash.forEach { (key, value) ->
            when (key) {
                "fluidResult" -> resultData = resultData.copy(fluidResult = value.toBooleanStrictOrNull())
                "fluidAction" -> resultData = resultData.copy(fluidAction = value)
                "fluidComment" -> resultData = resultData.copy(fluidComment = value)
                "swabDate" -> resultData = resultData.copy(swabDate = value.parseDateString())
                "swabResult" -> resultData = resultData.copy(swabResult = value.toBooleanStrictOrNull())
                "swabAction" -> resultData = resultData.copy(swabAction = value)
                "swabCultureComment" -> resultData = resultData.copy(swabCultureComment = value)
                "quarantineRequired" -> resultData = resultData.copy(quarantineRequired = value.toBooleanStrictOrNull())
                "repeatDateMS" -> resultData = resultData.copy(repeatDateMS = value.parseDateString())
                "waterATPRLU" -> resultData = resultData.copy(waterATPRLU = value.toIntOrNull())
                "swabATPRLU" -> resultData = resultData.copy(swabATPRLU = value.toIntOrNull())
            }
        }
        sampleData.postValue(resultData)
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