package com.gmail.cjgunnar13.decisionjournal

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "DecisionListFragment"

/**
 * Shows a list of decisions in order retrieved from database
 * displays in recyclerview
 * shows textview with message if nothing to show
 */
class DecisionListFragment : Fragment() {
    //UI elements
    private lateinit var decisionRecyclerView: RecyclerView

    private var adapter: DecisionAdapter = DecisionAdapter(emptyList())

    private val model: DecisionListViewModel by lazy {
        ViewModelProvider(this@DecisionListFragment).get(DecisionListViewModel::class.java)
    }

    //INTER-FRAGMENT COMMUNICATION
    interface Callbacks {
        fun onDecisionSelected(uuid: UUID)
    }

    private var callbacks: Callbacks? = null

    //MENU
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = context as Callbacks
    }

    //LIFECYCLE FUNCTIONS
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_decision_list, container, false)

        //initialize UI elements
        decisionRecyclerView = view.findViewById(R.id.rv_fdl_decision_list)
        decisionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DecisionListFragment.adapter
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //create data listeners

        //observe decision list and update UI if changed
        model.decisionsLiveData.observe(
            viewLifecycleOwner,
            Observer { decisions ->
                decisions?.let {
                    updateUI(it)
                }
            }
        )
    }

    //MENU
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_decisions_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_new_decision -> {
                val decision = Decision()
                model.addDecision(decision)
                callbacks?.onDecisionSelected(decision.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //HELPER
    /**
     * update adapter on recyclerview to show new list
     */
    private fun updateUI(decisions: List<Decision>) {
//        Log.i(TAG, "Showing ${decisions.size} elements")
//        for (element in decisions) {
//            Log.d(TAG, "Element: $element")
//        }

        adapter = DecisionAdapter(decisions)
        decisionRecyclerView.adapter = adapter
    }

    //RECYCLER
    private inner class DecisionHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        //UI elements
        val nameTextView: TextView = view.findViewById(R.id.tv_lid_name)

        //decision to display
        private var decision: Decision? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(decision: Decision) {
            this.decision = decision

            //update UI elements
            nameTextView.text =
                if (decision.name.isNotBlank()) decision.name else getString(R.string.unnamed)
        }

        override fun onClick(p0: View?) {
            decision?.let {
                callbacks?.onDecisionSelected(it.id)
            }
        }
    }

    private inner class DecisionAdapter(var decisions: List<Decision>) :
        RecyclerView.Adapter<DecisionHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecisionHolder {
            val view = layoutInflater.inflate(R.layout.list_item_decision, parent, false)
            return DecisionHolder(view)
        }

        override fun getItemCount() = decisions.size

        override fun onBindViewHolder(holder: DecisionHolder, position: Int) {
            holder.bind(decisions[position])
        }

    }

    //STATIC/INSTANTIATION
    companion object {
        fun newInstance() : DecisionListFragment {
            return DecisionListFragment()
        }
    }
}
