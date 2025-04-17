package com.example.fly.activities.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fly.activities.persistence.dao.SavedFlightDao
import com.example.fly.activities.persistence.entities.SavedFlight


@Database(entities = [SavedFlight::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract fun savedFlightDao(): SavedFlightDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private const val DATABASE_NAME = "fly_database"

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}