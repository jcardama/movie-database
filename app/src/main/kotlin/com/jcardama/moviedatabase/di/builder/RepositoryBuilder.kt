package com.jcardama.moviedatabase.di.builder

import com.jcardama.moviedatabase.data.repository.MovieRepositoryImpl
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryBuilder {
    @Suppress("unused")
    @Binds
    abstract fun bindsMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}
