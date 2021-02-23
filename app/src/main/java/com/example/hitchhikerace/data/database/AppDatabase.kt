package com.example.hitchhikerace.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hitchhikerace.data.database.dao.RaceEventDao
import com.example.hitchhikerace.data.database.dao.SingleRaceDao
import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.data.database.entity.SingleRaceEntity

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