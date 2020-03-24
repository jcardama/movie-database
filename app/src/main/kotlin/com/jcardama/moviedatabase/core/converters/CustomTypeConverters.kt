package com.universales.agentes.core.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class CustomTypeConverters {
    @TypeConverter
    fun fromString(stringListString: String): List<String>? {
        return Gson().fromJson(stringListString, Array<String>::class.java)?.toList()
    }

    @TypeConverter
    fun toString(stringList: List<String>?): String? {
        return Gson().toJson(stringList)
    }
}
