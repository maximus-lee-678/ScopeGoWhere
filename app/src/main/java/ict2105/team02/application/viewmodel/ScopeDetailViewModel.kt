package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.EndoscopeTransaction
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.UiState
import kotlinx.coroutines.launch

class ScopeDetailViewModel : ViewModel() {
    private val _scopeDetail = MutableLiveData<Endoscope>()
    val scopeDetail: LiveData<Endoscope> = _scopeDetail

    private val _scopeLogDetail = MutableLiveData<UiState<List<EndoscopeTransaction>>>()
    val scopeLogDetail: LiveData<UiState<List<EndoscopeTransaction>>> = _scopeLogDetail

    private val repo = DataRepository()

    fun fetchScopeDetail(serial: Int) {
        viewModelScope.launch {
            repo.getEndoscope(serial.toString()) {
                _scopeDetail.postValue(it)
            }
        }
    }

    fun fetchLogDetail(serial: Int) {
        viewModelScope.launch {
            val result = repo.getEndoscopeHistory(serial.toString())
            _scopeLogDetail.postValue(result)
        }
    }
}