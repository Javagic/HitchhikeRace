package me.javagic.hitchhikerace.di

import me.javagic.hitchhikerace.di.qualifiers.IO
import me.javagic.hitchhikerace.di.qualifiers.UI
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