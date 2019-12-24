package com.example.hitchhikerace.data.database

import androidx.room.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RaceEventDao {

    @Query("SELECT * FROM $TABLE_RACE_EVENT WHERE raceId == :raceId")
    fun getAll(raceId: Long): Observable<List<RaceEventEntity>>

    @Query("SELECT * FROM $TABLE_RACE_EVENT WHERE raceId == :raceId")
    fun getAllSingle(raceId: Long): Single<List<RaceEventEntity>>

    @Query("DELETE FROM $TABLE_RACE_EVENT")
    fun clear(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: RaceEventEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<RaceEventEntity>): Array<Long>

    @Delete
    fun delete(obj: RaceEventEntity)

    @Delete
    fun delete(obj: List<RaceEventEntity>)

}