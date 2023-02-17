package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.model.Schedule
import ict2105.team02.application.repo.ScheduleProducer

class ScheduleInfoViewModel : ViewModel() {
//    private val endoscopeData = MutableLiveData<Endoscope>()
    private var mRepo: ScheduleProducer? = null
    private var mListSchedule: MutableLiveData<List<Schedule>>? = null

    init {
        if (mListSchedule != null) {

        }else{
            mRepo = ScheduleProducer.getInstance()
            mListSchedule = mRepo!!.getSchedules()
        }

    }

    fun getSchedule(): LiveData<List<Schedule>>?{
        return mListSchedule
    }
}