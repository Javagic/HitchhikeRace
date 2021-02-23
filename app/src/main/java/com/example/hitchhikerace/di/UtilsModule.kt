package com.example.hitchhikerace.di

import com.example.hitchhikerace.di.qualifiers.IO
import com.example.hitchhikerace.di.qualifiers.UI
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class UtilsModule {

    @PerApplication
    @Provides
    @UI
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @PerApplication
    @Provides
    @IO
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }
}