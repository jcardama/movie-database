package com.jcardama.moviedatabase.data.restful

import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.model.response.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface MovieService {
	companion object {
		private const val URL = "/3/movie/popular"
	}

	@GET(URL)
	fun getAsync(): Deferred<ApiResponse<List<Movie>>>
}
