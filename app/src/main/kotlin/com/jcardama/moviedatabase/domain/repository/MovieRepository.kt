package com.jcardama.moviedatabase.domain.repository

import com.jcardama.moviedatabase.data.restful.MovieService
import com.jcardama.moviedatabase.data.source.db.MovieDao
import com.jcardama.moviedatabase.domain.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
        private val api: MovieService,
        private val dao: MovieDao
) : Repository {
    suspend fun save(movie: Movie?): Any {
        return dao.save(movie)
    }

    suspend fun getAll(): List<Movie>? = dao.getAll().let {
        when {
            it.isNullOrEmpty() || it[0].expired() -> {
                api.getAsync().await().results?.apply {
                    for(item in this) {
                        item.timestamp = System.currentTimeMillis()
                        save(item)
                    }
                }
            }
            else -> it
        }
    }

    suspend fun getFavorites(): List<Movie>? = dao.getFavorites()

    suspend fun getWatchList(): List<Movie>? = dao.getWatchList()
}
