package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.core.mappers.ErrorMapper
import com.jcardama.moviedatabase.core.usecase.NoParams
import com.jcardama.moviedatabase.core.usecase.UseCase
import com.jcardama.moviedatabase.domain.entity.MovieEntity
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<NoParams, List<MovieEntity>?>(errorUtil) {
    override suspend fun executeOnBackground(params: NoParams): List<MovieEntity>? = repository.getFavorites()
}
