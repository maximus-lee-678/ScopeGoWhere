package ict2105.team02.application.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import ict2105.team02.application.model.Endoscope

class Utils {
    companion object {
        fun parseJSONAllScope(json: String): List<Endoscope> {
            val jsonObj = JsonParser.parseString(json).asJsonObject
            val jsonArray = jsonObj["data"].asJsonArray
            val listType = object : TypeToken<List<Endoscope?>?>() {}.type
            return getGson().fromJson(jsonArray, listType)
        }

        fun parseJSONSingleScope(json: String): Endoscope {
            val jsonObj = JsonParser.parseString(json).asJsonObject
            val jsonData = jsonObj["data"]
            return getGson().fromJson(jsonData, Endoscope::class.java)
        }

        private fun getGson() = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").create()
    }
}
