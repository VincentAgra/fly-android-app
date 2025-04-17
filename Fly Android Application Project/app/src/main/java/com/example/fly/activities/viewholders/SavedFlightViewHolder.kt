package com.example.fly.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fly.R
import com.example.fly.activities.persistence.entities.SavedFlight
import java.text.NumberFormat
import java.util.Locale

/**
 * ViewHolder for displaying saved flights in a RecyclerView.
 * This class is responsible for binding SavedFlight data to the view.
 */
class SavedFlightViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val cabinClassTextView: TextView = view.findViewById(R.id.input_cabin_class)
    private val outboundDepartureTimeTextView: TextView = view.findViewById(R.id.input_departure_time_outbound)
    private val outboundArrivalTimeTextView: TextView = view.findViewById(R.id.input_arrival_time_outbound)
    private val outboundDepartureDateTextView: TextView = view.findViewById(R.id.input_departure_date_outbound)
    private val outboundArrivalDateTextView: TextView = view.findViewById(R.id.input_arrival_date_outbound)
    private val outboundDepartureLocationTextView: TextView = view.findViewById(R.id.input_departure_location_outbound)
    private val outboundArrivalLocationTextView: TextView = view.findViewById(R.id.input_arrival_location_outbound)

    private val returnDepartureTimeTextView: TextView = view.findViewById(R.id.input_departure_time_return_flight)
    private val returnArrivalTimeTextView: TextView = view.findViewById(R.id.input_arrival_time_return_flight)
    private val returnDepartureDateTextView: TextView = view.findViewById(R.id.input_departure_date_return_flight)
    private val returnArrivalDateTextView: TextView = view.findViewById(R.id.input_arrival_date_return_flight)
    private val returnDepartureLocationTextView: TextView = view.findViewById(R.id.input_departure_location_return_flight)
    private val returnArrivalLocationTextView: TextView = view.findViewById(R.id.input_arrival_location_return_flight)

    private val priceTextView: TextView = view.findViewById(R.id.tv_ticket_price)


    fun bind(savedFlight: SavedFlight) {
        cabinClassTextView.text = formatCabinClass(savedFlight.cabinClass)

        outboundDepartureTimeTextView.text = savedFlight.outboundDepartureTime
        outboundArrivalTimeTextView.text = savedFlight.outboundArrivalTime
        outboundDepartureDateTextView.text = savedFlight.outboundDepartureDate
        outboundArrivalDateTextView.text = savedFlight.outboundArrivalDate
        outboundDepartureLocationTextView.text = savedFlight.outboundDepartureLocation
        outboundArrivalLocationTextView.text = savedFlight.outboundArrivalLocation

        returnDepartureTimeTextView.text = savedFlight.returnDepartureTime
        returnArrivalTimeTextView.text = savedFlight.returnArrivalTime
        returnDepartureDateTextView.text = savedFlight.returnDepartureDate
        returnArrivalDateTextView.text = savedFlight.returnArrivalDate
        returnDepartureLocationTextView.text = savedFlight.returnDepartureLocation
        returnArrivalLocationTextView.text = savedFlight.returnArrivalLocation

        priceTextView.text = formatPrice(savedFlight.price, savedFlight.currency)
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


    private fun formatPrice(amount: Double, currency: String): String {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.currency = java.util.Currency.getInstance(currency)
        return format.format(amount)
    }
}