package com.jcardama.moviedatabase.data.restful

import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.model.response.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface MovieService {
	companion object {
		private const val GET_URL = "/3/movie/now_playing"
		private const val SEARCH_URL = "/3/search/movie"
	}

	@GET(GET_URL)
	fun getAsync(@Query("api_key") apiKey: String = Config.API_KEY): Deferred<ApiResponse<List<Movie>>>

	@GET(SEARCH_URL)
	fun searchAsync(@Query("query") query: String, @Query("api_key") apiKey: String = Config.API_KEY): Deferred<ApiResponse<List<Movie>>>
}
