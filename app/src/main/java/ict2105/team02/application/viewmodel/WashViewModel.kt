package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.model.WashData

class WashViewModel() : ViewModel() {
    var washData = MutableLiveData<WashData>()

    // init empty data here
    init{
        washData.value = WashData(
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
            "",
            ""
        )
    }
}