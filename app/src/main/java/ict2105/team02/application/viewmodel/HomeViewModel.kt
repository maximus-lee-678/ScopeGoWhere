package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.EndoscopeStatistics
import ict2105.team02.application.model.User
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _endoscopeStat = MutableLiveData<EndoscopeStatistics>()
    val endoscopeStat: LiveData<EndoscopeStatistics> = _endoscopeStat

    private val repo = DataRepository()

    fun fetchUserData(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getAuthenticatedUserData {
                _user.postValue(it)
                onFinish?.invoke()
            }
        }
    }

    fun fetchEndoscopeStats(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getEndoscopeStatistics {
                _endoscopeStat.postValue(it)
                onFinish?.invoke()
            }
        }
    }
}