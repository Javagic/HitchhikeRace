package com.example.hitchhikerace.di

import com.example.hitchhikerace.view.activity.MainActivity
import com.example.hitchhikerace.RaceSettingsViewImpl
import com.example.hitchhikerace.view.CreateBaseEventViewImpl
import com.example.hitchhikerace.view.CurrentStatisticViewImpl
import com.example.hitchhikerace.view.MainScreenViewImpl
import com.example.hitchhikerace.view.eventcreation.CreateNewRaceViewImpl
import com.example.hitchhikerace.view.raceinfo.SingleRaceInfoViewImpl
import com.example.hitchhikerace.view.raceshistory.RacesHistoryViewImpl
import dagger.Component

@PerApplication
@Component(
    modules = [
        ApplicationModule::class,
        DbModule::class
    ]
)
interface ApplicationComponent {
    fun inject(fragment: CreateNewRaceViewImpl)
    fun inject(fragment: MainActivity)
    fun inject(fragment: MainScreenViewImpl)
    fun inject(fragment: CurrentStatisticViewImpl)
    fun inject(fragment: CreateBaseEventViewImpl)
    fun inject(fragment: RacesHistoryViewImpl)
    fun inject(fragment: RaceSettingsViewImpl)
    fun inject(fragment: SingleRaceInfoViewImpl)
}