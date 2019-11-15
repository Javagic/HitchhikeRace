package com.example.hitchhikerace.database

import androidx.room.Embedded
import androidx.room.Relation

class RaceWithPoints {

    @Embedded
    var singleRaces: SingleRaceEntity? = null

    @Relation(entity = RaceEventEntity::class, parentColumn = "id", entityColumn = "raceId")
    var raceEventList: List<RaceEventEntity> = emptyList()
}