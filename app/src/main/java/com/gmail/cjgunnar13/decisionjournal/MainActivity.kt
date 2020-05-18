package com.gmail.cjgunnar13.decisionjournal

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import java.util.*

private const val TAG = "MainActivity"

/**
 * Launched and displayed on start of app
 * Manages fragments and their callbacks/communications
 */
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener,
    DecisionListFragment.Callbacks {

    //LIFECYCLE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set up shared preferences listening
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

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

    override fun onDestroy() {
        //remove shared preferences registering
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    //SETTINGS
    override fun onSharedPreferenceChanged(sp: SharedPreferences?, key: String?) {
        Log.v(TAG, "Preference changed: $key to ${sp?.getString(key, "")}")
        when (key) {
            "theme" -> {
                when (sp?.getString("theme", getString(R.string.system_choice_value))) {
                    getString(R.string.dark_value) -> AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                    getString(R.string.light_value) -> AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    //MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_fragment_container, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //CALLBACKS
    override fun onDecisionSelected(uuid: UUID) {
        val fragment = DecisionFragment.newInstance(uuid)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
