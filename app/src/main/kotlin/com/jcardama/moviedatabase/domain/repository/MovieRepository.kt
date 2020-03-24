package com.jcardama.moviedatabase.domain.repository

import com.jcardama.moviedatabase.data.model.MovieModel
import com.jcardama.moviedatabase.domain.entity.MovieEntity

interface MovieRepository : Repository {
    suspend fun getAll(shouldFetch: Boolean): List<MovieEntity>?

    suspend fun getById(id: Long): MovieEntity?

    suspend fun save(movieModel: MovieModel?): Any

    suspend fun searchByTitle(query: String): List<MovieEntity>?

    suspend fun getSearched(): List<MovieEntity>?

    suspend fun setAsSearched(id: Long): Any?

    suspend fun getFavorites(): List<MovieEntity>?

    suspend fun setFavorite(id: Long, isFavorite: Boolean): Any?

    suspend fun getWatchList(): List<MovieEntity>?

    suspend fun setWatchList(id: Long, isInWatchList: Boolean): Any?
}
