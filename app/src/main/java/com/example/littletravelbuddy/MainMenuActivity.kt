package com.example.littletravelbuddy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.littletravelbuddy.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners for each button
        binding.btnNewJournal.setOnClickListener {
            navigateToActivity(NewJournalActivity::class.java)
        }

        binding.btnJournalEntries.setOnClickListener {
            navigateToActivity(JournalEntriesActivity::class.java)
        }

        binding.btnFlightTracker.setOnClickListener {
            navigateToActivity(FlightTrackerActivity::class.java)
        }

        binding.btnCurrencyConverter.setOnClickListener {
            navigateToActivity(CurrencyConverterActivity::class.java)
        }

        binding.btnSearch.setOnClickListener {
            navigateToActivity(SearchActivity::class.java)
        }

        binding.btnProfile.setOnClickListener {
            navigateToActivity(ProfileActivity::class.java)
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
    }
}
