package com.example.hitchhikerace.view

import com.example.hitchhikerace.data.pojo.RaceEventType

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