package activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fly.R

class ReturnFlightsResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return_flights_results)

        // adds a back button to the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // add code hereS

    }
}