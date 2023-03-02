package ict2105.team02.application.repo

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.EndoscopeTransaction
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
        try {
            Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document("00" + serial.toString())
                .get()
                .addOnSuccessListener {
                    Log.d("TAG", it.toString())
                    onSuccess(it.toObject(Endoscope::class.java))
                }
        } catch (ex: Exception){
            Log.d("TAG", ex.toString())
        }
    }

    fun getEndoscopeHistory(serial:String, result: (EndoscopeTransaction?) -> Unit){
        Firebase.firestore.collection(COLLECTION_ENDOSCOPES).document(serial).get()
            .addOnSuccessListener {
                val data = it.data
            }
    }


}