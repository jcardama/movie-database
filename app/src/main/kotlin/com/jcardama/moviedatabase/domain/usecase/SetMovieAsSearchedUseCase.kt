package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.core.mappers.ErrorMapper
import com.jcardama.moviedatabase.core.usecase.UseCase
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class SetMovieAsSearchedUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<SetMovieAsSearchedParams, Any?>(errorUtil) {
    override suspend fun executeOnBackground(params: SetMovieAsSearchedParams): Any? = repository.setAsSearched(params.id)
}

class SetMovieAsSearchedParams(val id: Long)
