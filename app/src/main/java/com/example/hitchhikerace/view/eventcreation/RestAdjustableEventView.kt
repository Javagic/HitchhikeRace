package com.example.hitchhikerace.view.eventcreation

import com.example.hitchhikerace.data.pojo.RestEntity

interface RestAdjustableEventView {
    fun changeRest(currentRest: RestEntity)
}