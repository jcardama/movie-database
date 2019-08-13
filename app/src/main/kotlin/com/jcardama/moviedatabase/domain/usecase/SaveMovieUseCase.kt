package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.data.mapper.ErrorMapper
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import com.jcardama.moviedatabase.domain.usecase.base.UseCase
import javax.inject.Inject

class SaveMovieUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<Movie?, Any>(errorUtil) {
    override suspend fun executeOnBackground(data: Movie?): Any = repository.save(data)
}
