package com.jcardama.moviedatabase.di.builder

import com.jcardama.moviedatabase.data.source.local.MovieLocalDataSource
import com.jcardama.moviedatabase.data.source.local.MovieLocalDataSourceImpl
import com.jcardama.moviedatabase.data.source.remote.MovieRemoteDataSource
import com.jcardama.moviedatabase.data.source.remote.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataSourceBuilder {
    @Binds
    abstract fun bindsAuthRemoteDataSource(remoteDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    abstract fun bindsAuthLocalDataSource(localDataSource: MovieLocalDataSourceImpl): MovieLocalDataSource
}
