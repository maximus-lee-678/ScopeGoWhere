package ict2105.team02.application.model

import com.google.firebase.Timestamp
import java.util.*

data class WashData (
    val AERModel: String?,
    val AERSerial: Int?,
    val DetergentUsed: String?,
    val DetergentLotNo: Int?,
    val FilterChangeDate: Date?,
    val DisinfectantUsed: String?,
    val DisinfectantLotNo: Int?,
    val DisinfectantChangedDate: Date?,
    val ScopeDryer: Int?,
    val DoneBy: String?,
    val Remarks: String?,
    val DryerLevel: Int?,
    val WashDate: Date?
    )