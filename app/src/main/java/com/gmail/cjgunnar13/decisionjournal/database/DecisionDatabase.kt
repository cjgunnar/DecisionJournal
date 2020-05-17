package com.gmail.cjgunnar13.decisionjournal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.cjgunnar13.decisionjournal.Decision

/**
 * Database file
 * built by Repository
 * contains the appropriate queries in DAO
 */
@Database(entities = [Decision::class], version = 1)
@TypeConverters(DecisionTypeConverters::class)
abstract class DecisionDatabase : RoomDatabase() {
    abstract fun decisionDao(): DecisionDao
}