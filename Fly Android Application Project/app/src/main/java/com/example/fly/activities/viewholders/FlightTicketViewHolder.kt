package com.example.fly.viewholders

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fly.R
import com.example.fly.models.Flight
import java.text.NumberFormat
import java.util.Locale


class FlightTicketViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


    // initialize the views in the viewholder layout
    // outbound flight
    private val cabinClassTextView: TextView = view.findViewById(R.id.input_cabin_class)
    private val outboundDepartureTimeTextView: TextView = view.findViewById(R.id.input_departure_time_outbound)
    private val outboundArrivalTimeTextView: TextView = view.findViewById(R.id.input_arrival_time_outbound)
    private val outboundDepartureDateTextView: TextView = view.findViewById(R.id.input_departure_date_outbound)
    private val outboundArrivalDateTextView: TextView = view.findViewById(R.id.input_arrival_date_outbound)
    private val outboundDepartureLocationTextView: TextView = view.findViewById(R.id.input_departure_location_outbound)
    private val outboundArrivalLocationTextView: TextView = view.findViewById(R.id.input_arrival_location_outbound)

    // inbound flight
    private val returnDepartureTimeTextView: TextView = view.findViewById(R.id.input_departure_time_return_flight)
    private val returnArrivalTimeTextView: TextView = view.findViewById(R.id.input_arrival_time_return_flight)
    private val returnDepartureDateTextView: TextView = view.findViewById(R.id.input_departure_date_return_flight)
    private val returnArrivalDateTextView: TextView = view.findViewById(R.id.input_arrival_date_return_flight)
    private val returnDepartureLocationTextView: TextView = view.findViewById(R.id.input_departure_location_return_flight)
    private val returnArrivalLocationTextView: TextView = view.findViewById(R.id.input_arrival_location_return_flight)

    private val priceTextView: TextView = view.findViewById(R.id.tv_ticket_price)
    private val saveButton: Button = view.findViewById(R.id.btn_save_ticket)


    fun bind(flight: Flight, onSaveClick: (Flight) -> Unit) {

        cabinClassTextView.text = formatCabinClass(flight.cabinClass)

        outboundDepartureTimeTextView.text = flight.outbound.departureTime
        outboundArrivalTimeTextView.text = flight.outbound.arrivalTime
        outboundDepartureDateTextView.text = formatDate(flight.outbound.departureDate)
        outboundArrivalDateTextView.text = formatDate(flight.outbound.arrivalDate)
        outboundDepartureLocationTextView.text = formatLocation(
            flight.outbound.departureAirport.cityName,
            flight.outbound.departureAirport.countryCode
        )
        outboundArrivalLocationTextView.text = formatLocation(
            flight.outbound.arrivalAirport.cityName,
            flight.outbound.arrivalAirport.countryCode
        )

        returnDepartureTimeTextView.text = flight.returnSegment.departureTime
        returnArrivalTimeTextView.text = flight.returnSegment.arrivalTime
        returnDepartureDateTextView.text = formatDate(flight.returnSegment.departureDate)
        returnArrivalDateTextView.text = formatDate(flight.returnSegment.arrivalDate)
        returnDepartureLocationTextView.text = formatLocation(
            flight.returnSegment.departureAirport.cityName,
            flight.returnSegment.departureAirport.countryCode
        )
        returnArrivalLocationTextView.text = formatLocation(
            flight.returnSegment.arrivalAirport.cityName,
            flight.returnSegment.arrivalAirport.countryCode
        )

        priceTextView.text = formatPrice(flight.price.amount, flight.price.currency)

        saveButton.setOnClickListener {
            onSaveClick(flight)
        }
    }


    fun resetViews() {
        cabinClassTextView.text = "Economy Class"

        outboundDepartureTimeTextView.text = ""
        outboundArrivalTimeTextView.text = ""
        outboundDepartureDateTextView.text = ""
        outboundArrivalDateTextView.text = ""
        outboundDepartureLocationTextView.text = ""
        outboundArrivalLocationTextView.text = ""

        returnDepartureTimeTextView.text = ""
        returnArrivalTimeTextView.text = ""
        returnDepartureDateTextView.text = ""
        returnArrivalDateTextView.text = ""
        returnDepartureLocationTextView.text = ""
        returnArrivalLocationTextView.text = ""

        priceTextView.text = ""
    }


    private fun formatCabinClass(cabinClass: String): String {
        return when (cabinClass.lowercase()) {
            "economy" -> "Economy Class"
            "business" -> "Business Class"
            "first" -> "First Class"
            "premium_economy" -> "Premium Economy"
            else -> cabinClass
        }
    }


    private fun formatDate(apiDate: String): String {
        if (apiDate.isEmpty()) return ""
        try {
            val parts = apiDate.split("-")
            if (parts.size == 3) {
                val year = parts[0]
                val month = parts[1]
                val day = parts[2]
                return "$month/$day/$year"
            }
        } catch (e: Exception) {
        }
        return apiDate
    }


    // helper method that combines country and city
    private fun formatLocation(city: String, country: String): String {
        return if (city.isNotEmpty() && country.isNotEmpty()) {
            "$city, $country"
        } else if (city.isNotEmpty()) {
            city
        } else if (country.isNotEmpty()) {
            country
        } else {
            "Unknown location"
        }
    }


    // helper method to properly format price w/ proper currency symbol
    private fun formatPrice(amount: Double, currency: String): String {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.currency = java.util.Currency.getInstance(currency)
        return format.format(amount)
    }
}