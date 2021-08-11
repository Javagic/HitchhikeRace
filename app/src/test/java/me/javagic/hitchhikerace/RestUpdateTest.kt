package me.javagic.hitchhikerace

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import me.javagic.hitchhikerace.data.IPreferenceManager
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.data.pojo.RestEntity
import me.javagic.hitchhikerace.domain.RaceEventInteractor
import me.javagic.hitchhikerace.view.fragments.RestHelper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class RestUpdateTest {

    private var eventInteractor: RaceEventInteractor = spy()

    private var preferenceManager: IPreferenceManager = mock()

    @Before
    fun setUp() {
        whenever(preferenceManager.getCurrentRace()).thenReturn(1L)
    }

    @Test
    fun testFinishNotNull() {
        whenever(eventInteractor.getAllRaceEventListSingle(any())).thenReturn(Single.just(eventList))
        val testObserver = TestObserver.create<List<RaceEventEntity>>()
        RestHelper(eventInteractor, preferenceManager).refreshRest(8, 56, 2)
            .subscribe(testObserver)
        testObserver.apply {
            assertEquals("22 0 23", values().last().last().currentRest)
            assertEquals("56", values().last()[2].minute)
            assertEquals("8", values().last()[2].hour)
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
        }
        verify(preferenceManager, atLeastOnce()).saveCurrentRest(
            RestEntity("22 00 23".split(" "))
        )
        verify(preferenceManager, atLeastOnce()).getCurrentRace()
    }

    @Test
    fun testFinishNull() {
        whenever(eventInteractor.getAllRaceEventListSingle(any())).thenReturn(Single.just(eventList2))

        val testObserver = TestObserver.create<List<RaceEventEntity>>()
        RestHelper(eventInteractor, preferenceManager).refreshRest(8, 56, null)
            .subscribe(testObserver)
        testObserver.apply {
            assertEquals("22 10 23", values().last().last().currentRest)
            verify(
                preferenceManager,
                atLeastOnce()
            ).saveCurrentRest(RestEntity("22 00 22".split(" ")))
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
        }
//        verify(preferenceManager, atLeastOnce()).saveCurrentRest(
//            RestEntity("22 00 24".split(" "))
//        )
        verify(preferenceManager, atLeastOnce()).getCurrentRace()
    }

    val eventList = listOf(
        RaceEventEntity(
            0,
            RaceEventType.RACE_START,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "10",
            1L,
            1.0,
            1.0,
            "22 20 25"
        ).apply { id = 0 },
        RaceEventEntity(
            0,
            RaceEventType.REST_START,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "46",
            1L,
            1.0,
            1.0,
            "22 20 25"
        ).apply { id = 1 },
        RaceEventEntity(
            0,
            RaceEventType.REST_FINISH,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "59",
            1L,
            1.0,
            1.0,
            "22 10 25"
        ).apply { id = 2 },
        RaceEventEntity(
            0,
            RaceEventType.CREW_MEETING,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "57",
            1L,
            1.0,
            1.0,
            "22 10 25"
        ).apply { id = 3 },
        RaceEventEntity(
            0,
            RaceEventType.REST_START,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "46",
            1L,
            1.0,
            1.0,
            "22 10 25"
        ).apply { id = 4 },
        RaceEventEntity(
            0,
            RaceEventType.REST_FINISH,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "56",
            1L,
            1.0,
            1.0,
            "22 10 25"
        ).apply { id = 5 }
    )

    val eventList2 = listOf(
        RaceEventEntity(
            0,
            RaceEventType.RACE_START,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "10",
            1L,
            1.0,
            1.0,
            "22 30 25"
        ).apply { id = 0 },
        RaceEventEntity(
            0,
            RaceEventType.REST_START,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "46",
            1L,
            1.0,
            1.0,
            "22 20 25"
        ).apply { id = 1 },
        RaceEventEntity(
            0,
            RaceEventType.REST_FINISH,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "56",
            1L,
            1.0,
            1.0,
            "22 10 25"
        ).apply { id = 2 },
        RaceEventEntity(
            0,
            RaceEventType.CREW_MEETING,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "57",
            1L,
            1.0,
            1.0,
            "22 10 25"
        ).apply { id = 3 },
        RaceEventEntity(
            0,
            RaceEventType.REST_START,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "46",
            1L,
            1.0,
            1.0,
            "22 10 25"
        ).apply { id = 4 },
        RaceEventEntity(
            0,
            RaceEventType.REST_FINISH,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "56",
            1L,
            1.0,
            1.0,
            "22 10 25"
        ).apply { id = 5 },
        RaceEventEntity(
            0,
            RaceEventType.REST_START,
            "eventDescr",
            "mainText",
            "mainText",
            "8",
            "46",
            1L,
            1.0,
            1.0,
            "22 10 23"
        ).apply { id = 6 }
    )
}