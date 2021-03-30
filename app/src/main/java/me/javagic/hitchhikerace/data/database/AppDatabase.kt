package me.javagic.hitchhikerace.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.javagic.hitchhikerace.data.database.dao.RaceEventDao
import me.javagic.hitchhikerace.data.database.dao.SingleRaceDao
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.database.entity.SingleRaceEntity

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