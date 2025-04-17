package com.example.fly.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fly.R
import com.example.fly.activities.persistence.AppDatabase
import com.example.fly.activities.persistence.entities.SavedFlight
import com.example.fly.adapters.SavedFlightAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SavedFlightsActivity : AppCompatActivity() {

    // UI FIELDS
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerView: RecyclerView

    // Coroutine context for background operations
    private val coroutineContext = Job() + Dispatchers.Default
    private val coroutineScope = CoroutineScope(coroutineContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_flights)

        // VIEWS //
        initializeViews()
        // initializes the bottom menu nav view
        setupBottomNavigation()

        // TOOL BAR //
        // sets up the toolbar
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.activity_toolbar)
        setSupportActionBar(toolbar)
        // hides the toolbar title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // toolbar back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Load saved flights
        loadSavedFlights()
    }

    // this function initializes the views
    private fun initializeViews() {
        bottomNavigation = findViewById(R.id.view_bottom_nav)
        recyclerView = findViewById(R.id.saved_flights_activity_recyclerView)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // Load saved flights from database
    private fun loadSavedFlights() {
        coroutineScope.launch {
            try {
                // Get saved flights from database
                val savedFlights = AppDatabase.getDatabase(this@SavedFlightsActivity)
                    .savedFlightDao()
                    .getAllSavedFlights()

                // Update UI on main thread
                runOnUiThread {
                    if (savedFlights.isNotEmpty()) {
                        // Display saved flights in RecyclerView
                        recyclerView.adapter = SavedFlightAdapter(savedFlights) { savedFlight ->
                            // Show delete option when flight is clicked
                            showDeleteDialog(savedFlight)
                        }
                    } else {
                        Toast.makeText(
                            this@SavedFlightsActivity,
                            "No saved flights found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    displayError(e)
                }
            }
        }
    }

    // Show confirmation dialog before deletion
    private fun showDeleteDialog(savedFlight: SavedFlight) {
        AlertDialog.Builder(this)
            .setTitle("Delete Flight")
            .setMessage("Are you sure you want to delete this saved flight?")
            .setPositiveButton("Delete") { _, _ ->
                deleteSavedFlight(savedFlight)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Delete flight from database
    private fun deleteSavedFlight(savedFlight: SavedFlight) {
        coroutineScope.launch {
            try {
                // Delete from database
                AppDatabase.getDatabase(this@SavedFlightsActivity)
                    .savedFlightDao()
                    .deleteSavedFlight(savedFlight)

                // Reload saved flights
                loadSavedFlights()

                // Update UI on main thread
                runOnUiThread {
                    Toast.makeText(
                        this@SavedFlightsActivity,
                        "Flight deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    displayError(e)
                }
            }
        }
    }

    // function for toolbar back button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, SearchFlightsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // this is the function that handles bottom navigation
    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.menu_saved_flights

        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_saved_flights -> {
                    true
                }
                R.id.menu_search_flights -> {
                    val intent = Intent(this, SearchFlightsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    // Display error message
    private fun displayError(e: Exception) {
        val message = e.message ?: "Error with saved flights"
        Log.d(TAG, message)
        Toast.makeText(this@SavedFlightsActivity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "SavedFlightsActivity"
    }
}