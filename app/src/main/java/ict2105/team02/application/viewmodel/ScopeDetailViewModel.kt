package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch

class ScopeDetailViewModel : ViewModel() {
    val scopeDetail = MutableLiveData<Endoscope>()

    private val repo = DataRepository()

    fun fetchScopeDetail(serial: String, onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getEndoscope(serial) {
                scopeDetail.postValue(it)
                onFinish?.invoke()
            }
        }
    }
}