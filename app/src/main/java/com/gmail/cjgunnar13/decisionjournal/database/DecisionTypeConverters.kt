package com.gmail.cjgunnar13.decisionjournal.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

/**
 * Defines TypeConverters for non primitive types in database
 */
class DecisionTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toFields(fields: MutableList<String>?): String? {
        return Gson().toJson(fields)
    }

    @TypeConverter
    fun fromFields(fields: String?): MutableList<String>? {
        return Gson().fromJson(fields, Array<String>::class.java).toMutableList()
    }

    /*
    @TypeConverter
    fun toFieldsAnswers(fields: MutableList<String>?): String? {
        return Gson().toJson(fields)
    }

    @TypeConverter
    fun fromFieldsAnswers(fields: String?): MutableList<String>? {
        return Gson().fromJson(fields, Array<String>::class.java).toMutableList()
    }
     */
}