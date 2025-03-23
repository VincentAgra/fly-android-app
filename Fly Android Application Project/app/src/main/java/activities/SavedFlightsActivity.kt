package activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fly.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SavedFlightsActivity : AppCompatActivity() {

    // UI FIELDS
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_flights)

        // adds a back button to the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializeViews()

        // add code here
        setupBottomNavigation()

    }

    // this function initializes the views
    private fun initializeViews() {
        bottomNavigation = findViewById(R.id.view_bottom_nav)
    }

    // This is the function that handles bottom navigation
    private fun setupBottomNavigation() {
        // Set the home item as selected
        bottomNavigation.selectedItemId = R.id.menu_home

        // Set up the navigation listener
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_saved_flights -> {
                    // Already on home screen, no action needed
                    true
                }
                R.id.menu_search_flights -> {
                    // Navigate to search flights screen
                    val intent = Intent(this, SearchFlightsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_home -> {
                    // Navigate to saved flights screen
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}