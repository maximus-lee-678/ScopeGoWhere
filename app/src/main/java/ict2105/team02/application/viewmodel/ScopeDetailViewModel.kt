package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.UiState
import kotlinx.coroutines.launch

class ScopeDetailViewModel : ViewModel() {
    private val _scopeDetail = MutableLiveData<Endoscope>()
    val scopeDetail: LiveData<Endoscope> = _scopeDetail

    private val repo = DataRepository()

    fun fetchScopeDetail(serial: String, onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getEndoscope(serial) {
                _scopeDetail.postValue(it)
                onFinish?.invoke()
            }
        }
    }


    // TO BE IMPLEMENTED
    fun fetchLogDetail(){

    }
}