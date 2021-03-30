package me.javagic.hitchhikerace.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.javagic.hitchhikerace.data.database.entity.SingleRaceEntity
import me.javagic.hitchhikerace.data.database.entity.SingleRaceEntity.Companion.TABLE_SINGLE_RACE
import io.reactivex.Observable

@Dao
interface SingleRaceDao {

    @Query("SELECT * FROM $TABLE_SINGLE_RACE")
    fun getAll(): Observable<List<SingleRaceEntity>>

//
//    @Query("SELECT * FROM $TABLE_SINGLE_RACE")
//    fun getAllWithPoints(): Observable<List<RaceWithPoints>>

    @Query("DELETE FROM $TABLE_SINGLE_RACE")
    fun clear(): Int

    @Insert
    fun insert(obj: SingleRaceEntity): Long

    @Insert
    fun insert(obj: List<SingleRaceEntity>): Array<Long>
}