package com.jcardama.moviedatabase.di.module

import android.app.Application
import androidx.room.Room
import com.jcardama.moviedatabase.data.source.db.AppDatabase
import com.jcardama.moviedatabase.data.source.db.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDatabase {
        return Room
                .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    fun provideMovieDao(appDataBase: AppDatabase): MovieDao {
        return appDataBase.movieDao()
    }
}
