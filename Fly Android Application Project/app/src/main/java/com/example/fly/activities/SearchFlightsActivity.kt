package com.example.fly.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fly.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchFlightsActivity : AppCompatActivity() {

    // UI FIELDS
    private lateinit var enterUserDepartureLocationEditText: EditText
    private lateinit var enterUserTravelDestinationTextView: EditText
    private lateinit var datePickerDeparture: DatePicker
    private lateinit var datePickerReturn: DatePicker
    private lateinit var submitUserDetailsButton: Button
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fills the layout with xml file "activity_search_flights"
        setContentView(R.layout.activity_search_flights)

        // initialize the views
        initializeViews()

        // initialize the tool bar
        setupToolbar()

        // Initialize bottom nav view
        setupBottomNavigation()

        // initialize date picker
        setupDatePicker()


        // BUTTONS //
        // button to submit user flight details
        submitUserDetailsButton.setOnClickListener {
            searchFlights()
        }
    }

    // method for initializing tool bar
    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.activity_toolbar)
        setSupportActionBar(toolbar)
        // hides the toolbar title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // shows toolbar back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // method for intializing date picker
    private fun setupDatePicker() {
        // DATE PICKER - DEPARTURE DATE
        val today = Calendar.getInstance()

        datePickerDeparture.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            // will delete this
            // just checking to see if the input is working
            val msg = "Departure: $day/${month + 1}/$year"
            Toast.makeText(this@SearchFlightsActivity, msg, Toast.LENGTH_SHORT).show()
        }

        // DATE PICKER - ARRIVAL DATE
        // Set return date to tomorrow by default
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)

        datePickerReturn.init(
            tomorrow.get(Calendar.YEAR),
            tomorrow.get(Calendar.MONTH),
            tomorrow.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            // will delete this
            // just checking to see if the input is working
            val msg = "Return: $day/${month + 1}/$year"
            Toast.makeText(this@SearchFlightsActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }

    // search flight method
    private fun searchFlights() {
        // user input
        val originCode = enterUserDepartureLocationEditText.text.toString().trim().uppercase()
        val destinationCode = enterUserTravelDestinationTextView.text.toString().trim().uppercase()

        // check if input is added correctly
        if (originCode.isEmpty() || destinationCode.isEmpty()) {
            Toast.makeText(this, "Please enter origin and destination airports", Toast.LENGTH_SHORT).show()
            return
        }

        // format dates from DatePickers
        val departureDateFormatted = formatDateForApi(datePickerDeparture)
        val returnDateFormatted = formatDateForApi(datePickerReturn)

        // default data
        val cabinClass = "economy"
        val currency = "CAD"
        val adults = 1

        // progress bar
        progressBar.visibility = View.VISIBLE

        // Log the parameters for debugging
        Log.d(TAG, "Search parameters: $originCode -> $destinationCode, $departureDateFormatted - $returnDateFormatted")

        // Create intent and navigate to results activity
        val intent = FlightTicketsResultsActivity.newIntent(
            this,
            originCode,
            destinationCode,
            departureDateFormatted,
            returnDateFormatted,
            cabinClass,
            currency,
            adults
        )
        startActivity(intent)

        // Hide progress
        progressBar.visibility = View.GONE
    }

    // Method for initializing views
    private fun initializeViews() {
        enterUserDepartureLocationEditText = findViewById(R.id.et_enter_user_departure_location)
        enterUserTravelDestinationTextView = findViewById(R.id.et_enter_user_travel_destination)
        datePickerDeparture = findViewById(R.id.date_picker_departure)
        datePickerReturn = findViewById(R.id.date_picker_return)
        submitUserDetailsButton = findViewById(R.id.btn_submit_user_details)
        bottomNavigation = findViewById(R.id.view_bottom_nav)

        // Add a progress bar in your layout or add it programmatically
        progressBar = findViewById(R.id.search_progress_bar) ?: ProgressBar(this).also {
            // If not found in layout, create a simple ProgressBar
            it.visibility = View.GONE
        }
    }


    // Format date for API requests
    private fun formatDateForApi(datePicker: DatePicker): String {
        // Format as YYYY-MM-DD for API
        val year = datePicker.year
        val month = datePicker.month + 1 // Month is 0-based in DatePicker
        val day = datePicker.dayOfMonth

        // Use this format to match API requirements
        return String.format("%04d-%02d-%02d", year, month, day)
    }


    // method for toolbar back button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    // method for handling bottom navigation
    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.menu_search_flights

        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search_flights -> {
                    true
                }
                R.id.menu_home -> {
                    val intent = Intent(this, MainActivity::class.java)
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

    companion object {
        const val TAG = "SearchFlightsActivity"
    }
}