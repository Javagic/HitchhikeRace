package me.javagic.hitchhikerace.data

import me.javagic.hitchhikerace.data.pojo.RestEntity

interface IPreferenceManager {
    fun getCurrentRace(): Long
    fun saveCurrentRest(newRest: RestEntity)
}