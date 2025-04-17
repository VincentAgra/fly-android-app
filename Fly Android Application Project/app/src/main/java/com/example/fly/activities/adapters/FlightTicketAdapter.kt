package com.example.fly.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fly.R
import com.example.fly.models.Flight
import com.example.fly.viewholders.FlightTicketViewHolder


class FlightTicketAdapter(
    private var flights: List<Flight>,
    private val onSaveClick: (Flight) -> Unit
) : RecyclerView.Adapter<FlightTicketViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightTicketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_ticket_flights_results, parent, false)
        return FlightTicketViewHolder(view)
    }


    override fun onBindViewHolder(holder: FlightTicketViewHolder, position: Int) {
        holder.resetViews()

        val flight = flights[position]

        holder.bind(flight, onSaveClick)
    }


    override fun getItemCount(): Int = flights.size


    fun updateFlights(newFlights: List<Flight>) {
        this.flights = newFlights
        notifyDataSetChanged()
    }
}