package com.example.fly.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fly.R
import com.example.fly.activities.persistence.entities.SavedFlight
import com.example.fly.viewholders.SavedFlightViewHolder


class SavedFlightAdapter(
    private var savedFlights: List<SavedFlight>,
    private val onFlightClick: (SavedFlight) -> Unit
) : RecyclerView.Adapter<SavedFlightViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedFlightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_saved_flights, parent, false)
        return SavedFlightViewHolder(view)
    }


    override fun onBindViewHolder(holder: SavedFlightViewHolder, position: Int) {
        holder.resetViews()

        val savedFlight = savedFlights[position]

        holder.bind(savedFlight)

        holder.view.setOnClickListener {
            onFlightClick(savedFlight)
        }
    }


    override fun getItemCount(): Int = savedFlights.size


    fun updateSavedFlights(newSavedFlights: List<SavedFlight>) {
        this.savedFlights = newSavedFlights
        notifyDataSetChanged()
    }
}