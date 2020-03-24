package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.core.mappers.ErrorMapper
import com.jcardama.moviedatabase.core.usecase.UseCase
import com.jcardama.moviedatabase.domain.entity.MovieEntity
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<GetMovieByIdParams, MovieEntity?>(errorUtil) {
    override suspend fun executeOnBackground(params: GetMovieByIdParams): MovieEntity? = repository.getById(params.id)
}

class GetMovieByIdParams(val id: Long)
