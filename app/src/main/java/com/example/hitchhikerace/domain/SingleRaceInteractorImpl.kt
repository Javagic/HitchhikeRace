package com.example.hitchhikerace.domain

import android.annotation.SuppressLint
import com.example.hitchhikerace.data.database.AppDatabase
import com.example.hitchhikerace.data.database.SingleRaceEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SingleRaceInteractorImpl @Inject constructor(private val database: AppDatabase) :
    SingleRaceInteractor {

    @SuppressLint("CheckResult")
    override fun addSingleRace(newRace: SingleRaceEntity): Single<Long> {
        return Single.fromCallable {
            database.singleRaceDao()
                .insert(newRace)
        }
            .subscribeOn(Schedulers.io())
    }

    override fun getAll(): Observable<List<SingleRaceEntity>> {
        return database.singleRaceDao()
            .getAll()
            .subscribeOn(Schedulers.io())
    }

}