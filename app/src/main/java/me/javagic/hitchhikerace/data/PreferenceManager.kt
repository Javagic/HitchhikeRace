package me.javagic.hitchhikerace.data

import android.content.Context
import androidx.core.content.edit
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.data.pojo.RestEntity
import me.javagic.hitchhikerace.di.PerApplication
import me.javagic.hitchhikerace.utils.tryOrNull
import java.util.*
import javax.inject.Inject

const val RACE_EMPTY_ID = -1L

@PerApplication
class PreferenceManager @Inject constructor() : IPreferenceManager {

    fun saveCurrentRace(currentRaceId: Long) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit {
            putLong(KEY_CURRENT_RACE_ID, currentRaceId)
            commit()
        }
    }

    override fun getCurrentRace(): Long {
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

    override fun saveCurrentRest(newRest: RestEntity) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit(true) {
            putString(KEY_CURRENT_REST, "${newRest.hour} ${newRest.minute} ${newRest.partitions}")
            commit()
        }
    }

    fun getLegend(): String {
        return RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).getString(KEY_LEGEND_TEXT, "").orEmpty()
    }

    fun saveLegend(legendText: String) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit(true) {
            putString(KEY_LEGEND_TEXT, legendText)
            commit()
        }
    }

    fun getMap(): String {
        return RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).getString(KEY_MAP, "").orEmpty()
    }

    fun saveMap(mapText: String) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit(true) {
            putString(KEY_MAP, mapText)
            commit()
        }
    }

    fun getMainCrewName(): String {
        return RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).getString(KEY_MAIN_CREW_NAME, "").orEmpty()
    }

    fun saveMainCrewName(legendText: String) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit(true) {
            putString(KEY_MAIN_CREW_NAME, legendText)
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
        const val KEY_LEGEND_TEXT = "key_legend_text"
        const val KEY_MAP = "key_map"
        const val KEY_MAIN_CREW_NAME = "KEY_MAIN_CREW_NAME"
        const val KEY_CREW_LIST = "key_crew_list"
        const val KEY_CHECKPOINT_LIST = "key_checkpoint_list"
        const val KEY_CURRENT_REST = "key_current_rest"
        const val APPLICATION_PREFERENCES = "app_prefs"
    }
}