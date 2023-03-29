package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch
import java.util.*

class ScheduleInfoViewModel(
    private val dataRepository: DataRepository
) : ViewModel() {
    private var mListEndoscope: MutableLiveData<List<Endoscope>>? = null
    private var allEndoscope: List<Endoscope> = emptyList()

    init {
	    mListEndoscope = MutableLiveData()
    }

    fun setScheduleByDate(inputDate: Date) {

	    val filteredEndoscope = allEndoscope.filter { areDatesEqualIgnoringTime(it.nextSampleDate, inputDate) }
	    mListEndoscope?.value = filteredEndoscope
    }

    fun getScheduledEndoscope(): LiveData<List<Endoscope>>? {
        return mListEndoscope
    }

    private fun areDatesEqualIgnoringTime(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

    fun fetchAllScheduledScope(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            dataRepository?.getAllEndoscopes {
                allEndoscope = it
                onFinish?.invoke()
            }
        }
    }
}