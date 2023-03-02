package ict2105.team02.application.model

import java.util.Date

data class Endoscope(
    val NextSampleDate: Date? = null,
    var ScopeBrand: String = "",
    val ScopeModel: String = "",
    var ScopeSerial: Int = 0,
    val ScopeStatus: String = "",
    val ScopeType: String = "",
)