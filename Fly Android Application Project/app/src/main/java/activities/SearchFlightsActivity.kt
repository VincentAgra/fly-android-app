package activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fly.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFlightsActivity : AppCompatActivity() {

    // UI FIELDS
    private lateinit var userDepartureLocationTextView: TextView
    private lateinit var userTravelDestinationTextView: TextView
    private lateinit var enterUserDepartureLocationEditText: EditText
    private lateinit var enterUserTravelDestinationTextView: EditText
    private lateinit var submitUserDetailsButton: Button
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_flights)

        // adds a back button to the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // initialize the views
        initializeViews()

        setupBottomNavigation()

        // on-click listener for buttons
        // for submit user details button
        submitUserDetailsButton.setOnClickListener{
            // add code here
        }

    }

    // method for initializing views
    private fun initializeViews() {
        userDepartureLocationTextView = findViewById(R.id.tv_user_departure_location)
        userTravelDestinationTextView = findViewById(R.id.tv_user_travel_destination)
        enterUserDepartureLocationEditText = findViewById(R.id.et_enter_user_departure_location)
        enterUserTravelDestinationTextView = findViewById(R.id.et_enter_user_travel_destination)
        submitUserDetailsButton = findViewById(R.id.btn_submit_user_details)
        bottomNavigation = findViewById(R.id.view_bottom_nav)
    }

    // This is the function that handles bottom navigation
    private fun setupBottomNavigation() {
        // Set the home item as selected
        bottomNavigation.selectedItemId = R.id.menu_home

        // Set up the navigation listener
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search_flights -> {
                    // Already on home screen, no action needed
                    true
                }
                R.id.menu_home -> {
                    // Navigate to search flights screen
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_saved_flights -> {
                    // Navigate to saved flights screen
                    val intent = Intent(this, SavedFlightsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

}