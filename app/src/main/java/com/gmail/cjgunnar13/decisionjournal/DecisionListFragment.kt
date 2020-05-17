package com.gmail.cjgunnar13.decisionjournal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Shows a list of decisions in order retrieved from database
 * displays in recyclerview
 * shows textview with message if nothing to show
 */
class DecisionListFragment : Fragment() {
    private lateinit var decisionRecyclerView: RecyclerView

    private var adapter: DecisionAdapter = DecisionAdapter(emptyList())

    private val model: DecisionListViewModel by lazy {
        ViewModelProvider(this@DecisionListFragment).get(DecisionListViewModel::class.java)
    }

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

    /**
     * update adapter on recyclerview to show new list
     */
    private fun updateUI(decisions: List<Decision>) {
        adapter = DecisionAdapter(decisions)
        decisionRecyclerView.adapter = adapter
    }

    //RECYCLER
    private inner class DecisionHolder(view: View) : RecyclerView.ViewHolder(view) {
        //UI elements

        //decision to display
        private var decision: Decision? = null

        fun bind(decision: Decision) {
            this.decision = decision

            //update UI elements
        }
    }

    private inner class DecisionAdapter(var decisions: List<Decision>) :
        RecyclerView.Adapter<DecisionHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecisionHolder {
            val view = layoutInflater.inflate(R.layout.list_item_decision, parent)
            return DecisionHolder(view)
        }

        override fun getItemCount() = decisions.size

        override fun onBindViewHolder(holder: DecisionHolder, position: Int) {
            holder.bind(decisions[position])
        }

    }

    companion object {
        fun newInstance() : DecisionListFragment {
            return DecisionListFragment()
        }
    }
}
