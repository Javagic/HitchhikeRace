package com.example.hitchhikerace.data

import android.content.Context
import androidx.core.content.edit
import com.example.hitchhikerace.app.RaceApplication
import com.example.hitchhikerace.data.pojo.RestEntity
import com.example.hitchhikerace.di.PerApplication
import com.example.hitchhikerace.utils.tryOrNull
import java.util.*
import javax.inject.Inject

const val RACE_EMPTY_ID = -1L

@PerApplication
class PreferenceManager @Inject constructor() {

    fun saveCurrentRace(currentRaceId: Long) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit {
            putLong(KEY_CURRENT_RACE_ID, currentRaceId)
            commit()
        }
    }

    fun getCurrentRace(): Long {
        return RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).getLong(KEY_CURRENT_RACE_ID, RACE_EMPTY_ID)
    }

    fun saveCrewList(newList: String) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit {
            putString(KEY_CREW_LIST, newList)
            commit()
        }
    }

    fun getCrewList(): List<String> {
        val listString = RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).getString(KEY_CREW_LIST, "")
        return listString?.toUpperCase(Locale.getDefault())?.split(" ")?.sortedBy { it }
            ?: emptyList()
    }

    fun saveCheckPointList(newList: String) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit {
            putString(KEY_CHECKPOINT_LIST, newList)
            commit()
        }
    }

    fun getCheckPointList(): List<String> {
        val listString = RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).getString(KEY_CHECKPOINT_LIST, "")
        return listString?.toUpperCase(Locale.getDefault())?.split(" ")?.sortedBy { it }
            ?: emptyList()
    }

    fun saveCurrentRest(newRest: RestEntity) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit(true) {
            putString(KEY_CURRENT_REST, "${newRest.hour} ${newRest.minute} ${newRest.partitions}")
            commit()
        }
    }

    //TODO handle null state
    fun getCurrentRest(): RestEntity = tryOrNull {
        val listString = RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).getString(KEY_CURRENT_REST, "")
        listString?.split(" ")?.let {
            RestEntity(it)
        }
    } ?: RestEntity()

    companion object {
        const val KEY_CURRENT_RACE_ID = "key_current_race"
        const val KEY_CREW_LIST = "key_crew_list"
        const val KEY_CHECKPOINT_LIST = "key_checkpoint_list"
        const val KEY_CURRENT_REST = "key_current_rest"
        const val APPLICATION_PREFERENCES = "app_prefs"
    }
}