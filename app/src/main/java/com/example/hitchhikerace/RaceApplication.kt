package com.example.hitchhikerace

import android.app.Application
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.example.hitchhikerace.database.AppDatabase

class RaceApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        application = this
        database =
            Room.databaseBuilder(application, AppDatabase::class.java, APP_DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    companion object {
        lateinit var application: Application
        lateinit var database: AppDatabase
        private const val APP_DATABASE_NAME = "race_database.db"

    }
}