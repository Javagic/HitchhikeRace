package com.example.hitchhikerace.database

import android.annotation.SuppressLint
import com.example.hitchhikerace.RaceApplication
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DataBaseInteractorImpl {

    @SuppressLint("CheckResult")
    fun addEvent(
        type: RaceEventType,
        mainText: String,
        description: String,
        hour: String,
        minute: String
    ) {
        Completable.fromCallable {
            RaceApplication.database.raceEventDao()
                .insert(
                    RaceEventEntity(
                        0,
                        type,
                        description,
                        mainText,
                        hour,
                        minute,
                        System.currentTimeMillis()
                    )
                )
        }
            .subscribeOn(Schedulers.io())
            .subscribe({}, {})
    }

    fun getStatistic(): Observable<List<RaceEventEntity>> {
        return RaceApplication.database.raceEventDao().getAll()
            .subscribeOn(Schedulers.io())

    }

}