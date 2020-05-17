package com.gmail.cjgunnar13.decisionjournal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

/**
 * Launched and displayed on start of app
 * Manages fragments and their callbacks/communications
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set up fragments
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fl_fragment_container)
        if(currentFragment == null) {
            //create a new list fragment
            val listFragment = DecisionListFragment.newInstance()
            //add to stack and display
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fl_fragment_container, listFragment)
                .commit()
        }
    }
}