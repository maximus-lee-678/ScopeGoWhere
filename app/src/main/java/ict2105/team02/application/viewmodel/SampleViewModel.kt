package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.model.SampleData
import ict2105.team02.application.model.WashData

class SampleViewModel: ViewModel() {
    var sampleData = MutableLiveData<SampleData>()

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