package com.gmail.cjgunnar13.decisionjournal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.gmail.cjgunnar13.decisionjournal.Decision
import java.util.*

/**
 * DATABASE ACCESS OBJECT
 * contains queries for the database
 * interface implemented and built by Room in Repository
 */
@Dao
interface DecisionDao {

    //getDecisions
    @Query("SELECT * FROM decision")
    fun getDecisions(): LiveData<List<Decision>>

    //getDecision(id)
    @Query("SELECT * FROM decision WHERE ID=(:uuid)")
    fun getDecision(uuid: UUID): LiveData<Decision?>

    //getDecision with review after Date

    //updateDecision

    //addDecision(decision)
}