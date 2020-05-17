package com.gmail.cjgunnar13.decisionjournal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val ARG_DECISION_ID = "arg_decision_id"

/**
 * Fragment displaying details of an decision object for edit/view
 * create with newInstance
 */
class DecisionFragment : Fragment() {

    private lateinit var decision: Decision

    //UI components
    private lateinit var nameEditText: EditText

    private val model: DecisionViewModel by lazy {
        ViewModelProvider(this@DecisionFragment).get(DecisionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load decision that was passed into newInstance
        decision = Decision()
        val id = arguments?.getSerializable(ARG_DECISION_ID) as UUID
        //this updates observer in model
        model.loadDecision(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_decision, container, false)

        //initialize views based on R.id
        nameEditText = view.findViewById(R.id.et_decision_name) as EditText

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //listen to see if this fragment has a change of decision to display and update if it does
        model.decisionLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { decision ->
                decision?.let {
                    this.decision = decision
                    //update UI
                }
            }
        )
    }

    companion object {
        /**
         * Create a new instance, displaying the decision with decisionID
         */
        fun newInstance(decisionID : UUID) : DecisionFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DECISION_ID, decisionID)
            }

            return DecisionFragment().apply {
                arguments = args
            }
        }
    }
}