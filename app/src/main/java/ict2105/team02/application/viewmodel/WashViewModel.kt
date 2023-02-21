package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WashViewModel() : ViewModel() {
    val isScopeDetailsDone = MutableLiveData<Boolean>();
    val isWasherDone = MutableLiveData<Boolean>();
    val isDetergentDone = MutableLiveData<Boolean>();
    val isDisinfectantDone = MutableLiveData<Boolean>();
    val isDryingCabinetDone = MutableLiveData<Boolean>();

    val scopeBrand = MutableLiveData<String>();
    val scopeModel = MutableLiveData<String>();
    val scopeSerial = MutableLiveData<String>();

    val aerModel = MutableLiveData<String>();
    val aerSerial = MutableLiveData<String>();

    val detergentUsed = MutableLiveData<String>();
    val detergentLotNo = MutableLiveData<String>();
    val filterChangeDate = MutableLiveData<String>();

    val disinfectantUsed = MutableLiveData<String>();
    val disinfectantLotNo = MutableLiveData<String>();
    val disinfectantChanged = MutableLiveData<String>();

    val scopeDryer = MutableLiveData<String>();
    val scopeLevel = MutableLiveData<String>();
    val remarks = MutableLiveData<String>();
}