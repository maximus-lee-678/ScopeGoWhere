package ict2105.team02.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch
import java.util.*

class ScopeUpdateViewModel: ViewModel() {
    private var repo = DataRepository()

    fun updateScope(brand: String, model:String, serial:Int, type:String, nextSample: Date, status:String){
        viewModelScope.launch {
            val newScope = Endoscope(nextSample, brand, model, serial, status, type)
            repo.updateScope(newScope)
        }
    }

    fun deleteScope(serial: Int) {
        viewModelScope.launch {
            repo.deleteScope(serial)
        }
    }
}