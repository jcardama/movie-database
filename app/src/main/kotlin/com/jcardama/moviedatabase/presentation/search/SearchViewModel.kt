package com.jcardama.moviedatabase.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jcardama.moviedatabase.core.usecase.NoParams
import com.jcardama.moviedatabase.domain.usecase.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val searchMoviesByTitleUseCase: SearchMoviesByTitleUseCase,
        private val getSearchedMoviesUseCase: GetSearchedMoviesUseCase,
        private val setMovieAsSearchedUseCase: SetMovieAsSearchedUseCase
) : ViewModel() {
    private val searchMoviesByTitleParams = MutableLiveData<SearchMoviesByTitleParams>()
    val movies = Transformations.switchMap(searchMoviesByTitleParams) {
        searchMoviesByTitleUseCase.executeLive(it)
    }

    private val searchedMoviesParams = MutableLiveData<NoParams>()
    val searchedMovies = Transformations.switchMap(searchedMoviesParams) {
        getSearchedMoviesUseCase.executeLive(it)
    }

    internal val setMovieAsSearchedParams = MutableLiveData<SetMovieAsSearchedParams>()
    val setMovieAsSearched = Transformations.switchMap(setMovieAsSearchedParams) {
        setMovieAsSearchedUseCase.executeLive(it)
    }

    fun searchMoviesByTitle(query: String) {
        searchMoviesByTitleParams.value = SearchMoviesByTitleParams(query)
    }

    fun getSearchedMovies() {
        searchedMoviesParams.value = NoParams()
    }

    fun setMovieAsSearched(id: Long) {
        setMovieAsSearchedParams.value = SetMovieAsSearchedParams(id)
    }
}
