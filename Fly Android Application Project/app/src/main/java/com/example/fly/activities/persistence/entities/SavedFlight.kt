package com.example.fly.activities.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a saved flight in the Room database.
 * Each instance represents a round-trip flight that the user has saved.
 */
@Entity(tableName = "saved_flights")
data class SavedFlight(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Outbound Flight Details
    val outboundDepartureTime: String,
    val outboundArrivalTime: String,
    val outboundDepartureDate: String,
    val outboundArrivalDate: String,
    val outboundDepartureLocation: String,
    val outboundArrivalLocation: String,
    val outboundDepartureCode: String,
    val outboundArrivalCode: String,

    // Return Flight Details
    val returnDepartureTime: String,
    val returnArrivalTime: String,
    val returnDepartureDate: String,
    val returnArrivalDate: String,
    val returnDepartureLocation: String,
    val returnArrivalLocation: String,
    val returnDepartureCode: String,
    val returnArrivalCode: String,

    // General Flight Information
    val cabinClass: String,
    val price: Double,
    val currency: String,
    val flightId: String,  // Unique identifier from the API

    // Metadata
    val savedTimestamp: Long = System.currentTimeMillis()
)