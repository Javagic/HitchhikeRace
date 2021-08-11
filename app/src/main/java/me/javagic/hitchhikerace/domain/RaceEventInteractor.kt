package me.javagic.hitchhikerace.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.view.RaceEventViewModel

interface RaceEventInteractor {
    fun addEvent(entityId: Long, eventViewModel: RaceEventViewModel): Completable
    fun getAllRaceEventList(raceId: Long): Observable<List<RaceEventEntity>>
    fun getAllRaceEventListSingle(raceId: Long): Single<List<RaceEventEntity>>
    fun insertAllRaceEventList(list: List<RaceEventEntity>): Completable
}