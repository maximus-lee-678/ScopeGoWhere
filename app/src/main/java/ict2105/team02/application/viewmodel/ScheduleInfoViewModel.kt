package ict2105.team02.application.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.DateDetails
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.Schedule
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.repo.ScheduleProducer
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ScheduleInfoViewModel : ViewModel() {
    //    private val endoscopeData = MutableLiveData<Endoscope>()
    private var mRepo: ScheduleProducer? = null
    private val xRepo: DataRepository = DataRepository()
    private var mListSchedule: MutableLiveData<List<Schedule>>? = null
    private lateinit var allSchedule: List<Schedule>
    private var allEndoscope : List<Endoscope> = emptyList()


    init {
        if (mListSchedule != null) {
        } else {

            mRepo = ScheduleProducer.getInstance()
            allSchedule = mRepo!!.getSchedules()
            mListSchedule = MutableLiveData<List<Schedule>>()
            mListSchedule?.postValue(allSchedule)

        }
    }

    fun setScheduleByDate(inputDate: Date) {
//        mRepo = ScheduleProducer.getInstance()
        var filteredSchedule: ArrayList<Schedule> = ArrayList()
        if(allEndoscope == null)
            return
        for (endoscope in allEndoscope) {

            if (endoscope.nextSampleDate != null &&
                areDatesEqualIgnoringTime(endoscope.nextSampleDate, inputDate)) {
                var schedule = Schedule(endoscope.nextSampleDate,endoscope.scopeSerial.toString())
                filteredSchedule.add(schedule)

            }
        }
        val data = MutableLiveData<List<Schedule>>()
        data.value = filteredSchedule
//        mListSchedule = data
        mListSchedule?.postValue(filteredSchedule)
        Log.d("Model State", filteredSchedule.toString())
    }

    fun getSchedule(): LiveData<List<Schedule>>? {
        return mListSchedule
    }

    fun areDatesEqualIgnoringTime(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }
    fun fetchAllScheduledScope(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            xRepo?.getAllEndoscopes {
                allEndoscope = it
                onFinish?.invoke()
            }
        }
    }
}