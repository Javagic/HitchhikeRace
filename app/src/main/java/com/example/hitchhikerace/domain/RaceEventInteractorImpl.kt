package com.example.hitchhikerace.domain

import com.example.hitchhikerace.data.database.AppDatabase
import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.di.qualifiers.IO
import com.example.hitchhikerace.view.RaceEventViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class RaceEventInteractorImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val raceEventMapper: RaceEventMapper,
    @IO private val ioScheduler: Scheduler
) : RaceEventInteractor {

    override fun addEvent(entityId: Long, eventViewModel: RaceEventViewModel): Completable {
        return Completable.fromCallable {
            appDatabase.raceEventDao()
                .insert(
                    raceEventMapper.map(eventViewModel).apply {
                        id = entityId
                    }
                )
        }
            .subscribeOn(ioScheduler)
    }

    override fun getAllRaceEventList(raceId: Long): Observable<List<RaceEventEntity>> {
        return appDatabase.raceEventDao().getAll(raceId)
            .subscribeOn(ioScheduler)
    }

    override fun getAllRaceEventListSingle(raceId: Long): Single<List<RaceEventEntity>> {
        return appDatabase.raceEventDao()
            .getAllSingle(raceId)
            .subscribeOn(ioScheduler)
    }
}