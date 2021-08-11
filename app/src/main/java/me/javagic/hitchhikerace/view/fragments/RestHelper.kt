package me.javagic.hitchhikerace.view.fragments

import android.annotation.SuppressLint
import io.reactivex.Single
import me.javagic.hitchhikerace.data.IPreferenceManager
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.data.pojo.RestEntity
import me.javagic.hitchhikerace.domain.RaceEventInteractor

class RestHelper(
    val eventInteractor: RaceEventInteractor,
    var preferenceManager: IPreferenceManager
) {

    /**
     * Высчитываем новый рест для новых событий конца реста
     * Или изменяем все настоящие события
     */
    @SuppressLint("CheckResult")
    fun refreshRest(
        newHour: Int,
        newMinute: Int,
        finishEventId: Long? = null
    ): Single<List<RaceEventEntity>> {
        return eventInteractor.getAllRaceEventListSingle(preferenceManager.getCurrentRace())
            .map {
                processRest(it, newHour, newMinute, finishEventId)
            }
    }

    /**
     * Функция для обработки реста
     * finishEventId - ивент с которого стартует алгоритм изменения реста для всех последующих ивентов
     */
    private fun processRest(
        list: List<RaceEventEntity>,
        newHour: Int,
        newMinute: Int,
        finishEventId: Long? = null
    ): List<RaceEventEntity> {
        val eventList = list.sortedBy { it.id }
        //от какого события начинаем считать
        val restStartEvent = eventList.findLastStartRestEvent(finishEventId)
        val diffMinutes = restStartEvent.calculateDiffInMinutes(newHour, newMinute)
        val startRest = restStartEvent.currentRest.split(" ").let {
            RestEntity(it)
        }
        val newRestMinute = startRest.toMinutes() - diffMinutes
        val newRest = RestEntity(
            newRestMinute / 60,
            newRestMinute - ((newRestMinute / 60) * 60),
            startRest.partitions - 1
        )
        if (finishEventId != null) {
            eventList.find { it.id == finishEventId }?.hour = newHour.toString()
            eventList.find { it.id == finishEventId }?.minute = newMinute.toString()
            for (i in eventList.indexOfFirst { it.id == finishEventId } until eventList.size) {
                if (i == eventList.indexOfFirst { it.id == finishEventId }) {
                    eventList[i].currentRest = newRest.toString()
                    continue
                }
                if (eventList[i].raceEventType == RaceEventType.REST_FINISH) {
                    return processRest(
                        list,
                        eventList[i].hour.toInt(),
                        eventList[i].minute.toInt(),
                        eventList[i].id
                    )
                }
                eventList[i].currentRest = newRest.toString()
            }
        }
        preferenceManager.saveCurrentRest(newRest)
        return eventList
    }

    @Suppress("UnnecessaryVariable")
    fun RaceEventEntity.calculateDiffInMinutes(
        newHour: Int,
        newMinute: Int
    ): Int {
        val startHour = this.hour.toInt()
        val startMinute = this.minute.toInt()
        val diffMinutes =
            if (newHour < startHour || (newHour == startHour && newMinute < startMinute)) {
                (newHour * 60 + newMinute) + ((23 - startHour) * 60 + 60 - startMinute)
            } else {
                (newHour * 60 + newMinute) - (startMinute + startHour * 60)
            }
        return diffMinutes
    }

    fun List<RaceEventEntity>.findLastStartRestEvent(from: Long?): RaceEventEntity {
        return if (from == null) {
            this.findLast { it.raceEventType == RaceEventType.REST_START }!!
        } else let {
            for (i in this.indexOfFirst { it.id == from } downTo 0) {
                if (this[i].raceEventType == RaceEventType.REST_START) return@let this[i]
            }
            return@let this.first()
        }
    }
}