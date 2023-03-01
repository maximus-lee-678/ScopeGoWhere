//package ict2105.team02.application.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import ict2105.team02.application.model.Endoscope
//import ict2105.team02.application.repo.DataRepository
//import ict2105.team02.application.utils.UiState
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class EquipLogViewModel : ViewModel(){
//
//    private var equipRepository = DataRepository()
//
//    private var _equipDetail = MutableLiveData<UiState<Endoscope>>()
//    val equipDetail : LiveData<UiState<Endoscope>> = _equipDetail
//
//    fun getScopeLog(endoscope: Endoscope){
//        _equipDetail.postValue(UiState.Loading())
//        viewModelScope.launch (Dispatchers.Main){
//            val result = equipRepository.getScopeLog(endoscope)
//            _equipDetail.postValue(result)
//
//            // DEBUG
////            Log.d("TAG", result.data?.model.toString())
////            Log.d("TAG", result.data?.serial.toString())
////            Log.d("TAG", result.data?.type.toString())
////            Log.d("TAG", result.data?.status.toString())
////            Log.d("TAG", result.data?.nextSample.toString())
////            Log.d("TAG", result.data?.history.toString())
//        }
//    }
//
//}