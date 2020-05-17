package com.gmail.cjgunnar13.decisionjournal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

/**
 * The ViewModel for DecisionFragment
 */
class DecisionViewModel : ViewModel() {
    private val decisionRepository = DecisionRepository.get()
    private val decisionIdData = MutableLiveData<UUID>()
    var decisionLiveData: LiveData<Decision?> =
        Transformations.switchMap(decisionIdData) { decisionId ->
            decisionRepository.getDecision(decisionId)
        }

    fun loadDecision(decisionId: UUID) {
        decisionIdData.value = decisionId
    }

    fun saveDecision(decision: Decision) = decisionRepository.updateDecision(decision)


}