// MainMenuActivity.kt

package com.example.littletravelbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Set click listeners for each button
        findViewById<View>(R.id.btnNewJournal).setOnClickListener {
            navigateToActivity(NewJournalActivity::class.java)
        }

        findViewById<View>(R.id.btnJournalEntries).setOnClickListener {
            navigateToActivity(JournalEntriesActivity::class.java)
        }

        findViewById<View>(R.id.btnFlightTracker).setOnClickListener {
            navigateToActivity(FlightTrackerActivity::class.java)
        }

        findViewById<View>(R.id.btnCurrencyConverter).setOnClickListener {
            navigateToActivity(CurrencyConverterActivity::class.java)
        }

        findViewById<View>(R.id.btnSearch).setOnClickListener {
            navigateToActivity(SearchActivity::class.java)
        }

        findViewById<View>(R.id.btnProfile).setOnClickListener {
            navigateToActivity(ProfileActivity::class.java)
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
    }
}
