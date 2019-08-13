package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.data.mapper.ErrorMapper
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import com.jcardama.moviedatabase.domain.usecase.base.UseCase
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<Any, List<Movie>?>(errorUtil) {
    override suspend fun executeOnBackground(data: Any): List<Movie>? = repository.getFavorites()
}
