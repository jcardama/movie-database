package com.jcardama.moviedatabase.data.repository

import com.jcardama.moviedatabase.data.model.MovieModel
import com.jcardama.moviedatabase.data.model.mapToDomain
import com.jcardama.moviedatabase.data.source.local.MovieLocalDataSource
import com.jcardama.moviedatabase.data.source.remote.MovieRemoteDataSource
import com.jcardama.moviedatabase.domain.entity.MovieEntity
import com.jcardama.moviedatabase.domain.repository.MovieRepository
import com.jcardama.moviedatabase.domain.repository.Repository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
        private val movieLocalDataSource: MovieLocalDataSource,
        private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getAll(shouldFetch: Boolean): List<MovieEntity>? = object : Repository.NetworkBound<List<MovieEntity>, List<MovieModel>>() {
        override suspend fun saveCallResult(item: List<MovieModel>?) {
            movieLocalDataSource.saveAll(item?.map {
                it.apply {
                    timestamp = System.currentTimeMillis()
                }
            })
        }

        override fun shouldFetch(data: List<MovieModel>?): Boolean = shouldFetch || data.isNullOrEmpty() || data.first().expired()

        override suspend fun localCall(): List<MovieModel>? = movieLocalDataSource.getAll()

        override suspend fun networkCall(): List<MovieModel>? = movieRemoteDataSource.getAll()

        override suspend fun mapToDomain(data: List<MovieModel>?): List<MovieEntity>? = data?.mapToDomain()
    }.call()

    override suspend fun getById(id: Long): MovieEntity? = movieLocalDataSource.getById(id)?.mapToDomain()

    override suspend fun save(movieModel: MovieModel?): Any = movieLocalDataSource.save(movieModel)

    override suspend fun searchByTitle(query: String): List<MovieEntity>? = object : Repository.NetworkBound<List<MovieEntity>, List<MovieModel>>() {
        override suspend fun saveCallResult(item: List<MovieModel>?) {
            movieLocalDataSource.saveAll(item?.map {
                it.apply {
                    isFromSearch = true
                    timestamp = System.currentTimeMillis()
                }
            })
        }

        override fun shouldFetch(data: List<MovieModel>?): Boolean = true

        override suspend fun localCall(): List<MovieModel>? = movieLocalDataSource.searchByTitle(query)

        override suspend fun networkCall(): List<MovieModel>? = movieRemoteDataSource.search(query)

        override suspend fun mapToDomain(data: List<MovieModel>?): List<MovieEntity>? = data?.mapToDomain()
    }.call()

    override suspend fun getSearched(): List<MovieEntity>? = movieLocalDataSource.getSearched()?.mapToDomain()

    override suspend fun setAsSearched(id: Long): Any? = movieLocalDataSource.setAsSearched(id)

    override suspend fun getFavorites(): List<MovieEntity>? = movieLocalDataSource.getFavorites()?.mapToDomain()

    override suspend fun setFavorite(id: Long, isFavorite: Boolean): Any? = movieLocalDataSource.setFavorite(id, isFavorite)

    override suspend fun getWatchList(): List<MovieEntity>? = movieLocalDataSource.getWatchList()?.mapToDomain()

    override suspend fun setWatchList(id: Long, isInWatchList: Boolean): Any? = movieLocalDataSource.setWatchList(id, isInWatchList)
}
