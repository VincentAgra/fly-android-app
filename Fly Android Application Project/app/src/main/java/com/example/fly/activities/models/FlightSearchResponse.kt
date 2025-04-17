package com.example.fly.models

import com.google.gson.annotations.SerializedName


data class FlightSearchResponse(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("flights")
    val flights: List<Flight> = listOf(),
    @SerializedName("errors")
    val errors: List<String> = listOf()
)


data class Flight(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("price")
    val price: Price = Price(),
    @SerializedName("outbound")
    val outbound: FlightSegment = FlightSegment(),
    @SerializedName("return")
    val returnSegment: FlightSegment = FlightSegment(),
    @SerializedName("cabinClass")
    val cabinClass: String = ""
)


data class Price(
    @SerializedName("amount")
    val amount: Double = 0.0,
    @SerializedName("currency")
    val currency: String = ""
)


data class FlightSegment(
    @SerializedName("departureAirport")
    val departureAirport: Airport = Airport(),
    @SerializedName("arrivalAirport")
    val arrivalAirport: Airport = Airport(),
    @SerializedName("departureTime")
    val departureTime: String = "",
    @SerializedName("arrivalTime")
    val arrivalTime: String = "",
    @SerializedName("departureDate")
    val departureDate: String = "",
    @SerializedName("arrivalDate")
    val arrivalDate: String = "",
    @SerializedName("airline")
    val airline: String = "",
    @SerializedName("flightNumber")
    val flightNumber: String = ""
)


data class Airport(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("cityCode")
    val cityCode: String = "",
    @SerializedName("cityName")
    val cityName: String = "",
    @SerializedName("countryCode")
    val countryCode: String = "",
    @SerializedName("countryName")
    val countryName: String = ""
)