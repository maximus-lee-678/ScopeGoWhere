package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.model.Endoscope
import okhttp3.*
import java.io.IOException

class EquipmentListViewModel : ViewModel() {
    val equipments = MutableLiveData<List<Endoscope>>()

    private val client = OkHttpClient()

    fun fetchEquipments(onFinish: (() -> Unit)? = null) {
        val request = Request.Builder().url("https://swiftingduster.com/api/hci/scopes").build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val json = it.body()!!.string()
                    val data = Utils.parseJSONAllScope(json)
                    equipments.postValue(data)

                    if (onFinish != null) {
                        onFinish()
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }
}