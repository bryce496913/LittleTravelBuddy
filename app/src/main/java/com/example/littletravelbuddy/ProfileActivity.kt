// ProfileActivity.kt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.littletravelbuddy.R
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var imageProfile: ImageView
    private lateinit var etName: EditText
    private lateinit var etStreet: EditText
    private lateinit var etCity: EditText
    private lateinit var etState: EditText
    private lateinit var etZip: EditText
    private lateinit var etCountry: EditText
    private lateinit var etPassportNumber: EditText
    private lateinit var etAirlineName: EditText
    private lateinit var etFrequentFlyerNumber: EditText
    private lateinit var etSpokenLanguages: EditText
    private lateinit var etEmergencyContactName: EditText
    private lateinit var etEmergencyContactNumber: EditText

    private val PICK_IMAGE_REQUEST = 1
    private val PREFS_NAME = "ProfilePrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imageProfile = findViewById(R.id.imageProfile)
        etName = findViewById(R.id.editTextName)
        etStreet = findViewById(R.id.editTextStreet)
        etCity = findViewById(R.id.editTextCity)
        etState = findViewById(R.id.spinnerState)
        etZip = findViewById(R.id.editTextZIP)
        etCountry = findViewById(R.id.editTextCountry)
        etPassportNumber = findViewById(R.id.editTextPassportNumber)
        etAirlineName = findViewById(R.id.editTextAirlineName)
        etFrequentFlyerNumber = findViewById(R.id.editTextFrequentFlyerNumber)
        etSpokenLanguages = findViewById(R.id.editTextSpokenLanguages)
        etEmergencyContactName = findViewById(R.id.editTextEmergencyContactName)
        etEmergencyContactNumber = findViewById(R.id.editTextContactNumber)

        // Set click listener for profile picture
        imageProfile.setOnClickListener {
            openGallery()
        }

        // Set click listener for save button
        val btnSave: Button = findViewById(R.id.btnSaveProfile)
        btnSave.setOnClickListener {
            saveProfile()
        }

        // Load saved profile information
        loadProfile()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun saveProfile() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Save profile picture
        val bitmap = (imageProfile.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val imageString: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        editor.putString("ProfilePicture", imageString)

        // Save other profile information
        editor.putString("Name", etName.text.toString())
        editor.putString("Street", etStreet.text.toString())
        editor.putString("City", etCity.text.toString())
        editor.putString("State", etState.text.toString())
        editor.putString("Zip", etZip.text.toString())
        editor.putString("Country", etCountry.text.toString())
        editor.putString("PassportNumber", etPassportNumber.text.toString())
        editor.putString("AirlineName", etAirlineName.text.toString())
        editor.putString("FrequentFlyerNumber", etFrequentFlyerNumber.text.toString())
        editor.putString("SpokenLanguages", etSpokenLanguages.text.toString())
        editor.putString("EmergencyContactName", etEmergencyContactName.text.toString())
        editor.putString("EmergencyContactNumber", etEmergencyContactNumber.text.toString())

        editor.apply()
    }

    private fun loadProfile() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Load profile picture
        val imageString = sharedPreferences.getString("ProfilePicture", "")
        if (imageString != null && imageString.isNotEmpty()) {
            val imageBytes: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imageProfile.setImageBitmap(bitmap)
        }

        // Load other profile information
        etName.setText(sharedPreferences.getString("Name", ""))
        etStreet.setText(sharedPreferences.getString("Street", ""))
        etCity.setText(sharedPreferences.getString("City", ""))
        etState.setText(sharedPreferences.getString("State", ""))
        etZip.setText(sharedPreferences.getString("Zip", ""))
        etCountry.setText(sharedPreferences.getString("Country", ""))
        etPassportNumber.setText(sharedPreferences.getString("PassportNumber", ""))
        etAirlineName.setText(sharedPreferences.getString("AirlineName", ""))
        etFrequentFlyerNumber.setText(sharedPreferences.getString("FrequentFlyerNumber", ""))
        etSpokenLanguages.setText(sharedPreferences.getString("SpokenLanguages", ""))
        etEmergencyContactName.setText(sharedPreferences.getString("EmergencyContactName", ""))
        etEmergencyContactNumber.setText(sharedPreferences.getString("EmergencyContactNumber", ""))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
            imageProfile.setImageBitmap(bitmap)
        }
    }
}
