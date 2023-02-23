package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class EquipmentListViewModel : ViewModel() {
    val equipments = MutableLiveData<List<Endoscope>>()

    private val repo = DataRepository()

    fun fetchEquipments(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getAllEndoscopes {
                equipments.postValue(it)
                onFinish?.invoke()
            }
        }
    }
}