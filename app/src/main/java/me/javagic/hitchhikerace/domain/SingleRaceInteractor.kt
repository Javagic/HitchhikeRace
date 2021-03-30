package me.javagic.hitchhikerace.domain

import me.javagic.hitchhikerace.data.database.entity.SingleRaceEntity
import io.reactivex.Observable
import io.reactivex.Single

interface SingleRaceInteractor {
    fun addSingleRace(newRace: SingleRaceEntity): Single<Long>
    fun getAll(): Observable<List<SingleRaceEntity>>
}