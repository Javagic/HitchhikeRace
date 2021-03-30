package me.javagic.hitchhikerace.di

import me.javagic.hitchhikerace.view.activity.MainActivity
import me.javagic.hitchhikerace.RaceSettingsViewImpl
import me.javagic.hitchhikerace.view.fragments.CreateBaseEventViewImpl
import me.javagic.hitchhikerace.view.fragments.CurrentStatisticViewImpl
import me.javagic.hitchhikerace.view.fragments.MainScreenViewImpl
import me.javagic.hitchhikerace.view.eventcreation.CreateNewRaceViewImpl
import me.javagic.hitchhikerace.view.raceinfo.SingleRaceInfoViewImpl
import me.javagic.hitchhikerace.view.raceshistory.RacesHistoryViewImpl
import dagger.Component

@PerApplication
@Component(
    modules = [
        ApplicationModule::class,
        UtilsModule::class,
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