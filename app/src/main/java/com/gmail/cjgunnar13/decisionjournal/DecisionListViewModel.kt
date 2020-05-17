package com.gmail.cjgunnar13.decisionjournal

import androidx.lifecycle.ViewModel

class DecisionListViewModel : ViewModel()  {
    //get singleton repository
    private val decisionRepository = DecisionRepository.get()

    //access methods
    val decisionsLiveData = decisionRepository.getDecisions()
}