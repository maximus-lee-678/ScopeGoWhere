package ict2105.team02.application.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.model.Schedule
import ict2105.team02.application.repo.ScheduleProducer
import java.util.*
import kotlin.collections.ArrayList

class ScheduleInfoViewModel : ViewModel() {
//    private val endoscopeData = MutableLiveData<Endoscope>()
    private var mRepo: ScheduleProducer? = null
    private var mListSchedule: MutableLiveData<List<Schedule>>? = null
    private lateinit var allSchedule : List<Schedule>;
    init {
        if (mListSchedule != null) {
        }else{
            mRepo = ScheduleProducer.getInstance()
            allSchedule = mRepo!!.getSchedules()
            mListSchedule = MutableLiveData<List<Schedule>>()
            mListSchedule?.postValue(allSchedule)

        }
    }
    fun setScheduleByDate(inputDate : Date) {
//        mRepo = ScheduleProducer.getInstance()
        var filteredSchedule: ArrayList<Schedule> = ArrayList()
        for(schedule in allSchedule)
        {
            if(areDatesEqualIgnoringTime(schedule.date,inputDate)){
                filteredSchedule.add(schedule);
            }
        }
        val data = MutableLiveData<List<Schedule>>()
        data.value = filteredSchedule
//        mListSchedule = data
        mListSchedule?.postValue(filteredSchedule)
        Log.d("Model State", filteredSchedule.toString())
    }
    fun getSchedule(): LiveData<List<Schedule>>?{
        return mListSchedule
    }
    fun areDatesEqualIgnoringTime(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        calendar1.time = date1
        val calendar2 = Calendar.getInstance()
        calendar2.time = date2
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }
}