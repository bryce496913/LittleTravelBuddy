// CurrencyConverterActivity

package com.example.littletravelbuddy

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class CurrencyConverterActivity : AppCompatActivity() {

    private lateinit var spinnerConvertFrom: Spinner
    private lateinit var spinnerConvertTo: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var btnConvert: Button
    private lateinit var textConvertedValue: TextView
    private lateinit var currencyList: List<String>

    private val apiKey = "fca_live_3yGN1egNtbwPPBmFygqMteRMAz2KKVrbeg1oENfQ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)

        spinnerConvertFrom = findViewById(R.id.spinnerConvertFrom)
        spinnerConvertTo = findViewById(R.id.spinnerConvertTo)
        editTextAmount = findViewById(R.id.editTextAmount)
        btnConvert = findViewById(R.id.btnConvert)
        textConvertedValue = findViewById(R.id.textConvertedValue)

        // Fetch the list of currencies
        fetchCurrencyList()

        btnConvert.setOnClickListener {
            convertCurrency()
        }
    }

    private fun fetchCurrencyList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://freecurrencyapi.net/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CurrencyService::class.java)
        val call = service.getCurrencyList(apiKey)

        call.enqueue(object : Callback<CurrencyListResponse> {
            override fun onResponse(call: Call<CurrencyListResponse>, response: Response<CurrencyListResponse>) {
                if (response.isSuccessful) {
                    currencyList = response.body()?.currencies ?: emptyList()
                    setupSpinners()
                } else {
                    Toast.makeText(this@CurrencyConverterActivity, "Failed to fetch currencies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CurrencyListResponse>, t: Throwable) {
                Toast.makeText(this@CurrencyConverterActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinners() {
        // Set up spinners with currencies
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerConvertFrom.adapter = adapter
        spinnerConvertTo.adapter = adapter
    }

    private fun convertCurrency() {
        val fromCurrency = spinnerConvertFrom.selectedItem.toString()
        val toCurrency = spinnerConvertTo.selectedItem.toString()
        val amount = editTextAmount.text.toString().toDouble()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://freecurrencyapi.net/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CurrencyService::class.java)
        val call = service.convertCurrency(apiKey, fromCurrency, toCurrency, amount)

        call.enqueue(object : Callback<CurrencyResponse> {
            override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                if (response.isSuccessful) {
                    val convertedValue = response.body()?.result ?: 0.0
                    textConvertedValue.text = "Converted Value: $convertedValue $toCurrency"
                } else {
                    Toast.makeText(this@CurrencyConverterActivity, "Conversion failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                Toast.makeText(this@CurrencyConverterActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

interface CurrencyService {
    @GET("currency_converter")
    fun convertCurrency(
        @Query("apikey") apiKey: String,
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
        @Query("amount") amount: Double
    ): Call<CurrencyResponse>

    @GET("currencies")
    fun getCurrencyList(
        @Query("apikey") apiKey: String
    ): Call<CurrencyListResponse>
}

data class CurrencyListResponse(
    @SerializedName("currencies") val currencies: List<String>
)

data class CurrencyResponse(
    @SerializedName("status") val status: String,
    @SerializedName("result") val result: Double
)
