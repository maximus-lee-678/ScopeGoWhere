package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch

class EquipmentListViewModel : ViewModel() {
    private val _equipments = MutableLiveData<List<Endoscope>>()
    private val _displayedEquipments = MutableLiveData<List<Endoscope>>()

    val equipments: LiveData<List<Endoscope>> get() = _equipments
    val displayedEquipments: LiveData<List<Endoscope>> get() = _displayedEquipments

    private val repo = DataRepository()

    fun fetchEquipments(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getAllEndoscopes {
                _equipments.postValue(it)
                _displayedEquipments.postValue(it)
                onFinish?.invoke()
            }
        }
    }

    fun filterEquipmentByStatus(statuses: List<String>) {
        if (equipments.value == null) return

        val filteredList = equipments.value!!.filter { statuses.contains(it.scopeStatus) }
        _displayedEquipments.postValue(filteredList)
    }


    fun filterEquipmentByName(name: String) {
        if (equipments.value == null) return

        val nameLowercase = name.lowercase()
        // Name is combination of model and serial
        val filteredList = equipments.value!!.filter { "${it.scopeModel}${it.scopeSerial}".lowercase().contains(nameLowercase) }
        _displayedEquipments.postValue(filteredList)
    }

    fun clearFilters() {
        _displayedEquipments.postValue(_equipments.value!!)
    }
}
