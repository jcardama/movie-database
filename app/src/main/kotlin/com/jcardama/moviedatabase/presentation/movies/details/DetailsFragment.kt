package com.jcardama.moviedatabase.presentation.movies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.core.usecase.State
import com.jcardama.moviedatabase.presentation.base.BaseFragment
import com.jcardama.moviedatabase.presentation.movies.MoviesViewModel
import com.jcardama.moviedatabase.core.extensions.loadFromUrl
import kotlinx.android.synthetic.main.fragment_movie.view.*

class DetailsFragment : BaseFragment() {
    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_movie, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        view.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        with(viewModel) {
            getMovieById(movieId.value ?: 0L)

            movieId.value = null

            movie.observe(viewLifecycleOwner) {
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when (it.state) {
                    State.SUCCESS -> {
                        view.backdrop_image_view.loadFromUrl("${Config.BACKDROP_BASE_URL}${it.data?.backdropPath}")
                        view.cover_image_view.loadFromUrl("${Config.POSTER_BASE_URL}${it.data?.posterPath}")
                        view.title_text_view.text = it.data?.title
                        view.release_date_text_view.text = getString(R.string.hint_release_date, it.data?.releaseDate)
                        view.score_text_view.text = it.data?.voteAverage?.toString()
                        view.progress_bar.progress = it.data?.voteAverage?.toInt() ?: 0
                        view.overview_text_view.text = it.data?.overview
                    }
                }
            }
        }
    }
}
