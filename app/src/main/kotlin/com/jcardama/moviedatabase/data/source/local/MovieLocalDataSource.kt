package com.jcardama.moviedatabase.data.source.local

import com.jcardama.moviedatabase.data.model.MovieModel
import javax.inject.Inject

interface MovieLocalDataSource {
    suspend fun getAll(): List<MovieModel>?
    suspend fun getById(id: Long): MovieModel?
    suspend fun saveAll(movies: List<MovieModel>?): Any
    suspend fun save(movie: MovieModel?): Long
    suspend fun searchByTitle(query: String): List<MovieModel>?
    suspend fun getSearched(): List<MovieModel>?
    suspend fun setAsSearched(id: Long): Any
    suspend fun getFavorites(): List<MovieModel>?
    suspend fun setFavorite(id: Long, isFavorite: Boolean): Any
    suspend fun getWatchList(): List<MovieModel>?
    suspend fun setWatchList(id: Long, isInWatchList: Boolean): Any
}

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : MovieLocalDataSource {
    override suspend fun getAll(): List<MovieModel>? = movieDao.getAll()

    override suspend fun getById(id: Long): MovieModel? = movieDao.getById(id)

    override suspend fun saveAll(movies: List<MovieModel>?): Any = movieDao.saveAll(movies)

    override suspend fun save(movie: MovieModel?): Long = movieDao.save(movie)

    override suspend fun searchByTitle(query: String): List<MovieModel>? = movieDao.searchByTitle(query)

    override suspend fun getSearched(): List<MovieModel>? = movieDao.getSearched()

    override suspend fun setAsSearched(id: Long): Any = movieDao.setAsSearched(id)

    override suspend fun getFavorites(): List<MovieModel>? = movieDao.getFavorites()

    override suspend fun setFavorite(id: Long, isFavorite: Boolean): Any = movieDao.setFavorite(id, isFavorite)

    override suspend fun getWatchList(): List<MovieModel>? = movieDao.getWatchList()

    override suspend fun setWatchList(id: Long, isInWatchList: Boolean): Any = movieDao.setWatchList(id, isInWatchList)
}
