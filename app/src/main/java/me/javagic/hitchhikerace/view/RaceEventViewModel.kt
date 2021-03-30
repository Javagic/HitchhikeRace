package me.javagic.hitchhikerace.view

import me.javagic.hitchhikerace.data.pojo.RaceEventType

class RaceEventViewModel(
    val type: RaceEventType,
    val mainText: String,
    val specialDataText: String,
    val description: String,
    val hour: String,
    val minute: String,
    val latitude: Double,
    val longitude: Double
)