package com.jcardama.moviedatabase.data.source.remote

import com.jcardama.moviedatabase.data.model.MovieModel
import com.jcardama.moviedatabase.data.source.remote.restful.MovieService
import javax.inject.Inject

interface MovieRemoteDataSource {
    suspend fun getAll(): List<MovieModel>?

    suspend fun search(query: String): List<MovieModel>?
}

class MovieRemoteDataSourceImpl @Inject constructor(private val movieService: MovieService) : MovieRemoteDataSource {
    override suspend fun getAll(): List<MovieModel>? = movieService.getAllAsync().await().results

    override suspend fun search(query: String): List<MovieModel>? = movieService.searchAsync(query).await().results
}
