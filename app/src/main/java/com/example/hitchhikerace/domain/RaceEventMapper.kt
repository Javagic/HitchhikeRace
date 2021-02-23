package com.example.hitchhikerace.domain

import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.data.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import javax.inject.Inject

class RaceEventMapper @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    fun map(eventViewModel: RaceEventViewModel): RaceEventEntity {
        return RaceEventEntity(
            preferenceManager.getCurrentRace(),
            eventViewModel.type,
            eventViewModel.description,
            eventViewModel.mainText,
            eventViewModel.specialDataText,
            eventViewModel.hour,
            eventViewModel.minute,
            System.currentTimeMillis(),
            eventViewModel.latitude,
            eventViewModel.longitude,
            preferenceManager.getCurrentRest().toString()
        )
    }
}