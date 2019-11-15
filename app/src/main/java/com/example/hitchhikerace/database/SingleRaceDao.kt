package com.example.hitchhikerace.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hitchhikerace.database.SingleRaceEntity.Companion.TABLE_SINGLE_RACE
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface SingleRaceDao : BaseDao<SingleRaceEntity> {

    @Query("SELECT * FROM $TABLE_SINGLE_RACE")
    fun getAll(): Observable<List<SingleRaceEntity>>

    @Query("SELECT * FROM $TABLE_SINGLE_RACE")
    fun getAllWithPoints(): Observable<List<RaceWithPoints>>

    @Query("DELETE FROM $TABLE_SINGLE_RACE")
    fun clear(): Int

}