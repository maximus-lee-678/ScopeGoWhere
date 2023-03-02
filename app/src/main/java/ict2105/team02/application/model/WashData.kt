package ict2105.team02.application.model

import java.util.*

data class WashData (
    val aerModel: String?,
    val aerSerial: Int?,
    val detergentUsed: String?,
    val detergentLotNo: Int?,
    val filterChangeDate: Date?,
    val disinfectantUsed: String?,
    val disinfectantLotNo: Int?,
    val disinfectantChangedDate: Date?,
    val scopeDryer: Int?,
    val doneBy: String?,
    val remarks: String?,
    val dryerLevel: Int?,
    val washDate: Date?,
        )