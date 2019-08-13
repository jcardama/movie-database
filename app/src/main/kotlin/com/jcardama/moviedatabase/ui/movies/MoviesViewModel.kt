package com.jcardama.moviedatabase.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.domain.usecase.*
import com.jcardama.moviedatabase.util.extension.*
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
        private val getAllMoviesUseCase: GetAllMoviesUseCase,
        private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
        private val getWatchListMoviesUseCase: GetWatchListMoviesUseCase,
        private val getMovieByIdUseCase: GetMovieByIdUseCase,
        private val saveMovieUseCase: SaveMovieUseCase
) : ViewModel() {
    val movies: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }
    val movie: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }
    val favoriteMovies: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }
    val watchListMovies: MutableLiveData<MutableList<Movie>> by lazy { MutableLiveData<MutableList<Movie>>() }

    fun getMovieById(id: Int) = getMovieByIdUseCase.execute(id) {
        onComplete { movie.value = it }
    }

    fun getMovies() = getAllMoviesUseCase.execute {
        onComplete { movies.value = it?.toMutableList() }
    }

    fun getFavoriteMovies() = getFavoriteMoviesUseCase.execute {
        onComplete { favoriteMovies.value = it?.toMutableList() }
    }

    fun getWatchListMovies() = getWatchListMoviesUseCase.execute {
        onComplete { watchListMovies.value = it?.toMutableList() }
    }

    fun save(data: Movie?) = saveMovieUseCase.execute(data) {
        onComplete {
            favoriteMovies.value = favoriteMovies.value?.addOrRemoveIf(data!!) { !data.favorite }
            watchListMovies.value = watchListMovies.value?.addOrRemoveIf(data!!) { !data.addedToWatchList }
            movies.value = movies.value?.replace(data!!) { it.id == data.id }
        }
    }
}
