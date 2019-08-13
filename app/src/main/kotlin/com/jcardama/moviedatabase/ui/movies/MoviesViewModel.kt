package com.jcardama.moviedatabase.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.model.response.ErrorModel
import com.jcardama.moviedatabase.domain.usecase.GetAllMoviesUseCase
import com.jcardama.moviedatabase.domain.usecase.GetFavoriteMoviesUseCase
import com.jcardama.moviedatabase.domain.usecase.SaveMovieUseCase
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
        private val getAllMoviesUseCase: GetAllMoviesUseCase,
        private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
        private val saveMovieUseCase: SaveMovieUseCase
) : ViewModel() {
    val movies: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }
    val favoriteMovies: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }
    val movieSave: MutableLiveData<Any> by lazy { MutableLiveData<Any>() }
    val movieSaveError: MutableLiveData<ErrorModel> by lazy { MutableLiveData<ErrorModel>() }

    fun getMovies() = getAllMoviesUseCase.execute {
        onComplete { movies.value = it?.toMutableList() }
        onError { movies.value = mutableListOf() }
    }

    fun getFavoriteMovies() = getFavoriteMoviesUseCase.execute {
        onComplete { favoriteMovies.value = it?.toMutableList() }
    }

    fun saveMovie(data: Movie?) = saveMovieUseCase.execute(data) {
        onComplete { movieSave.value = it }
        onError { movieSaveError.value = it }
    }
}
