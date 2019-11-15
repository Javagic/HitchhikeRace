package com.example.hitchhikerace.database

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
    abstract fun advertisingDao(): SingleRaceDao
    abstract fun rotatorDao(): RaceEventDao

}