package ict2105.team02.application.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.EndoscopeTransaction
import ict2105.team02.application.model.Schedule
import ict2105.team02.application.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class EquipRepository {
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    // get log of a specific scope
    suspend fun getScopeLog(endoscope: Endoscope) : UiState<Endoscope> {

        return withContext(Dispatchers.IO){
            try {
                val document = database.collection("endoscopes")
                    .whereEqualTo("model", endoscope.model)
                    .whereEqualTo("serial", endoscope.serial)
                    .get().await().documents[0]
                val data = document.data
                if(data != null){

                    // get data from the firestore
                    val model = data["model"] as String
                    val serial = data["serial"] as String
                    val type = data["type"] as String
                    val status = data["status"] as String
                    val dateNextSample = data["nextSample"] as com.google.firebase.Timestamp
                    val nextSample = dateNextSample.toDate()


                    // get history (list of map) from firestore
                    val historyArrayList: ArrayList<Map<String, Any>>? = data["history"] as? ArrayList<Map<String, Any>>
                    Log.d("TAG", "AFTER FETCHING FROM FIRESTORE ABOUT THE HISTORY")
                    val historyMutableList: MutableList<EndoscopeTransaction>? = mutableListOf<EndoscopeTransaction>()
                    var history : List<EndoscopeTransaction>? = null

                    // convert the data to list<EndoscopeTransaction> to put in the object
                    // get every data in the map and create new object of endoscope transaction
                    // convert the mutable list to list
                    if (historyArrayList != null) {
                        for (map in historyArrayList){
                            val value1 = map["date"] as com.google.firebase.Timestamp
                            val historyDate = value1.toDate()

                            val value2 = map["doneBy"] as String
                            val value3 = map["transaction"] as String

                            var historyData = EndoscopeTransaction(date = historyDate, doneBy = value2,
                            transaction = value3)

                            historyMutableList?.add(historyData)
                        }

                        history = historyMutableList
                    }

                    val detail = Endoscope(model = model, serial = serial, type = type, status = status,
                    nextSample = nextSample, history = history)

                    // DEBUG
//                    Log.d("TAG", detail.model)
//                    Log.d("TAG", detail.serial)
//                    Log.d("TAG", detail.type)
//                    Log.d("TAG", detail.status)
//                    Log.d("TAG", detail.nextSample.toString())
//                    Log.d("TAG", detail.history.toString())

                    UiState.Success(detail)
                }else{
                    UiState.Error("Endoscope cannot be found!")
                }
            }catch(e: Exception){
                UiState.Error("Endoscope cannot be found!")
            }
        }
    }



}