package com.gmail.cjgunnar13.decisionjournal

import android.app.Application

/**
 * Created on application start (set in manifest)
 * Initializes singletons (the decision repository)
 */
class DecisionJournalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DecisionRepository.initialize(this)
    }
}