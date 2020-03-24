package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.core.mappers.ErrorMapper
import com.jcardama.moviedatabase.core.usecase.UseCase
import com.jcardama.moviedatabase.domain.entity.MovieEntity
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<GetAllMoviesParams, List<MovieEntity>?>(errorUtil) {
    override suspend fun executeOnBackground(params: GetAllMoviesParams): List<MovieEntity>? = repository.getAll(params.shouldFetch)
}

class GetAllMoviesParams(val shouldFetch: Boolean)

