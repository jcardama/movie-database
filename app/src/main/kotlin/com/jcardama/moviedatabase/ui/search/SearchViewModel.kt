package com.jcardama.moviedatabase.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.usecase.GetSearchedMoviesUseCase
import com.jcardama.moviedatabase.domain.usecase.SaveMovieUseCase
import com.jcardama.moviedatabase.domain.usecase.SearchMoviesByTitleUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val searchMoviesByTitleUseCase: SearchMoviesByTitleUseCase,
        private val getSearchedMoviesUseCase: GetSearchedMoviesUseCase,
        private val saveMovieUseCase: SaveMovieUseCase
) : ViewModel() {
    val movies: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }
    val save: MutableLiveData<Any> by lazy { MutableLiveData<Any>() }

    fun searchMoviesByTitle(data: String) = searchMoviesByTitleUseCase.execute(data) {
        onComplete { movies.value = it?.toMutableList() }
    }

    fun getSearchedMovies() = getSearchedMoviesUseCase.execute {
        onComplete { movies.value = it?.toMutableList() }
    }

    fun save(data: Movie?) = saveMovieUseCase.execute(data) {
        onComplete { save.value = it }
    }
}
