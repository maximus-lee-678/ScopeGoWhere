package ict2105.team02.application.model

import java.util.Date

data class Endoscope(
    val nextSampleDate: Date = Date(),
    var scopeBrand: String = "",
    val scopeModel: String = "",
    var scopeSerial: Int = 0,
    val scopeStatus: String = "",
    val scopeType: String = "",
)