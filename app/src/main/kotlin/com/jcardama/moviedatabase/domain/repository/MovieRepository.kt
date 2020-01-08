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

    suspend fun getById(id: Int) = dao.getById(id)

    suspend fun searchByTitle(query: String): List<Movie>? = dao.searchByTitle(query)?.toMutableList().let { movies ->
        movies?.addAll(api.searchAsync(query).await().results?.apply {
            for(item in this) {
                val currentItem = when(movies.contains(item)) {
                    true -> {
                        movies.find { it == item }
                    }
                    else -> item
                }
                currentItem?.isFromSearch = true
                currentItem?.timestamp = System.currentTimeMillis()
                save(currentItem)
            }
        } ?: mutableListOf())
        movies?.distinctBy { it.id }
    }

    suspend fun getSearched(): List<Movie>? = dao.getSearched()

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
