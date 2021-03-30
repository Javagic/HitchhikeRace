package me.javagic.hitchhikerace.app

import android.app.Application
import androidx.multidex.MultiDexApplication
import me.javagic.hitchhikerace.di.ApplicationComponent
import me.javagic.hitchhikerace.di.DaggerApplicationComponent
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import timber.log.Timber

//TODO make correction for every race event
//TODO add icons
//TODO сделать нормальную обработку даты для гонок и событий
//TODO учитывать пересечение часовых поясов во время гонки
//TODO убрать кнопки в настройки гонки
class RaceApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        application = this
        //TODO plant only for debug version
        Timber.plant(Timber.DebugTree())
        mapper = jacksonObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP))
        appComponent = DaggerApplicationComponent.builder().build()
    }

    companion object {
        lateinit var application: Application
        lateinit var mapper: ObjectMapper
        lateinit var appComponent: ApplicationComponent
    }
}