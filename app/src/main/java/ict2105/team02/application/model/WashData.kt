package ict2105.team02.application.model

import androidx.lifecycle.MutableLiveData

data class WashData(
    var scopeBrand :String,
    var scopeModel :String,
    var scopeSerial :String,
    var aerModel :String,
    var aerSerial :String,
    var detergentUsed:String,
    var detergentLotNo :String,
    var filterChangeDate :String,
    var disinfectantUsed :String,
    var disinfectantLotNo :String,
    var disinfectantChanged :String,
    var scopeDryer :String,
    var scopeLevel:String,
    var remarks:String
)
