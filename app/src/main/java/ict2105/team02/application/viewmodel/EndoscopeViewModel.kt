package ict2105.team02.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.Constants
import kotlinx.coroutines.launch
import java.util.*

class EndoscopeViewModel(
    private val dataRepository: DataRepository
): ViewModel() {

    fun insertScope(brand: String, model:String, serial:Int, type:String, nextSample: Date){
        val newScope = Endoscope(nextSample, brand, model, serial, Constants.ENDOSCOPE_CIRCULATION,type)
        dataRepository.insertNewScope(newScope)
    }

    fun updateScope(brand: String, model:String, serial:Int, type:String, nextSample: Date, status:String){
        viewModelScope.launch {
            val newScope = Endoscope(nextSample, brand, model, serial, status, type)
            dataRepository.updateScope(newScope)
        }
    }

    fun deleteScope(serial: Int) {
        viewModelScope.launch {
            dataRepository.deleteScope(serial)
        }
    }
}