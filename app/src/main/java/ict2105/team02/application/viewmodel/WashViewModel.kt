package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.model.WashData
import java.util.*

class WashViewModel() : ViewModel() {
    var washData = MutableLiveData<WashData>()

    // init empty data here
    init{
        washData.value = WashData(
            "",
        0,
        "",
        0,
        null,
        "",
        0,
        null,
        0,
        "",
        "",
        0,
        null,
        )
    }
}