package me.javagic.hitchhikerace.domain

import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.view.RaceEventViewModel
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
            preferenceManager.getCurrentRest().toString(),
            eventViewModel.technicalText
        )
    }
}