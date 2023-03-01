package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){
    val todaySchedule = MutableLiveData<List<Endoscope>>()

    private val repo = DataRepository()

    fun fetchTodayScheduledScope(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getTodayScheduledEndoscopes {
                todaySchedule.postValue(it)
                onFinish?.invoke()
            }
        }
    }
}