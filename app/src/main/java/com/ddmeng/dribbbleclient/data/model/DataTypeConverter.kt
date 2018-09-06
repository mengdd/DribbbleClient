package com.ddmeng.dribbbleclient.data.model

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

object DataTypeConverter {
    @TypeConverter
    @JvmStatic
    fun stringToStringMap(data: String?): Map<String, String>? {
        return data?.let {
            Gson().fromJson(it, object : TypeToken<Map<String, String>>() {}.type)
        }
    }

    @TypeConverter
    @JvmStatic
    fun stringMapToString(maps: Map<String, String>?): String? {
        return Gson().toJson(maps)
    }

    @TypeConverter
    @JvmStatic
    fun stringToAnyList(data: String?): List<String>? {
        return data?.let {
            Gson().fromJson(it, object : TypeToken<List<JsonElement>>() {}.type)
        }
    }

    @TypeConverter
    @JvmStatic
    fun anyListToString(list: List<String>?): String? {
        return Gson().toJson(list)
    }
}
