package ict2105.team02.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import ict2105.team02.application.model.Endoscope
import okhttp3.*
import java.io.IOException

class ScopeDetailViewModel : ViewModel() {
    val scopeDetail = MutableLiveData<Endoscope>()

    private val client = OkHttpClient()

    fun fetchScopeDetail(serial: String, onFinish: (() -> Unit)? = null) {
        val request = Request.Builder()
            .url("https://swiftingduster.com/api/hci/scopes?serial=$serial").build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val json = it.body()!!.string()
                    val jsonObj = JsonParser.parseString(json).asJsonObject
                    val jsonData = jsonObj["data"]
                    val data = Gson().fromJson(jsonData, Endoscope::class.java)
                    scopeDetail.postValue(data)

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