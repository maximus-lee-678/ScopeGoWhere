package ict2105.team02.application.repo

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.EndoscopeTransaction
import ict2105.team02.application.model.History
import ict2105.team02.application.model.WashData
import ict2105.team02.application.utils.Utils

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

    fun getEndoscopeHistory(serial:String, onSuccess: (List<History>) -> Unit){
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial).collection("History").get()
            .addOnSuccessListener {
                for (logs in it){
                    Log.d("History", logs.data.toString())
                }
                onSuccess(it.toObjects(History::class.java))
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