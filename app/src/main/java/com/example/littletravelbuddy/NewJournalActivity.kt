// NewJournalActivity.kt

package com.example.littletravelbuddy

import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewJournalActivity : AppCompatActivity() {

    private lateinit var etDestination: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var etNotes: EditText
    private lateinit var ivPhotos: ImageView
    private lateinit var btnCapturePhoto: Button
    private lateinit var btnSelectPhoto: Button
    private lateinit var etLocation: EditText
    private lateinit var mapFragment: FrameLayout

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        ivPhotos.setImageURI(uri)
    }

    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_journal)

        // Initialize Destination Text views
        etDestination = findViewById(R.id.etDestination)

        // Example usage: Get the entered destination when needed
        val destinationText = etDestination.text.toString()


        // Initialize Date Pick views
        datePicker = findViewById(R.id.datePicker)

        // Example usage: Get the selected date when needed
        val selectedYear = datePicker.year
        val selectedMonth = datePicker.month
        val selectedDay = datePicker.dayOfMonth

        // Create a Date object from the selected values
        val selectedDate = Calendar.getInstance()
        selectedDate.set(selectedYear, selectedMonth, selectedDay)

        // Initialize Notes Text Box views
        etNotes = findViewById(R.id.etNotes)

        // Example usage: Get the entered notes when needed
        val notesText = etNotes.text.toString()

        // Initialize Image Placement, Gallery Select and Camera Capture views
        ivPhotos = findViewById(R.id.ivPhotos)
        btnCapturePhoto = findViewById(R.id.btnCapturePhoto)
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto)

        // Set click listener for capturing photo
        btnCapturePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        // Set click listener for selecting photo from gallery
        btnSelectPhoto.setOnClickListener {
            getContent.launch("image/*")
        }

        // Initialize EditText for location
        etLocation = findViewById(R.id.etLocation)

        // Initialize MapView
        val mapView: MapView = findViewById(R.id.mapView)

        // Initialize OpenStreetMap
        Configuration.getInstance().userAgentValue = packageName
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        // Example usage: Display location on the map
        val geocodingService = provideGeocodingService()

        // Set click listener for displaying location on the map
        etLocation.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val enteredLocation = etLocation.text.toString()
                if (enteredLocation.isNotBlank()) {
                    geocodeLocation(geocodingService, enteredLocation, mapView)
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            ivPhotos.setImageBitmap(imageBitmap)
        }
    }

    private fun provideGeocodingService(): GeocodingService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GeocodingService::class.java)
    }

    private fun geocodeLocation(geocodingService: GeocodingService, location: String, osmMap: MapView) {
        val call = geocodingService.geocodeLocation(location)
        call.enqueue(object : Callback<List<GeocodingResult>> {
            override fun onResponse(
                call: Call<List<GeocodingResult>>,
                response: Response<List<GeocodingResult>>
            ) {
                if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                    val result = response.body()!!.first()
                    val geoPoint = GeoPoint(result.lat.toDouble(), result.lon.toDouble())
                    osmMap.controller.animateTo(geoPoint)
                    osmMap.controller.setZoom(15.0)
                }
            }

            override fun onFailure(call: Call<List<GeocodingResult>>, t: Throwable) {
                // Handle failure (e.g., display an error message)
            }
        })
    }
}
