package com.gmail.cjgunnar13.decisionjournal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.text.DateFormat
import java.util.*

private const val ARG_DECISION_ID = "arg_decision_id"

private const val REQUEST_DATE = 0

private const val DIALOG_DATE = "dialog_date"

/**
 * Fragment displaying details of an decision object for edit/view
 * create with newInstance
 */
class DecisionFragment : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var decision: Decision

    //UI components
    private lateinit var nameEditText: EditText
    private lateinit var dateButton: Button
    //private lateinit var fieldListView: ExpandableListView

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
        dateButton = view.findViewById(R.id.b_decision_date_picker) as Button
        //fieldListView = view.findViewById(R.id.elv_field_questions)

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
                    updateUI()
                }
            }
        )
    }

    private fun updateUI() {
        nameEditText.setText(decision.name)
        dateButton.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(decision.date)
    }

    override fun onStart() {
        super.onStart()

        //create UI observers to update as data is entered
        val nameWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                decision.name = sequence.toString()
            }
        }
        nameEditText.addTextChangedListener(nameWatcher)

        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(decision.date).apply {
                setTargetFragment(this@DecisionFragment, REQUEST_DATE)
                show(this@DecisionFragment.parentFragmentManager, DIALOG_DATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        model.saveDecision(decision)
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

    override fun onDateSelected(date: Date) {
        decision.date = date
        updateUI()
    }
}
