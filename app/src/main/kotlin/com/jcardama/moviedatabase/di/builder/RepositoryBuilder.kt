package com.jcardama.moviedatabase.di.builder

import com.jcardama.moviedatabase.domain.repository.Repository
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryBuilder {
    @Suppress("unused")
    @Binds
    abstract fun bindsMovieRepository(movieRepository: MovieRepository): Repository
}
