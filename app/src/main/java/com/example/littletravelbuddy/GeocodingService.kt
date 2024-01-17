// GeocodingService.kt

package com.example.littletravelbuddy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET("search")
    fun geocodeLocation(@Query("q") location: String): Call<List<GeocodingResult>>
}
