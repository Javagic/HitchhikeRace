package com.example.hitchhikerace.view

import android.content.Context
import androidx.core.content.edit
import com.example.hitchhikerace.RaceApplication
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

    companion object {
        const val KEY_CREW_LIST = "key_crew_list"
        const val APPLICATION_PREFERENCES = "app_prefs"
    }
}