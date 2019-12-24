package com.example.hitchhikerace.domain

import com.example.hitchhikerace.data.database.RaceEventEntity
import com.example.hitchhikerace.view.RaceEventViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface RaceEventInteractor {
    fun addEvent(entityId: Long, eventViewModel: RaceEventViewModel): Completable
    fun getAllRaceEventList(raceId: Long): Observable<List<RaceEventEntity>>
    fun getAllRaceEventListSingle(raceId: Long): Single<List<RaceEventEntity>>
}