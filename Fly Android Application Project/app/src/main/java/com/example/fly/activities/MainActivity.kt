package com.example.fly.activities
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fly.R
import android.content.Context
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // UI FIELDS
    private lateinit var bottomNavigation: BottomNavigationView

    private var counter = STATE_COUNTER_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        setContentView(R.layout.activity_main)


        // initialize the views
        initializeViews()

        // initialize bottom nav view
        setupBottomNavigation()

        // initialize the tool bar
        setupToolbar()

        // loads data from using the method "loadAppStateData()"
        savedInstanceState?.let {
            loadAppStateData()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop called")

        // when the app goes into onStop in the life cycle, it persist the data
        saveAppStateData()

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity", "onRestart called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy called")
    }


    // method for initializing views
    private fun initializeViews() {
        bottomNavigation = findViewById(R.id.view_bottom_nav)
    }

    // method for initializing tool bar
    private fun setupToolbar() {
        // sets up the toolbar
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.activity_toolbar)
        setSupportActionBar(toolbar)
        // hides the toolbar title
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }


    // method for handling bottom navigation
    private fun setupBottomNavigation() {
        // default activity
        bottomNavigation.selectedItemId = R.id.menu_home

        // nav listener
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    true
                }
                R.id.menu_search_flights -> {
                    val intent = Intent(this, SearchFlightsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_saved_flights -> {
                    val intent = Intent(this, SavedFlightsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }


    // method for SAVING APP STATE DATA to SharedPreferences
    private fun saveAppStateData() {

        val sharedPrefs = getSharedPreferences("app_state", Context.MODE_PRIVATE)

        with(sharedPrefs.edit()) {
            putInt(STATE_COUNTER, counter)
            apply()
        }
    }


    // method for LOADING APP STATE DATA from sharedprefs
    private fun loadAppStateData() {
        val sharedPrefs = getSharedPreferences("app_state", Context.MODE_PRIVATE)
        counter = sharedPrefs.getInt(STATE_COUNTER, STATE_COUNTER_DEFAULT)
    }


    // companion object
    companion object {
        const val STATE_COUNTER = "counter"
        const val STATE_COUNTER_DEFAULT = 0
        const val TAG = "com.example.lifecycle_and_persistence.MainActivity"
    }
}