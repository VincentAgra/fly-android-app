package com.example.fly.api

import com.example.fly.models.FlightSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface FlightsApiService {
    @GET("{api_key}/{departureCode}/{arrivalCode}/{departureDate}/{returnDate}/" +
            "{adults}/{children}/{infants}/{cabinClass}/{currency}")
    suspend fun searchFlights(
        @Path("api_key") apiKey: String,
        @Path("departureCode") departureCode: String,
        @Path("arrivalCode") arrivalCode: String,
        @Path("departureDate") departureDate: String,
        @Path("returnDate") returnDate: String,
        @Path("adults") adults: Int,
        @Path("children") children: Int,
        @Path("infants") infants: Int,
        @Path("cabinClass") cabinClass: String,
        @Path("currency") currency: String
    ): FlightSearchResponse
}