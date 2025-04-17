package com.example.fly.activities.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fly.activities.persistence.entities.SavedFlight


@Dao
interface SavedFlightDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedFlight(savedFlight: SavedFlight)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedFlights(savedFlights: List<SavedFlight>)


    @Delete
    suspend fun deleteSavedFlight(savedFlight: SavedFlight)


    @Query("SELECT * FROM saved_flights ORDER BY savedTimestamp DESC")
    suspend fun getAllSavedFlights(): List<SavedFlight>


    @Query("SELECT * FROM saved_flights WHERE id = :id")
    suspend fun getSavedFlightById(id: Int): SavedFlight?


    @Query("SELECT EXISTS(SELECT 1 FROM saved_flights WHERE flightId = :flightId LIMIT 1)")
    suspend fun isFlightSaved(flightId: String): Boolean


    @Query("DELETE FROM saved_flights")
    suspend fun deleteAllSavedFlights()
}