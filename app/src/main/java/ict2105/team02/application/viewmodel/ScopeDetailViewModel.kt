package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.model.Endoscope
import okhttp3.*
import java.io.IOException

class ScopeDetailViewModel : ViewModel() {
    val _scopeDetail = MutableLiveData<Endoscope>()
    val scopeDetail:LiveData<Endoscope> = _scopeDetail

    private val client = OkHttpClient()

    fun fetchScopeDetail(serial: String, onFinish: (() -> Unit)? = null) {
        val request = Request.Builder()
            .url("https://swiftingduster.com/api/hci/scopes?serial=$serial").build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val json = it.body()!!.string()
                    val data = Utils.parseJSONSingleScope(json)
                    _scopeDetail.postValue(data)

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