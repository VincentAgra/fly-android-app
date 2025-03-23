package activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fly.R
import android.content.Context
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // UI FIELDS
    /*
    private lateinit var appNameTextView: TextView
    private lateinit var searchFlightsButton: Button
    private lateinit var savedFlightsButton: Button */
    private lateinit var bottomNavigation: BottomNavigationView

    private var counter = STATE_COUNTER_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")

        // fills the layout with xml file "activity_main"
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            saveAppStateData()
        }

        // initialize the views
        initializeViews()

        // bottom nav menu
        setupBottomNavigation()

        /* on-click listener for buttons
        // for search flight button
        searchFlightsButton.setOnClickListener{
            val intent = Intent(this, SearchFlightsActivity::class.java)
            startActivity(intent)
        }

        // for saved flight button
        savedFlightsButton.setOnClickListener{
            val intent = Intent(this, SavedFlightsActivity::class.java)
            startActivity(intent)
        } */
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

        // saves app state to shared preferences
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
        /*
        appNameTextView = findViewById(R.id.tv_app_name)
        searchFlightsButton = findViewById(R.id.btn_search_flights)
        savedFlightsButton = findViewById(R.id.btn_saved_flights) */
        bottomNavigation = findViewById(R.id.view_bottom_nav)
    }

    // This is the function that handles bottom navigation
    private fun setupBottomNavigation() {
        // Set the home item as selected
        bottomNavigation.selectedItemId = R.id.menu_home

        // Set up the navigation listener
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    // Already on home screen, no action needed
                    true
                }
                R.id.menu_search_flights -> {
                    // Navigate to search flights screen
                    val intent = Intent(this, SearchFlightsActivity::class.java)
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