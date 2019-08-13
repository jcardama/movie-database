package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.data.mapper.ErrorMapper
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import com.jcardama.moviedatabase.domain.usecase.base.UseCase
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<Int, Movie?>(errorUtil) {
    override suspend fun executeOnBackground(data: Int): Movie? = repository.getById(data)
}
