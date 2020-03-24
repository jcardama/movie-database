package com.jcardama.moviedatabase.domain.usecase

import com.jcardama.moviedatabase.core.mappers.ErrorMapper
import com.jcardama.moviedatabase.core.usecase.UseCase
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class SetMovieWatchListUseCase @Inject constructor(
        errorUtil: ErrorMapper,
        private val repository: MovieRepository
) : UseCase<SetMovieWatchListParams, Any?>(errorUtil) {
    override suspend fun executeOnBackground(params: SetMovieWatchListParams): Any? = repository.setWatchList(params.id, params.isInWatchList)
}

class SetMovieWatchListParams(val id: Long, val isInWatchList: Boolean)
