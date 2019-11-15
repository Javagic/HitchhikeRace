package com.example.hitchhikerace.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hitchhikerace.database.RaceEventEntity.Companion.TABLE_RACE_EVENT
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RaceEventDao : BaseDao<RaceEventEntity> {

    @Query("SELECT * FROM $TABLE_RACE_EVENT")
    fun getAll(): Observable<List<RaceEventEntity>>

    @Query("SELECT * FROM $TABLE_RACE_EVENT")
    fun getAllSingle(): Single<List<RaceEventEntity>>

    @Query("DELETE FROM $TABLE_RACE_EVENT")
    fun clear(): Int

    @Transaction
    fun fill(profileKey: String, widgetList: List<RaceEventEntity>) {
//        clearByProfile(profileKey)
        insert(widgetList)
    }

    @Transaction
    fun fillForAllProfiles(profileKeys: List<String>, widgetList: List<String>) {
        clear()
        profileKeys.forEach { profileKey ->
            widgetList.forEachIndexed { index, alias ->
//                insert(UserWidgetEntity(profileKey, alias, index))
            }
        }
    }
}