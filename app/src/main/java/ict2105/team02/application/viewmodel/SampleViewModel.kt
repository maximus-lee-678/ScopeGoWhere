package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.model.SampleData
import ict2105.team02.application.model.WashData

class SampleViewModel: ViewModel() {
    var sampleData = MutableLiveData<SampleData>()
    // Fluid Result
    val dateOfFluidResult = MutableLiveData<String>();
    val fluidResult = MutableLiveData<String>();
    val actionFluid = MutableLiveData<String>();
    val cultureCommentFluid = MutableLiveData<String>();

    // Swab Result
    val dateOfSwabResult = MutableLiveData<String>();
    val swabResult = MutableLiveData<String>();
    val actionSwab = MutableLiveData<String>();
    val cultureCommentSwab = MutableLiveData<String>();

    // Repeat of MS
    val quarantinePeriod = MutableLiveData<String>();
    val repeatDateMS = MutableLiveData<String>();
    val borescope = MutableLiveData<Boolean>(); // dropDown

    // ATP
    val atpWaterRLU = MutableLiveData<String>();
    val atpSwapRLU = MutableLiveData<String>();
    init {
            sampleData.value = SampleData(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
    }
}