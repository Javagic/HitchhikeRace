package com.example.hitchhikerace.di

import com.example.hitchhikerace.domain.RaceEventInteractor
import com.example.hitchhikerace.domain.RaceEventInteractorImpl
import com.example.hitchhikerace.domain.SingleRaceInteractor
import com.example.hitchhikerace.domain.SingleRaceInteractorImpl
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