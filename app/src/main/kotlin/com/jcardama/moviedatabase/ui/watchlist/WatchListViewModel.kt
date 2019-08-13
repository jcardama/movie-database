package com.jcardama.moviedatabase.ui.watchlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.usecase.GetWatchListMoviesUseCase
import com.jcardama.moviedatabase.domain.usecase.SaveMovieUseCase
import javax.inject.Inject

class WatchListViewModel @Inject constructor(
        private val getWatchListMoviesUseCase: GetWatchListMoviesUseCase,
        private val saveMovieUseCase: SaveMovieUseCase
) : ViewModel() {
    val movies: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }
    val movieSave: MutableLiveData<Any> by lazy { MutableLiveData<Any>() }

    fun getWatchList() = getWatchListMoviesUseCase.execute {
        onComplete { movies.value = it?.toMutableList() }
    }

    fun saveMovie(data: Movie?) = saveMovieUseCase.execute(data) {
        onComplete { movieSave.value = it }
    }
}
