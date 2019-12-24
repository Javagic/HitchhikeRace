package com.example.hitchhikerace.domain

import com.example.hitchhikerace.data.database.SingleRaceEntity
import io.reactivex.Observable
import io.reactivex.Single

interface SingleRaceInteractor {
    fun addSingleRace(newRace: SingleRaceEntity): Single<Long>
    fun getAll(): Observable<List<SingleRaceEntity>>
}