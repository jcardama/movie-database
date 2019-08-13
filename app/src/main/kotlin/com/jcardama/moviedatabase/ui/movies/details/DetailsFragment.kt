package com.jcardama.moviedatabase.ui.movies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.ui.base.BaseFragment
import com.jcardama.moviedatabase.ui.movies.MoviesViewModel
import com.jcardama.moviedatabase.util.extension.loadFromUrl
import kotlinx.android.synthetic.main.fragment_movie.view.*

class DetailsFragment : BaseFragment() {
    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        view.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        with(viewModel) {
            getMovieById(arguments?.getInt("id") ?: 0)

            movie.observe(this@DetailsFragment, Observer {
                view.backdrop_image_view.loadFromUrl("${Config.BACKDROP_BASE_URL}${it.backdropPath}")
                view.cover_image_view.loadFromUrl("${Config.POSTER_BASE_URL}${it.posterPath}")
                view.title_text_view.text = it.title
                view.release_date_text_view.text = "Release date: ${it.releaseDate}"
                view.score_text_view.text = it.voteAverage.toString()
                view.progress_bar.progress = it.voteAverage?.toInt() ?: 0
                view.overview_text_view.text = it.overview
            })
        }
    }
}
