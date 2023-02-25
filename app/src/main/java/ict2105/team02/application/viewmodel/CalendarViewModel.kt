package ict2105.team02.application.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import ict2105.team02.application.model.DateDetails
import ict2105.team02.application.repo.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class CalendarViewModel(
    private val repository: UserPreferencesRepository, application: Application
) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    // Initialise following to today
    private val calendar: Calendar = Calendar.getInstance()
    var dateDetails: MutableLiveData<DateDetails> = MutableLiveData(DateDetails(calendar.time))
    var selectedDate: MutableLiveData<IntArray> =
        MutableLiveData(
            intArrayOf(
                dateDetails.value!!.day!!,
                dateDetails.value!!.month!!,
                dateDetails.value!!.year!!
            )
        )

    // Layout type of Schedule, retrieved from UserPreferencesRepository.
    var scheduleLayoutType: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            scheduleLayoutType.postValue(repository.userPreferencesFlow.first().scheduleLayoutType)
        }
    }

    /**
     * Writes new layout type to repository.
     * Called by listener in fragment. (layout switch)
     */
    fun updateLayoutType(layoutType: Boolean) {
        scheduleLayoutType.postValue(layoutType)
        viewModelScope.launch {
            repository.updateLayoutType(layoutType, context)
        }
    }

    /**
     * Realigns calendar and dateDetails values based on new selected date.
     * Called by listener in fragment. (layout switch)
     */
    fun forceAlignCalendar() {
        calendar.set(selectedDate.value!![2], selectedDate.value!![1] - 1, selectedDate.value!![0])

        refreshDateDetails()
    }

    /**
     * Helper function that updates dateDetails based on current calendar value.
     */
    private fun refreshDateDetails() {
        dateDetails.value = DateDetails(calendar.time)
    }

    /**
     * Changes calendar and dateDetails values.
     * Called by listener in fragment. (previous or next button)
     */
    fun updateSelectedPeriodStep(type: String, magnitude: Int) {
        when (type) {
            "week" -> calendar.add(Calendar.WEEK_OF_MONTH, magnitude)
            "month" -> calendar.add(Calendar.MONTH, magnitude)
        }

        refreshDateDetails()
    }

    /**
     * Changes calendar, dateDetails and selectedDate values.
     * Called by listener in fragment. (recyclerview cell)
     */
    fun updateSelectedDateExplicit(newSelectedDate: IntArray) {
        calendar.set(newSelectedDate[2], newSelectedDate[1] - 1, newSelectedDate[0])

        refreshDateDetails()
        selectedDate.value = newSelectedDate
    }
}

class CalendarViewModelFactory(
    private val repository: UserPreferencesRepository,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}