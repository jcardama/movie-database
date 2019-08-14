package com.jcardama.moviedatabase.ui.movies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.ui.base.BaseFragment
import com.jcardama.moviedatabase.ui.movies.MoviesViewModel
import com.jcardama.moviedatabase.util.extension.loadFromUrl
import com.jcardama.moviedatabase.util.extension.setVectorTint
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
        view.toolbar.inflateMenu(R.menu.movie)


        with(viewModel) {
            getMovieById(arguments?.getInt("id") ?: 0)

            movie.observe(this@DetailsFragment, Observer { movie ->
                view.toolbar.menu.findItem(R.id.watch_list_item).setIcon(when (movie.addedToWatchList) {
                    true -> R.drawable.ic_playlist_add_check
                    else -> R.drawable.ic_playlist_add
                })
                view.toolbar.menu.findItem(R.id.favorite_item).setVectorTint(context!!, when (movie.favorite) {
                    true -> R.color.red_700
                    else -> R.color.textColorPrimaryInverse
                })
                view.toolbar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.favorite_item -> {
                            movie.favorite = !movie.favorite
                            menuItem.setVectorTint(context!!, when (movie.favorite) {
                                true -> R.color.red_700
                                else -> R.color.textColorPrimaryInverse
                            })
                            Toast.makeText(context!!, when (movie.favorite) {
                                true -> R.string.message_movie_added_to_favorites
                                else -> R.string.message_movie_removed_from_favorites
                            }, Toast.LENGTH_LONG).show()
                            viewModel.save(movie)
                        }
                        R.id.watch_list_item -> {
                            movie.addedToWatchList = !movie.addedToWatchList
                            menuItem.setIcon(when (movie.addedToWatchList) {
                                true -> R.drawable.ic_playlist_add_check
                                else -> R.drawable.ic_playlist_add
                            })
                            Toast.makeText(context!!, when (movie.addedToWatchList) {
                                true -> R.string.message_movie_added_to_watch_list
                                else -> R.string.message_movie_removed_from_watch_list
                            }, Toast.LENGTH_LONG).show()
                            viewModel.save(movie)
                        }
                    }
                    true
                }
                view.backdrop_image_view.loadFromUrl("${Config.BACKDROP_BASE_URL}${movie.backdropPath}")
                view.cover_image_view.loadFromUrl("${Config.POSTER_BASE_URL}${movie.posterPath}")
                view.title_text_view.text = movie.title
                view.release_date_text_view.text = getString(R.string.hint_release_date, movie.releaseDate)
                view.score_text_view.text = movie.voteAverage.toString()
                view.progress_bar.progress = movie.voteAverage?.toInt() ?: 0
                view.overview_text_view.text = movie.overview
            })
        }
    }
}
