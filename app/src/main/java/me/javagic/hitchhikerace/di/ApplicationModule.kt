package me.javagic.hitchhikerace.di

import me.javagic.hitchhikerace.domain.RaceEventInteractor
import me.javagic.hitchhikerace.domain.RaceEventInteractorImpl
import me.javagic.hitchhikerace.domain.SingleRaceInteractor
import me.javagic.hitchhikerace.domain.SingleRaceInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface ApplicationModule {

    @Binds
    @PerApplication
    fun provideRaceEventInteractor(value: RaceEventInteractorImpl): RaceEventInteractor

    @Binds
    @PerApplication
    fun provideSingleRaceInteractor(value: SingleRaceInteractorImpl): SingleRaceInteractor

}