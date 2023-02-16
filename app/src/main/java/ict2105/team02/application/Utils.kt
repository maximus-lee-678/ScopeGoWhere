package ict2105.team02.application

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import ict2105.team02.application.model.Endoscope

class Utils {
    companion object {
        @JvmStatic
        fun parseJSONAllScope(json: String): List<Endoscope> {
            val jsonObj = JsonParser.parseString(json).asJsonObject
            val jsonArray = jsonObj["data"].asJsonArray
            val listType = object : TypeToken<List<Endoscope?>?>() {}.type
            return Gson().fromJson(jsonArray, listType)
        }

        @JvmStatic
        fun parseJSONSingleScope(json: String): Endoscope {
            val jsonObj = JsonParser.parseString(json).asJsonObject
            val jsonData = jsonObj["data"]
            return Gson().fromJson(jsonData, Endoscope::class.java)
        }
    }
}