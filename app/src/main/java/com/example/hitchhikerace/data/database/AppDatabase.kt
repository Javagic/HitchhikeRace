package com.example.hitchhikerace.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        (RaceEventEntity::class),
        (SingleRaceEntity::class)],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun singleRaceDao(): SingleRaceDao
    abstract fun raceEventDao(): RaceEventDao
}