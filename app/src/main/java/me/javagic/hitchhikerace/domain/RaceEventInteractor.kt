package me.javagic.hitchhikerace.domain

import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.view.RaceEventViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface RaceEventInteractor {
    fun addEvent(entityId: Long, eventViewModel: RaceEventViewModel): Completable
    fun getAllRaceEventList(raceId: Long): Observable<List<RaceEventEntity>>
    fun getAllRaceEventListSingle(raceId: Long): Single<List<RaceEventEntity>>
}