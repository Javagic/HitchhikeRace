package me.javagic.hitchhikerace.view.eventcreation

import me.javagic.hitchhikerace.data.pojo.RestEntity

interface RestAdjustableEventView {
    fun changeRest(currentRest: RestEntity)
}