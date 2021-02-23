package com.example.hitchhikerace.data.database.dao

import androidx.room.*
import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.data.database.entity.TABLE_RACE_EVENT
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
    fun insert(value: RaceEventEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value: List<RaceEventEntity>): Array<Long>

    @Delete
    fun delete(value: RaceEventEntity)

    @Delete
    fun delete(value: List<RaceEventEntity>)

}