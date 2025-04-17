package com.example.fly.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fly.R
import com.example.fly.activities.persistence.AppDatabase
import com.example.fly.activities.persistence.EntityModelConverter
import com.example.fly.adapters.FlightTicketAdapter
import com.example.fly.api.FlightsRetrofitApi
import com.example.fly.models.Flight
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FlightTicketsResultsActivity : AppCompatActivity() {

    // UI FIELDS
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyResultsTextView: TextView

    // Flight search parameters
    private lateinit var originCode: String
    private lateinit var destinationCode: String
    private lateinit var departureDate: String
    private lateinit var returnDate: String
    private lateinit var cabinClass: String
    private lateinit var currency: String
    private var adults: Int = 1

    // Coroutine context for background operations
    private val coroutineContext = Job() + Dispatchers.Default
    private val coroutineScope = CoroutineScope(coroutineContext)

    // Adapter for flight results
    private lateinit var flightAdapter: FlightTicketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_tickets_results)

        // Extract search parameters from intent
        extractSearchParameters()

        // Initialize views
        initializeViews()

        // Setup recycler view
        setupRecyclerView()

        // Setup bottom navigation
        setupBottomNavigation()

        // Setup toolbar
        setupToolbar()

        // Search for flights
        searchFlights()
    }

    private fun extractSearchParameters() {
        intent.extras?.let { bundle ->
            originCode = bundle.getString("ORIGIN_CODE", "")
            destinationCode = bundle.getString("DESTINATION_CODE", "")
            departureDate = bundle.getString("DEPARTURE_DATE", "")
            returnDate = bundle.getString("RETURN_DATE", "")
            cabinClass = bundle.getString("CABIN_CLASS", "economy")
            currency = bundle.getString("CURRENCY", "USD")
            adults = bundle.getInt("ADULTS", 1)
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.activity_toolbar)
        setSupportActionBar(toolbar)
        // Hide the toolbar title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // Show toolbar back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initializeViews() {
        bottomNavigation = findViewById(R.id.view_bottom_nav)
        recyclerView = findViewById(R.id.departure_flights_activity_recyclerView)
        progressBar = findViewById(R.id.flight_results_progress_bar)
        emptyResultsTextView = findViewById(R.id.flight_results_empty_text)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with empty list
        flightAdapter = FlightTicketAdapter(emptyList()) { flight ->
            saveFlight(flight)
        }

        recyclerView.adapter = flightAdapter
    }

    private fun searchFlights() {
        // Validate search parameters
        if (originCode.isEmpty() || destinationCode.isEmpty() ||
            departureDate.isEmpty() || returnDate.isEmpty()) {
            displayError(Exception("Invalid search parameters"))
            return
        }

        // Show progress indicator
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyResultsTextView.visibility = View.GONE

        // Perform search in background
        coroutineScope.launch {
            try {
                // Call the API
                val searchResponse = FlightsRetrofitApi.searchFlights(
                    originCode,
                    destinationCode,
                    departureDate,
                    returnDate,
                    adults,
                    0, // Children
                    0, // Infants
                    cabinClass,
                    currency
                )

                // Update UI on main thread
                runOnUiThread {
                    // Hide progress indicator
                    progressBar.visibility = View.GONE

                    // If there are flights, show them in the RecyclerView
                    if (searchResponse.success && searchResponse.flights.isNotEmpty()) {
                        flightAdapter.updateFlights(searchResponse.flights)
                        recyclerView.visibility = View.VISIBLE
                        emptyResultsTextView.visibility = View.GONE
                    } else {
                        // No flights found
                        recyclerView.visibility = View.GONE
                        emptyResultsTextView.visibility = View.VISIBLE
                    }
                }

            } catch (e: Exception) {
                // Handle error
                runOnUiThread {
                    displayError(e)
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun saveFlight(flight: Flight) {
        coroutineScope.launch {
            try {
                // Convert Flight to SavedFlight entity
                val savedFlight = EntityModelConverter.convertFlightToSavedFlightEntity(
                    flight,
                    originCode,
                    destinationCode
                )

                // Save to database
                AppDatabase.getDatabase(this@FlightTicketsResultsActivity)
                    .savedFlightDao()
                    .insertSavedFlight(savedFlight)

                // Show success message
                runOnUiThread {
                    Toast.makeText(
                        this@FlightTicketsResultsActivity,
                        "Flight saved successfully",
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

    // Handle toolbar back button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.menu_search_flights

        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_saved_flights -> {
                    val intent = Intent(this, SavedFlightsActivity::class.java)
                    startActivity(intent)
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

    private fun displayError(e: Exception) {
        val message = e.message ?: "An error occurred while searching for flights"
        Log.e(TAG, "Error: $message", e)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Show empty state
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        emptyResultsTextView.visibility = View.VISIBLE
        emptyResultsTextView.text = "Error: $message"
    }

    companion object {
        const val TAG = "FlightTicketsResults"

        // Factory method to create intent
        fun newIntent(
            context: Context,
            originCode: String,
            destinationCode: String,
            departureDate: String,
            returnDate: String,
            cabinClass: String = "economy",
            currency: String = "USD",
            adults: Int = 1
        ): Intent {
            return Intent(context, FlightTicketsResultsActivity::class.java).apply {
                putExtra("ORIGIN_CODE", originCode)
                putExtra("DESTINATION_CODE", destinationCode)
                putExtra("DEPARTURE_DATE", departureDate)
                putExtra("RETURN_DATE", returnDate)
                putExtra("CABIN_CLASS", cabinClass)
                putExtra("CURRENCY", currency)
                putExtra("ADULTS", adults)
            }
        }
    }
}