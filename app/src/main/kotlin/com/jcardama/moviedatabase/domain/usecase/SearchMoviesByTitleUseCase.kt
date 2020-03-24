package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.core.mappers.ErrorMapper
import com.jcardama.moviedatabase.core.usecase.UseCase
import com.jcardama.moviedatabase.domain.entity.MovieEntity
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesByTitleUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<SearchMoviesByTitleParams, List<MovieEntity>?>(errorUtil) {
    override suspend fun executeOnBackground(params: SearchMoviesByTitleParams): List<MovieEntity>? = repository.searchByTitle(params.query)
}

class SearchMoviesByTitleParams(val query: String)
