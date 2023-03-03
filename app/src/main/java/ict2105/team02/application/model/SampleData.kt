package ict2105.team02.application.model

import androidx.lifecycle.MutableLiveData

data class SampleData (
    var dateOfFluidResult : String,
    var fluidResult : String,
    var actionFluid : String,
    var cultureCommentFluid : String,

    // Swab Result
    var dateOfSwabResult: String,
    var swabResult : String,
    var actionSwab : String,
    var cultureCommentSwab : String,

    // Repeat of MS
    var quarantinePeriod : String,
    var repeatDateMS : String,
    var borescope : String, // dropDown

    // ATP
    var atpWaterRLU : String,
    var atpSwabRLU : String,
)