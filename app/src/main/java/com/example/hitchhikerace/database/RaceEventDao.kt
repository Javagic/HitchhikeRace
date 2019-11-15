package com.example.hitchhikerace.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hitchhikerace.database.RaceEventEntity.Companion.TABLE_RACE_EVENT
import io.reactivex.Observable

@Dao
interface RaceEventDao : BaseDao<RaceEventEntity> {

    @Query("SELECT * FROM $TABLE_RACE_EVENT")
    fun getAll(): Observable<List<UserWidgetEntity>>

    @Query("DELETE FROM $TABLE_RACE_EVENT")
    fun clear(): Int

    @Query("DELETE FROM $TABLE_RACE_EVENT WHERE profileKey = :profileKey")
    fun clearByProfile(profileKey: String): Int

    @Transaction
    fun fill(profileKey: String, widgetList: List<UserWidgetEntity>) {
        clearByProfile(profileKey)
        insert(widgetList)
    }

    @Transaction
    fun fillForAllProfiles(profileKeys: List<String>, widgetList: List<String>) {
        clear()
        profileKeys.forEach { profileKey ->
            widgetList.forEachIndexed { index, alias ->
                insert(UserWidgetEntity(profileKey, alias, index))
            }
        }
    }
}