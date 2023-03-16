package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.DataRepository
import kotlinx.coroutines.launch

class EquipmentListViewModel : ViewModel() {
    private val equipments = MutableLiveData<List<Endoscope>>()
    val filteredEquipment = MutableLiveData<List<Endoscope>>()
    private val repo = DataRepository()

    fun fetchEquipments(onFinish: (() -> Unit)? = null) {
        viewModelScope.launch {
            repo.getAllEndoscopes {
                equipments.postValue(it)
                filteredEquipment.postValue(it)
                onFinish?.invoke()
            }
        }
    }

    fun filterEquipmentStatus(status: String): Int {
        val filteredList = getFilteredEquipmentStatusList(status, equipments.value ?: emptyList())
        filteredEquipment.postValue(filteredList)

        return filteredList.size
    }


    fun filterEquipmentSerial(status: String, serial: String): Int {
        val filteredList = getFilteredEquipmentSerialList(
            serial,
            getFilteredEquipmentStatusList(status, equipments.value ?: emptyList())
        )
        filteredEquipment.postValue(filteredList)

        return filteredList.size
    }

    private fun getFilteredEquipmentStatusList(status: String, toFilterList: List<Endoscope>): List<Endoscope> {
        val allEquipments = toFilterList
        val filtered = if (status.lowercase() == "all") {
            allEquipments // return all equipment
        } else {
            allEquipments.filter { it.scopeStatus.equals(status, ignoreCase = true) }
        }

        return filtered
    }

    private fun getFilteredEquipmentSerialList(serial: String, toFilterList: List<Endoscope>): List<Endoscope> {
        val filtered = toFilterList.filter {
            (it.scopeModel.plus(it.scopeSerial.toString())).contains(
                serial,
                ignoreCase = true
            )
        }

        return filtered
    }
}
