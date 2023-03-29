package ict2105.team02.application.repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ict2105.team02.application.model.*
import ict2105.team02.application.utils.*
import kotlinx.coroutines.tasks.await

private const val COLLECTION_USERS = "users"
private const val COLLECTION_ENDOSCOPES = "endoscopes"

class DataRepository {
    fun getAuthenticatedUserData(onSuccess: (User) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Firebase.firestore.collection(COLLECTION_USERS)
                .document(user.uid)
                .get()
                .addOnSuccessListener {
                    onSuccess(it.toObject(User::class.java)!!.also { u ->
                        u.email = user.email ?: ""
                    })
                }
        }
    }

    fun getEndoscopeStatistics(onSuccess: (EndoscopeStatistics) -> Unit?) {
        getAllEndoscopes { endoscopes ->
            onSuccess(
                EndoscopeStatistics(
                    endoscopes.filter {
                        it.nextSampleDate >= Utils.getTodayStartDate() && it.nextSampleDate < Utils.getTodayEndDate()
                    }.size,
                    endoscopes.size,
                    endoscopes.filter {
                        it.scopeStatus == Constants.ENDOSCOPE_CIRCULATION
                    }.size,
                    endoscopes.filter {
                        it.scopeStatus == Constants.ENDOSCOPE_WASH
                    }.size,
                    endoscopes.filter {
                        it.scopeStatus == Constants.ENDOSCOPE_SAMPLE
                    }.size,
                )
            )
        }
    }

    fun getAllEndoscopes(onSuccess: (List<Endoscope>) -> Unit) {
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).get()
            .addOnSuccessListener {
                onSuccess(it.toObjects(Endoscope::class.java))
            }
    }

    fun getEndoscope(serial: String, onSuccess: (Endoscope?) -> Unit) {
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial)
            .get()
            .addOnSuccessListener {
                onSuccess(it.toObject(Endoscope::class.java))
            }
    }

    suspend fun getEndoscopeHistory(serial: String): UiState<List<EndoscopeTransaction>> {
        return try {
            // query to get all logs for a specific scope
            val document = Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial)
            val result = document.collection("History").get().await()
            if (result != null) {
                // endoscope transaction list
                val scopeLogMutableList: MutableList<EndoscopeTransaction>? = mutableListOf()

                for (doc in result.documents) {
                    val data = doc.data

                    // to store wash data and result data in each document
                    var washDataDetail: WashData? = null
                    var resultDataDetail: ResultData? = null

                    if (data != null) {
                        Log.d("TAG", "fetching wash data ...")
                        // wash data
                        val washData: Map<String, Any>? = data["washData"] as? Map<String, Any>
                        if (washData != null) {
                            val aerModel = washData["AERModel"] as? String
                            val aerSerial = washData["AERSerial"] as? Int
                            val detergentLotNo = washData["DetergentLotNo"] as? Int
                            val detergentUsed = washData["DetergentUsed"] as? String
                            val disinfectantDate =
                                washData["DisinfectantChangedDate"] as? com.google.firebase.Timestamp
                            val disinfectantChangedDate = disinfectantDate?.toDate()
                            val disinfectantLotNo = washData["DisinfectantLotNo"] as? Int
                            val disinfectantUsed = washData["DisinfectantUsed"] as? String
                            val doneBy = washData["DoneBy"] as? String
                            val dryerLevel = washData["DryerLevel"] as? Int
                            val filterDate =
                                washData["FilterChangedDate"] as? com.google.firebase.Timestamp
                            val filterChangeDate = filterDate?.toDate()
                            val remarks = washData["Remarks"] as? String
                            val scopeDryer = washData["ScopeDryer"] as? Int
                            val washDate = washData["WashDate"] as? com.google.firebase.Timestamp
                            val washChangeDate = washDate?.toDate()

                            // creating object for wash data
                            washDataDetail = WashData(
                                AERModel = aerModel,
                                AERSerial = aerSerial,
                                DetergentLotNo = detergentLotNo,
                                DetergentUsed = detergentUsed,
                                DisinfectantChangedDate = disinfectantChangedDate,
                                DisinfectantLotNo = disinfectantLotNo,
                                DisinfectantUsed = disinfectantUsed,
                                DoneBy = doneBy,
                                DryerLevel = dryerLevel,
                                FilterChangeDate = filterChangeDate,
                                Remarks = remarks,
                                ScopeDryer = scopeDryer,
                                WashDate = washChangeDate
                            )
                        }
                        Log.d("TAG", "finish fetching wash data")

                        Log.d("TAG", "fetching result data ...")
                        // result data
                        val resultData: Map<String, Any>? = data["resultData"] as? Map<String, Any>
                        if (resultData != null) {
                            val borescope = resultData["borescope"] as? Boolean
                            val fluidAction = resultData["fluidAction"] as? String
                            val fluidComment = resultData["fluidComment"] as? String
                            val fluidResult = resultData["fluidResult"] as? Boolean
                            val quarantineRequired = resultData["quarantineRequired"] as? Boolean
                            val recordedBy = resultData["recordedBy"] as? String
                            val repeatDateMS =
                                resultData["repeatDateMS"] as? com.google.firebase.Timestamp
                            val repeatChangeDateMS = repeatDateMS?.toDate()
                            val resultDate =
                                resultData["resultDate"] as? com.google.firebase.Timestamp
                            val resultChangeDate = resultDate?.toDate()
                            val swabAction = resultData["swabAction"] as? String
                            val swabCultureComment = resultData["swabCultureComment"] as? String
                            val swabDate = resultData["swabDate"] as? com.google.firebase.Timestamp
                            val swabChangeDate = swabDate?.toDate()
                            val swabResult = resultData["swabResult"] as? Boolean
                            val swapATPRLU = resultData["swapATPRLU"] as? Int
                            val waterATPRLU = resultData["waterATPRLU"] as? Int

                            //creating object for result data
                            resultDataDetail = ResultData(
                                borescope = borescope,
                                fluidAction = fluidAction,
                                fluidComment = fluidComment,
                                fluidResult = fluidResult,
                                quarantineRequired = quarantineRequired,
                                doneBy = recordedBy,
                                repeatDateMS = repeatChangeDateMS,
                                resultDate = resultChangeDate,
                                swabAction = swabAction,
                                swabCultureComment = swabCultureComment,
                                swabDate = swabChangeDate,
                                swabResult = swabResult,
                                swabATPRLU = swapATPRLU,
                                waterATPRLU = waterATPRLU
                            )
                            Log.d("Result Data", resultData.toString())
                        }
                        var combineDetails = EndoscopeTransaction(
                            washData = washDataDetail,
                            resultData = resultDataDetail
                        )
                        scopeLogMutableList?.add(combineDetails)
                    }
                }
                if (scopeLogMutableList?.isEmpty() == false) {
                    var scopeLogList = scopeLogMutableList.toList()
                    UiState.Success(scopeLogList)
                } else {
                    UiState.Error("Scope has not been sampled!")
                }
            } else {
                UiState.Error("Scope Detail cannot be found!")
            }
        } catch (e: Exception) {
            UiState.Error("Scope cannot be found!")
        }
    }

    fun insertWashData(serial: String, docName: String, washData: WashData) {
        val data: HashMap<String, HashMap<String, Any>>
        try {
            data = hashMapOf("washData" to washData.asHashMap())

        } catch (e: NullPointerException) {
            Log.d(TAG, "Failed to insert wash data: Wash data is invalid")
            return
        }

        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial).collection("History")
            .document(docName)
            .set(data)
            .addOnSuccessListener {
                Log.d(TAG, "Firebase insert wash data success")

                // Update scope status
                updateScopeStatus(serial, Constants.ENDOSCOPE_WASH)
            }
            .addOnFailureListener { e -> Log.d(TAG, "Firebase insert wash data fail due to $e") }
    }

    fun insertSampleData(serial: String, docName: String, sampleData: ResultData) {
        val data: HashMap<String, HashMap<String, Any>>
        try {
            data = hashMapOf("resultData" to sampleData.asHashMap())

        } catch (e: NullPointerException) {
            Log.d(TAG, "Failed to insert sample result data: Result data is invalid")
            return
        }

        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial).collection("History")
            .document(docName)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Document exists, update it
                    Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial).collection("History")
                        .document(docName)
                        .update(data as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d(TAG, "Firebase update sample result data success")

                            // Update scope status
                            updateScopeStatus(serial, Constants.ENDOSCOPE_SAMPLE)
                        }
                        .addOnFailureListener { e -> Log.d(TAG, "Firebase update sample result data fail due to $e") }
                } else {
                    // Document does not exist, set it
                    Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial).collection("History")
                        .document(docName)
                        .set(data as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d(TAG, "Firebase insert sample result data success")

                            // Update scope status
                            updateScopeStatus(serial, Constants.ENDOSCOPE_SAMPLE)
                        }
                        .addOnFailureListener { e -> Log.d(TAG, "Firebase insert sample result data fail due to $e") }
                }
            }
            .addOnFailureListener { e -> Log.d(TAG, "Firebase get document fail due to $e") }
    }

    fun insertNewScope(newScope: Endoscope) {
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES)
            .document(newScope.scopeSerial.toString())
            .set(newScope)
            .addOnSuccessListener {
                Log.d("Insert New", "Success")
            }
            .addOnFailureListener { e ->
                Log.d("Insert New", "Fail due to $e")
            }
    }

    fun updateScope(scope: Endoscope) {
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(scope.scopeSerial.toString())
            .set(scope)
            .addOnSuccessListener {
                Log.d("Update Details", "Success")
            }
            .addOnFailureListener { e ->
                Log.d("Update Details", "Fail due to $e")
            }
    }

    fun updateScopeStatus(serial: String, status: String) {
        val updateScopeStatus = mapOf( "scopeStatus" to status) // Sampling -> Circulation
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial).update(updateScopeStatus)
        Log.d(TAG, "Firebase scope status update success")
    }

    fun deleteScope(serial: Int) {
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial.toString())
            .delete()
            .addOnSuccessListener {
                Log.d("Delete Scope", "Success")
            }
            .addOnFailureListener { e ->
                Log.d("Delete Scope", "Fail due to $e")
            }
    }

    /**
     * Singleton for this repository.
     */
    companion object {
        // Boilerplate-y code for singleton: the private reference to this self
        @Volatile
        private var INSTANCE: DataRepository? = null

        /**
         * Boilerplate-y code for singleton: to ensure only a single copy is ever present
         */
        fun getInstance(): DataRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = DataRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}