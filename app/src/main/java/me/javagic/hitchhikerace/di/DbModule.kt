package me.javagic.hitchhikerace.di

import androidx.room.Room
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule {

    @Provides
    @PerApplication
    internal fun provideAddDatabase(): AppDatabase {
        return Room.databaseBuilder(
            RaceApplication.application, AppDatabase::class.java,
            APP_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        private const val APP_DATABASE_NAME = "race_database.db"
    }
}