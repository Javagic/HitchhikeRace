package com.example.hitchhikerace.domain

import android.annotation.SuppressLint
import com.example.hitchhikerace.data.database.AppDatabase
import com.example.hitchhikerace.data.database.RaceEventEntity
import com.example.hitchhikerace.view.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RaceEventInteractorImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val preferenceManager: PreferenceManager
) : RaceEventInteractor {

    @SuppressLint("CheckResult")
    override fun addEvent(entityId: Long, eventViewModel: RaceEventViewModel): Completable {
        return Completable.fromCallable {
            appDatabase.raceEventDao()
                .insert(
                    RaceEventEntity(
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
                    ).apply {
                        id = entityId
                    })
        }
            .subscribeOn(Schedulers.io())
    }

    override fun getAllRaceEventList(raceId: Long): Observable<List<RaceEventEntity>> {
        return appDatabase.raceEventDao().getAll(raceId)
            .subscribeOn(Schedulers.io())
    }

    override fun getAllRaceEventListSingle(raceId: Long): Single<List<RaceEventEntity>> {
        return appDatabase.raceEventDao()
            .getAllSingle(raceId)
            .subscribeOn(Schedulers.io())
    }
}