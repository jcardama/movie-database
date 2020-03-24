package com.jcardama.moviedatabase.presentation.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jcardama.moviedatabase.core.usecase.NoParams
import com.jcardama.moviedatabase.domain.usecase.*
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
        private val getAllMoviesUseCase: GetAllMoviesUseCase,
        private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
        private val getWatchListMoviesUseCase: GetWatchListMoviesUseCase,
        private val getMovieByIdUseCase: GetMovieByIdUseCase,
        private val setMovieFavoriteUseCase: SetMovieFavoriteUseCase,
        private val setMovieWatchListUseCase: SetMovieWatchListUseCase
) : ViewModel() {
    val movieId = MutableLiveData<Long?>()
    private val moviesParams = MutableLiveData<GetAllMoviesParams>()
    val movies = Transformations.switchMap(moviesParams) {
        getAllMoviesUseCase.executeLive(it)
    }
    private val favoriteMoviesParams = MutableLiveData<NoParams>()
    val favoriteMovies = Transformations.switchMap(favoriteMoviesParams) {
        getFavoriteMoviesUseCase.executeLive(it)
    }
    private val watchListMoviesParams = MutableLiveData<NoParams>()
    val watchListMovies = Transformations.switchMap(watchListMoviesParams) {
        getWatchListMoviesUseCase.executeLive(it)
    }
    private val movieParams = MutableLiveData<GetMovieByIdParams>()
    val movie = Transformations.switchMap(movieParams) {
        getMovieByIdUseCase.executeLive(it)
    }
    val setMovieFavoriteParams = MutableLiveData<SetMovieFavoriteParams>()
    val setMovieFavorite = Transformations.switchMap(setMovieFavoriteParams) {
        setMovieFavoriteUseCase.executeLive(it)
    }
    val setMovieWatchListParams = MutableLiveData<SetMovieWatchListParams>()
    val setMovieWatchList = Transformations.switchMap(setMovieWatchListParams) {
        setMovieWatchListUseCase.executeLive(it)
    }

    fun getMovies(shouldFetch: Boolean = false) {
        moviesParams.value = GetAllMoviesParams(shouldFetch)
    }

    fun getFavoriteMovies() {
        favoriteMoviesParams.value = NoParams()
    }

    fun getWatchListMovies() {
        watchListMoviesParams.value = NoParams()
    }

    fun getMovieById(id: Long) {
        movieParams.value = GetMovieByIdParams(id)
    }

    fun setMovieFavorite(id: Long, isFavorite: Boolean) {
        setMovieFavoriteParams.value = SetMovieFavoriteParams(id, isFavorite)
    }

    fun setMovieWatchList(id: Long, isInWatchList: Boolean) {
        setMovieWatchListParams.value = SetMovieWatchListParams(id, isInWatchList)
    }
}
