package com.example.fly.activities.persistence

import com.example.fly.activities.persistence.entities.SavedFlight
import com.example.fly.models.Airport
import com.example.fly.models.Flight
import com.example.fly.models.FlightSegment
import com.example.fly.models.Price


object EntityModelConverter {


    fun convertFlightToSavedFlightEntity(
        flight: Flight,
        departureLocationName: String,
        arrivalLocationName: String
    ): SavedFlight {
        val outbound = flight.outbound
        val returnSegment = flight.returnSegment

        return SavedFlight(
            outboundDepartureTime = outbound.departureTime,
            outboundArrivalTime = outbound.arrivalTime,
            outboundDepartureDate = outbound.departureDate,
            outboundArrivalDate = outbound.arrivalDate,
            outboundDepartureLocation = "${outbound.departureAirport.cityName}, ${outbound.departureAirport.countryName}",
            outboundArrivalLocation = "${outbound.arrivalAirport.cityName}, ${outbound.arrivalAirport.countryName}",
            outboundDepartureCode = outbound.departureAirport.code,
            outboundArrivalCode = outbound.arrivalAirport.code,

            returnDepartureTime = returnSegment.departureTime,
            returnArrivalTime = returnSegment.arrivalTime,
            returnDepartureDate = returnSegment.departureDate,
            returnArrivalDate = returnSegment.arrivalDate,
            returnDepartureLocation = "${returnSegment.departureAirport.cityName}, ${returnSegment.departureAirport.countryName}",
            returnArrivalLocation = "${returnSegment.arrivalAirport.cityName}, ${returnSegment.arrivalAirport.countryName}",
            returnDepartureCode = returnSegment.departureAirport.code,
            returnArrivalCode = returnSegment.arrivalAirport.code,

            cabinClass = flight.cabinClass,
            price = flight.price.amount,
            currency = flight.price.currency,
            flightId = flight.id
        )
    }


    fun createDisplayFlightFromEntity(savedFlight: SavedFlight): Flight {

        val outbound = FlightSegment(
            departureAirport = Airport(code = savedFlight.outboundDepartureCode),
            arrivalAirport = Airport(code = savedFlight.outboundArrivalCode),
            departureTime = savedFlight.outboundDepartureTime,
            arrivalTime = savedFlight.outboundArrivalTime,
            departureDate = savedFlight.outboundDepartureDate,
            arrivalDate = savedFlight.outboundArrivalDate
        )

        val returnSegment = FlightSegment(
            departureAirport = Airport(code = savedFlight.returnDepartureCode),
            arrivalAirport = Airport(code = savedFlight.returnArrivalCode),
            departureTime = savedFlight.returnDepartureTime,
            arrivalTime = savedFlight.returnArrivalTime,
            departureDate = savedFlight.returnDepartureDate,
            arrivalDate = savedFlight.returnArrivalDate
        )

        return Flight(
            id = savedFlight.flightId,
            price = Price(savedFlight.price, savedFlight.currency),
            outbound = outbound,
            returnSegment = returnSegment,
            cabinClass = savedFlight.cabinClass
        )
    }
}