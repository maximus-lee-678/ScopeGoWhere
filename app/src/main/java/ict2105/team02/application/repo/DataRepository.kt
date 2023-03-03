package ict2105.team02.application.repo

import android.util.Log
import com.google.android.gms.common.util.ArrayUtils
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ict2105.team02.application.model.*
import ict2105.team02.application.utils.UiState
import ict2105.team02.application.utils.Utils
import kotlinx.coroutines.tasks.await

private const val COLLECTION_ENDOSCOPES = "endoscopes"

class DataRepository {
    fun getTodayScheduledEndoscopes(onSuccess: (List<Endoscope>) -> Unit) {
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES)
            .whereGreaterThan("NextSampleDate", Utils.getTodayStartDate())
            .whereLessThan("NextSampleDate", Utils.getTodayEndDate())
            .get()
            .addOnSuccessListener {
                onSuccess(it.toObjects(Endoscope::class.java))
            }
    }

    fun getAllEndoscopes(onSuccess: (List<Endoscope>) -> Unit) {
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).get()
            .addOnSuccessListener {
                onSuccess(it.toObjects(Endoscope::class.java))
            }
    }
//    Implement if and only if recyclerView doesn't have to be to Equipment
//    fun getAllEndoscopesSchedule(onSuccess: (List<Schedule>) -> Unit) {
//        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).get()
//            .addOnSuccessListener {
//                onSuccess(it.toObjects(Schedule::class.java))
//            }
//    }
    fun getEndoscope(serial: String, onSuccess: (Endoscope?) -> Unit) {
        Log.d("GET ENDOSCOPE", serial)
        lateinit var testScope:Endoscope
        try {
            Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial)
                .get()
                .addOnSuccessListener {
                    Log.d("TAG", it.toString())
                    onSuccess(it.toObject(Endoscope::class.java))
                    testScope = it.toObject(Endoscope::class.java)!!
                    Log.d("Test Endoscope", testScope.toString())
                }
        } catch (ex: Exception){
            Log.d("TAG", ex.toString())
        }
    }

    suspend fun getEndoscopeHistory(serial:String): UiState<List<EndoscopeTransaction>>{
        return try {
            // query to get all logs for a specific scope
            val document = Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial)
            val result = document.collection("History").get().await()
            if(result != null){
                // endoscope transaction list
                var scopeLogMutableList: MutableList<EndoscopeTransaction>? = mutableListOf<EndoscopeTransaction>()

                for(doc in result.documents){
                        val data = doc.data

                        // to store wash data and result data in each document
                        var washDataDetail: WashData? = null
                        var resultDataDetail: ResultData? = null

                        if(data != null){
                            Log.d("TAG", "fetching wash data ...")
                            // wash data
                            val washData: Map<String, Any>? = data["washData"] as? Map<String, Any>
                            if (washData != null){
                                val aerModel = washData["AERModel"] as? String
                                val aerSerial = washData["AERSerial"] as? Int
                                val detergentLotNo = washData["DetergentLotNo"] as? Int
                                val detergentUsed = washData["DetergentUsed"] as? String
                                val disinfectantDate = washData["DisinfectantChangedDate"] as? com.google.firebase.Timestamp
                                val disinfectantChangedDate = disinfectantDate?.toDate()
                                val disinfectantLotNo = washData["DisinfectantLotNo"] as? Int
                                val disinfectantUsed = washData["DisinfectantUsed"] as? String
                                val doneBy = washData["DoneBy"] as? String
                                val dryerLevel = washData["DryerLevel"] as? Int
                                val filterDate = washData["FilterChangedDate"] as? com.google.firebase.Timestamp
                                val filterChangeDate = filterDate?.toDate()
                                val remarks = washData["Remarks"] as? String
                                val scopeDryer = washData["ScopeDryer"] as? Int
                                val washDate = washData["WashDate"] as? com.google.firebase.Timestamp
                                val washChangeDate = washDate?.toDate()

                                // creating object for wash data
                                washDataDetail = WashData(AERModel = aerModel, AERSerial = aerSerial, DetergentLotNo = detergentLotNo,
                                    DetergentUsed = detergentUsed, DisinfectantChangedDate = disinfectantChangedDate,
                                    DisinfectantLotNo = disinfectantLotNo, DisinfectantUsed = disinfectantUsed, DoneBy = doneBy,
                                    DryerLevel = dryerLevel, FilterChangeDate = filterChangeDate, Remarks = remarks,
                                    ScopeDryer = scopeDryer, WashDate = washChangeDate)
                            }
                            Log.d("TAG", "finish fetching wash data")

                            Log.d("TAG", "fetching result data ...")
                            // result data
                            val resultData: Map<String, Any>? = data["resultData"] as? Map<String, Any>
                            if (resultData != null){
                                val borescope = resultData["Borescope"] as? Boolean
                                val fluidAction = resultData["FluidAction"] as? String
                                val fluidComment = resultData["FluidComment"] as? String
                                val fluidResult = resultData["FluidResult"] as? Boolean
                                val quarantineRequired = resultData["QuarantineRequired"] as? Boolean
                                val recordedBy = resultData["RecordedBy"] as? String
                                val repeatDateMS = resultData["RepeatDateMS"] as? com.google.firebase.Timestamp
                                val repeatChangeDateMS = repeatDateMS?.toDate()
                                val resultDate = resultData["ResultDate"] as? com.google.firebase.Timestamp
                                val resultChangeDate = resultDate?.toDate()
                                val swabAction = resultData["SwabAction"] as? String
                                val swabCultureComment = resultData["SwabCultureComment"] as? String
                                val swabDate = resultData["SwabDate"] as? com.google.firebase.Timestamp
                                val swabChangeDate = swabDate?.toDate()
                                val swabResult = resultData["SwabResult"] as? Boolean
                                val swapATPRLU = resultData["SwapATPRLU"] as? Int
                                val waterATPRLU = resultData["WaterATPRLU"] as? Int

                                //creating object for result data
                                resultDataDetail = ResultData(borescope = borescope, fluidAction = fluidAction,
                                    fluidComment = fluidComment, fluidResult = fluidResult, quarantineRequired = quarantineRequired,
                                    recordedBy = recordedBy, repeatDateMS = repeatChangeDateMS, resultDate = resultChangeDate,
                                    swabAction = swabAction, swabCultureComment = swabCultureComment, swabDate = swabChangeDate,
                                    swabResult = swabResult, swabATPRLU = swapATPRLU, waterATPRLU = waterATPRLU)

                                Log.d("TAG", "finish fetching result data")
                            }

                            var combineDetails = EndoscopeTransaction(washData = washDataDetail, resultData = resultDataDetail)
                            scopeLogMutableList?.add(combineDetails)
                        }

                    }
                if (scopeLogMutableList?.isEmpty() == false) {
                    var scopeLogList = scopeLogMutableList.toList()
                    UiState.Success(scopeLogList)
                }else{
                    UiState.Error("Scope has not been sampled!")
                }
            }else{
                UiState.Error("Scope Detail cannot be found!")
            }
        }catch(e: Exception){
            UiState.Error("Scope cannot be found!")
        }
    }

    fun insertWashData(serial: String, docName: String, washData:HashMap<String, Any?>){
        val data = hashMapOf(
            "washData" to washData
        )
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial).collection("History").document(docName)
            .set(data).addOnSuccessListener {
                Log.d("Insert", "Success")
            }
            .addOnFailureListener {
                Log.d("Insert", "Fail")
            }
    }
}