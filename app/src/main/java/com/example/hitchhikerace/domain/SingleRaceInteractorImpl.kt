package com.example.hitchhikerace.domain

import com.example.hitchhikerace.data.database.AppDatabase
import com.example.hitchhikerace.data.database.entity.SingleRaceEntity
import com.example.hitchhikerace.di.qualifiers.IO
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class SingleRaceInteractorImpl @Inject constructor(
    private val database: AppDatabase,
    @IO private val ioScheduler: Scheduler
) : SingleRaceInteractor {

    override fun addSingleRace(newRace: SingleRaceEntity): Single<Long> {
        return Single.fromCallable {
            database.singleRaceDao()
                .insert(newRace)
        }
            .subscribeOn(ioScheduler)
    }

    override fun getAll(): Observable<List<SingleRaceEntity>> {
        return database.singleRaceDao()
            .getAll()
            .subscribeOn(ioScheduler)
    }

}