package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.EndoscopeStatistics
import ict2105.team02.application.model.User
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataRepository: DataRepository
) : ViewModel(){
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _endoscopeStat = MutableLiveData<EndoscopeStatistics>()
    val endoscopeStat: LiveData<EndoscopeStatistics> = _endoscopeStat

    fun fetchUserData(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            dataRepository.getAuthenticatedUserData {
                _user.postValue(it)
                onFinish?.invoke()
            }
        }
    }

    fun fetchEndoscopeStats(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            dataRepository.getEndoscopeStatistics {
                _endoscopeStat.postValue(it)
                onFinish?.invoke()
            }
        }
    }
}