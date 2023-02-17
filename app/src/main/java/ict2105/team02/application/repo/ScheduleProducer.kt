package ict2105.team02.application.repo
import androidx.lifecycle.MutableLiveData
import ict2105.team02.application.model.Schedule
import java.text.SimpleDateFormat
import java.util.*


object ScheduleProducer {
    private val dataSet = ArrayList<Schedule>()

    fun getInstance(): ScheduleProducer = this

    val dateString = "2023-02-17"
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = format.parse(dateString)

    // Pretend to get data from a webservice or online source
    fun getSchedules(): MutableLiveData<List<Schedule>> {
        setSchedules()
        val data = MutableLiveData<List<Schedule>>()
        data.value = dataSet
        return data
    }
    fun setSchedules(){
        dataSet.add(Schedule(date,"Scope ABC"))
        dataSet.add(Schedule(date,"Scope EFG"))
        dataSet.add(Schedule(date,"Scope ASD"))
        dataSet.add(Schedule(date,"Scope QWE"))
        dataSet.add(Schedule(date,"Scope ABC"))
        dataSet.add(Schedule(date,"Scope EFG"))
        dataSet.add(Schedule(date,"Scope ASD"))
        dataSet.add(Schedule(date,"Scope QWE"))
    }

}