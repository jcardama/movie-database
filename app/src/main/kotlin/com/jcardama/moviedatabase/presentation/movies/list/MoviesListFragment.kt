package com.jcardama.moviedatabase.presentation.movies.list

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.core.extensions.*
import com.jcardama.moviedatabase.core.util.GridSpacingItemDecoration
import com.jcardama.moviedatabase.core.usecase.State
import com.jcardama.moviedatabase.domain.entity.MovieEntity
import com.jcardama.moviedatabase.presentation.base.BaseFragment
import com.jcardama.moviedatabase.presentation.movies.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies_list.view.progress_bar
import kotlinx.android.synthetic.main.fragment_watch_list.view.empty_layout
import kotlinx.android.synthetic.main.fragment_watch_list.view.recycler_view
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesListFragment : BaseFragment() {
    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.recycler_view.layoutManager = GridLayoutManager(requireContext(), resources.getInteger(R.integer.columns))
        view.recycler_view.addItemDecoration(GridSpacingItemDecoration(resources.getInteger(R.integer.columns), activity?.dpToPx(15f) ?: 0, true))

        view.recycler_view.initAdapter<MovieEntity>(R.layout.item_movie).onBindView { itemView, item, _ ->
            itemView.cover_image_view.loadFromUrl("${Config.POSTER_BASE_URL}${item?.posterPath}")

            itemView.title_text_view.text = item?.title

            itemView.favorite_image_view.setVectorTint(when (item?.favorite) {
                true -> R.color.red_700
                else -> R.color.textColorPrimary
            })

            itemView.favorite_image_view.setOnClickListener {
                item?.favorite = !(item?.favorite ?: false)
                Toast.makeText(requireContext(), when (item?.favorite) {
                    true -> R.string.message_movie_added_to_favorites
                    else -> R.string.message_movie_removed_from_favorites
                }, Toast.LENGTH_LONG).show()
                itemView.favorite_image_view.setVectorTint(when (item?.favorite) {
                    true -> R.color.red_700
                    else -> R.color.textColorPrimary
                })
                viewModel.setMovieFavorite(item?.id ?: 0L, item?.favorite ?: false)
            }

            itemView.watch_list_image_view.setImageResource(when (item?.addedToWatchList) {
                true -> R.drawable.ic_playlist_add_check
                else -> R.drawable.ic_playlist_add
            })

            itemView.watch_list_image_view.setOnClickListener {
                item?.addedToWatchList = !(item?.addedToWatchList ?: false)
                Toast.makeText(requireContext(), when (item?.addedToWatchList) {
                    true -> R.string.message_movie_added_to_watch_list
                    else -> R.string.message_movie_removed_from_watch_list
                }, Toast.LENGTH_LONG).show()
                itemView.watch_list_image_view.setImageResource(when (item?.addedToWatchList) {
                    true -> R.drawable.ic_playlist_add_check
                    else -> R.drawable.ic_playlist_add
                })
                viewModel.setMovieWatchList(item?.id ?: 0L, item?.addedToWatchList ?: false)
            }
        }.onClick { _, item, _ ->
            viewModel.movieId.value = item?.id
        }

        with(viewModel) {
            getMovies()

            movies.observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> view.progress_bar.fadeIn()
                    State.SUCCESS -> view.progress_bar.fadeOut(ANIMATION_DURATION_TIME, object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            when {
                                it.data.isNullOrEmpty() -> {
                                    view.empty_layout.show()
                                    view.recycler_view.getTypedAdapter<MovieEntity>().clear()
                                }
                                else -> {
                                    view.empty_layout.hide()
                                    view.recycler_view.getTypedAdapter<MovieEntity>().setItems(it.data?.toMutableList() ?: mutableListOf())
                                }
                            }
                        }
                    })
                    State.ERROR, State.CANCELLED -> view.progress_bar.fadeOut(ANIMATION_DURATION_TIME, object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            when (view.recycler_view.adapter?.itemCount) {
                                null -> view.empty_layout.show()
                                0 -> view.empty_layout.show()
                                else -> {
                                    view.empty_layout.hide()
                                    view.recycler_view.getTypedAdapter<MovieEntity>().setItems(it.data?.toMutableList() ?: mutableListOf())
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}
