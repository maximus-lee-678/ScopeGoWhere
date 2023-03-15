package ict2105.team02.application.repo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ict2105.team02.application.model.DateDetails
import ict2105.team02.application.model.Schedule
import java.text.SimpleDateFormat
import java.util.*


object ScheduleProducer {
    private val dataSet = ArrayList<Schedule>()

    fun getInstance(): ScheduleProducer = this

    val dateString = "2023-02-18"
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = (format.parse(dateString))

    val dateString1 = "2023-02-19"
    val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date1 = (format.parse(dateString1))

    // Pretend to get data from a webservice or online source
    fun getSchedules(): List<Schedule> {
        setSchedules()
//        val data = MutableLiveData<List<Schedule>>()
//        data.value = dataSet
        return dataSet
    }
    fun getLiveSchedules(): MutableLiveData<List<Schedule>> {
        setSchedules()
        val data = MutableLiveData<List<Schedule>>()
        data.value = dataSet
        return data
    }
    fun setSchedules(){
        dataSet.add(Schedule(date,"Scope ABC", null, null))
        dataSet.add(Schedule(date,"Scope EFG", null, null))
        dataSet.add(Schedule(date,"Scope ASD", null, null))
        dataSet.add(Schedule(date,"Scope qwe", null, null))
        dataSet.add(Schedule(date1,"Scope 456", null, null))
        dataSet.add(Schedule(date1,"Scope 789", null, null))
        dataSet.add(Schedule(date1,"Scope 111", null, null))
        dataSet.add(Schedule(date1,"Scope 111", null, null))
        dataSet.add(Schedule(date,"Scope ABC", null, null))
        dataSet.add(Schedule(date,"Scope EFG", null, null))
        dataSet.add(Schedule(date,"Scope ASD", null, null))
        dataSet.add(Schedule(date,"Scope qwe", null, null))
        dataSet.add(Schedule(date1,"Scope 456", null, null))
        dataSet.add(Schedule(date1,"Scope 789", null, null))
        dataSet.add(Schedule(date1,"Scope 111", null, null))
        dataSet.add(Schedule(date1,"Scope 111", null, null))
        Log.d("ScheduleProducer", " The input date is $date")
    }

}