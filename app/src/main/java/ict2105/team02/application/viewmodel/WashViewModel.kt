package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WashViewModel() : ViewModel() {
    val isScopeDetailsDone = MutableLiveData<Boolean>();
    val isWasherDone = MutableLiveData<Boolean>();
    val isDetergentDone = MutableLiveData<Boolean>();
    val isDisinfectantDone = MutableLiveData<Boolean>();
    val isDryingCabinetDone = MutableLiveData<Boolean>();
}