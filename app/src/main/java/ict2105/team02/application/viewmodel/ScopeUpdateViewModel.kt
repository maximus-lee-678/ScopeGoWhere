package ict2105.team02.application.viewmodel

import androidx.lifecycle.ViewModel
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import java.util.*

class ScopeUpdateViewModel: ViewModel() {
    private var repo = DataRepository()

    suspend fun updateScope(brand: String, model:String, serial:Int, type:String, nextSample: Date, status:String){
        var newScope = Endoscope(nextSample, brand, model, serial, status, type)
        repo.updateScope(newScope)
    }
}