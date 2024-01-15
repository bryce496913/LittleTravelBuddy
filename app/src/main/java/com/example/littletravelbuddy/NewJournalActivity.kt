// NewJournalActivity.kt

package com.example.littletravelbuddy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_new_journal.*

class NewJournalActivity : AppCompatActivity() {

    // Request codes for media capture and selection
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_SELECT_FROM_GALLERY = 2

    // Variables to store selected media URI and location
    private var selectedMediaUri: Uri? = null
    private var selectedLocation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_journal)

        // TODO: Initialize map view in mapContainer

        // Set onClick listeners for media buttons
        btnCapturePhoto.setOnClickListener { capturePhoto() }
        btnSelectFromGallery.setOnClickListener { selectFromGallery() }

        // TODO: Implement location retrieval
    }

    // Button onClick method for capturing a photo
    fun capturePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    // Button onClick method for selecting from gallery
    fun selectFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_SELECT_FROM_GALLERY)
    }

    // Handle result from media capture or selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    // Photo captured from camera
                    // TODO: Handle captured photo URI
                }

                REQUEST_SELECT_FROM_GALLERY -> {
                    // Photo selected from gallery
                    // TODO: Handle selected photo URI
                }
            }
        }
    }

    // TODO: Implement location retrieval

    // Button onClick method for saving the journal entry
    fun saveJournalEntry() {
// TODO: Implement saving journal entry logic
// Use etDestination.text.toString() to get destination
    }
}
