package com.example.fly.api

import com.example.fly.models.FlightSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Configures and provides access to the FlightAPI.io REST API
 *
 * Key changes:
 * - Updated base URL
 * - Removed region parameter
 * - Simplified configuration
 */
object FlightsRetrofitApi {
    // base url flight api.io
    private const val BASE_URL = "https://api.flightapi.io/roundtrip/"


    // api key
    private const val API_KEY = "67fb255f8403a0d39db01c2c"


    // timeout config
    private const val TIMEOUT_DURATION = 30000L


    // logging interceptor for debugging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT_DURATION, TimeUnit.MILLISECONDS)
        .readTimeout(TIMEOUT_DURATION, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT_DURATION, TimeUnit.MILLISECONDS)
        .build()


    // build Retrofit instance
    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // create API service instance
    private val flightsApiService: FlightsApiService = buildRetrofit().create(FlightsApiService::class.java)


    suspend fun searchFlights(
        departureCode: String,
        arrivalCode: String,
        departureDate: String,
        returnDate: String,
        adults: Int = 1,
        children: Int = 0,
        infants: Int = 0,
        cabinClass: String = "Economy",
        currency: String = "CAD"
    ): FlightSearchResponse {
        return flightsApiService.searchFlights(
            API_KEY,
            departureCode,
            arrivalCode,
            departureDate,
            returnDate,
            adults,
            children,
            infants,
            cabinClass,
            currency
        )
    }
}