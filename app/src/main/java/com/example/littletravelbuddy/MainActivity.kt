// MainActivity.kt

package com.example.littletravelbuddy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val SPLASH_TIMEOUT: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Use a Handler to delay the redirection to the main menu
        Handler().postDelayed({
            // Start the main menu activity
            val intent = Intent(this@MainActivity, MainMenuActivity::class.java)
            startActivity(intent)

            // Close the splash activity
            finish()
        }, SPLASH_TIMEOUT)
    }
}
