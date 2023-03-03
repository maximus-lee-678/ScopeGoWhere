package ict2105.team02.application.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.History
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.UiState
import kotlinx.coroutines.launch

class ScopeDetailViewModel : ViewModel() {
    private val _scopeDetail = MutableLiveData<Endoscope>()
    val scopeDetail: LiveData<Endoscope> = _scopeDetail
    var scopeHistory = MutableLiveData<List<History>>()
    private val repo = DataRepository()

    fun fetchScopeDetail(serial: Int, onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getEndoscope(serial.toString()) {
                _scopeDetail.postValue(it)
                onFinish?.invoke()
            }
        }
    }

    fun fetchScopeHistory(serial: Int, onFinish: (() -> Unit)? = null){
        viewModelScope.launch {
            repo.getEndoscopeHistory(serial.toString()){
                scopeHistory.postValue(it)
                onFinish?.invoke()
            }
        }
    }

    // TO BE IMPLEMENTED
    fun fetchLogDetail(){

    }
}