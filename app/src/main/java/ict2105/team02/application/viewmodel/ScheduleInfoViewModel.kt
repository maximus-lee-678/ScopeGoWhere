package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.EndoscopeProducer

class ScheduleInfoViewModel : ViewModel() {
//    private val endoscopeData = MutableLiveData<Endoscope>()
    private val listOfEndoscopeData = EndoscopeProducer().getendoscopes()

    init {
    }

    fun getEndoscopeData(): LiveData<List<Endoscope>> = listOfEndoscopeData
}