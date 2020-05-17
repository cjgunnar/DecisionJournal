package com.gmail.cjgunnar13.decisionjournal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.*

/**
 * Fragment displaying details of an decision object for edit/view
 * create with newInstance
 */
class DecisionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_decision, container, false)
        return view
    }

    companion object {
        fun newInstance(decisionID : UUID) : DecisionFragment {
            return DecisionFragment()
        }
    }
}