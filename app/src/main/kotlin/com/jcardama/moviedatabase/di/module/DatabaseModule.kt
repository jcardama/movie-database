package com.jcardama.moviedatabase.di.module

import android.app.Application
import androidx.room.Room
import com.jcardama.moviedatabase.data.source.local.AppDatabase
import com.jcardama.moviedatabase.data.source.local.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDatabase = Room
            .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMovieDao(appDataBase: AppDatabase): MovieDao = appDataBase.movieDao()
}
