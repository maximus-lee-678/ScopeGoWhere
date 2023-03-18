package ict2105.team02.application.model

import java.util.*

data class WashData(
    val AERModel: String? = null,
    val AERSerial: Int? = null,

    val DetergentUsed: String? = null,
    val DetergentLotNo: Int? = null,
    val FilterChangeDate: Date? = null,

    val DisinfectantUsed: String? = null,
    val DisinfectantLotNo: Int? = null,
    val DisinfectantChangedDate: Date? = null,

    val ScopeDryer: Int? = null,
    val DryerLevel: Int? = null,
    val Remarks: String? = null,

    val WashDate: Date? = null,
    val DoneBy: String? = null,
)