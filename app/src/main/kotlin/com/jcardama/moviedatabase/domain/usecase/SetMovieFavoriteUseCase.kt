package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.core.mappers.ErrorMapper
import com.jcardama.moviedatabase.core.usecase.UseCase
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class SetMovieFavoriteUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<SetMovieFavoriteParams, Any?>(errorUtil) {
    override suspend fun executeOnBackground(params: SetMovieFavoriteParams): Any? = repository.setFavorite(params.id, params.isFavorite)
}

class SetMovieFavoriteParams(val id: Long, val isFavorite: Boolean)
