package com.gmail.cjgunnar13.decisionjournal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.gmail.cjgunnar13.decisionjournal.database.DecisionDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "decision_database"

/**
 * Where ViewModels can access the data stored
 * use DecisionRepository.get() to access
 * SINGLETON, loaded on application start using app context
 * must be initialized before use
 */
class DecisionRepository private constructor(context: Context) {
    //BUILD DATABASE AND DATABASE ACCESS OBJECT
    private val database: DecisionDatabase = Room.databaseBuilder(
        context,
        DecisionDatabase::class.java,
        DATABASE_NAME
    )
        .build()
    private val decisionDao = database.decisionDao()

    private val executor = Executors.newSingleThreadExecutor()

    //REPOSITORY ACCESS FUNCTIONS
    fun getDecisions(): LiveData<List<Decision>> = decisionDao.getDecisions()
    fun getDecision(uuid: UUID): LiveData<Decision?> = decisionDao.getDecision(uuid)

    //perform in background thread
    fun updateDecision(decision: Decision) =
        executor.execute { decisionDao.updateDecision(decision) }

    fun addDecision(decision: Decision) = executor.execute { decisionDao.addDecision(decision) }

    fun deleteDecision(decisionId: UUID) =
        executor.execute { decisionDao.deleteDecision(decisionId) }


    //COMPANION OBJECT FOR SINGLETON
    companion object {
        //create as singleton
        private var INSTANCE: DecisionRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DecisionRepository(context)
            }
        }

        //get singleton
        fun get(): DecisionRepository {
            return INSTANCE
                ?: throw IllegalStateException("DecisionRepository must be initialized before accessing!")
        }
    }
}