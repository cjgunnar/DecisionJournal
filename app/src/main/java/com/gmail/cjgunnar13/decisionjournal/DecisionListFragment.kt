package com.gmail.cjgunnar13.decisionjournal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
 * Shows a list of decisions in order retrieved from database
 * displays in recyclerview
 * shows textview with message if nothing to show
 */
class DecisionListFragment : Fragment() {
    private lateinit var decisionRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_decision_list, container, false)

        //create elements...

        return view
    }

    companion object {
        fun newInstance() : DecisionListFragment {
            return DecisionListFragment()
        }
    }
}