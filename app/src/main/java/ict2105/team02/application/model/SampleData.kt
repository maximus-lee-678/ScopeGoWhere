package ict2105.team02.application.model

import androidx.lifecycle.MutableLiveData
import java.util.Date

data class SampleData (
    var dateOfFluidResult : Date,
    var fluidResult : String,
    var actionFluid : String,
    var cultureCommentFluid : String,

    // Swab Result
    var dateOfSwabResult: Date,
    var swabResult : String,
    var actionSwab : String,
    var cultureCommentSwab : String,

    // Repeat of MS
    var quarantinePeriod : String,
    var repeatDateMS : Date,
    var borescope : String, // dropDown

    // ATP
    var atpWaterRLU : String,
    var atpSwabRLU : String,
)