package com.example.hitchhikerace.view

import android.content.Context
import androidx.core.content.edit
import com.example.hitchhikerace.RaceApplication
import com.example.hitchhikerace.tryOrNull
import java.util.*

class PreferenceManager {

    fun saveCrewList(newList: String) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit {
            putString(KEY_CREW_LIST, newList)
        }
    }

    fun getCrewList(): List<String> {
        val listString = RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).getString(KEY_CREW_LIST, "")
        return listString?.toUpperCase(Locale.getDefault())?.split(" ") ?: emptyList()
    }

    fun saveCurrentRest(newRest: RestEntity) {
        RaceApplication.application.getSharedPreferences(
            APPLICATION_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit(true) {
            putString(KEY_CURRENT_REST, "${newRest.hour} ${newRest.minute} ${newRest.partitions}")
        }
    }

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
        const val KEY_CREW_LIST = "key_crew_list"
        const val KEY_CURRENT_REST = "key_current_rest"
        const val APPLICATION_PREFERENCES = "app_prefs"
    }
}