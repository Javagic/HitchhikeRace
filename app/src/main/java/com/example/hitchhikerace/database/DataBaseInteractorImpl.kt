package com.example.hitchhikerace.database

import android.annotation.SuppressLint
import com.example.hitchhikerace.RaceApplication
import com.example.hitchhikerace.view.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class DataBaseInteractorImpl {

    @SuppressLint("CheckResult")
    fun addEvent(eventViewModel: RaceEventViewModel): Completable {
        return Completable.fromCallable {
            RaceApplication.database.raceEventDao()
                .insert(
                    RaceEventEntity(
                        0,
                        eventViewModel.type,
                        eventViewModel.description,
                        eventViewModel.mainText,
                        eventViewModel.hour,
                        eventViewModel.minute,
                        System.currentTimeMillis(),
                        eventViewModel.latitude,
                        eventViewModel.longitude,
                        PreferenceManager().getCurrentRest().toString()
                    )
                )
        }
            .subscribeOn(Schedulers.io())
    }

    fun getAllRaceEventList(): Observable<List<RaceEventEntity>> {
        return RaceApplication.database.raceEventDao().getAll()
            .subscribeOn(Schedulers.io())
    }

    fun getAllRaceEventListSingle(): Single<List<RaceEventEntity>> {
        return RaceApplication.database.raceEventDao().getAllSingle()
            .subscribeOn(Schedulers.io())
    }

}