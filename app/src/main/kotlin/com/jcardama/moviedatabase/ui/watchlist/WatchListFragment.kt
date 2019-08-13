@file:Suppress("UNCHECKED_CAST")

package com.jcardama.moviedatabase.ui.watchlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.ui.base.BaseFragment
import com.jcardama.moviedatabase.util.adapter.RecyclerViewAdapterUtil
import com.jcardama.moviedatabase.util.extension.*
import kotlinx.android.synthetic.main.fragment_watch_list.view.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.view_appbar_title.view.*

class WatchListFragment : BaseFragment() {
    private val viewModel: WatchListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(WatchListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_watch_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.toolbar.title = getString(R.string.title_watch_list)
        view.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        view.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        view.recycler_view.layoutManager = GridLayoutManager(context!!, 2)
        view.recycler_view.addItemDecoration(
                GridSpacingItemDecoration(2, activity?.dpToPx(15f) ?: 0, true)
        )

        RecyclerViewAdapterUtil.Builder<Movie>(context!!, R.layout.item_movie)
                .bindView { itemView, item, _ ->
                    itemView.cover_image_view.loadFromUrl("${Config.POSTER_BASE_URL}${item?.posterPath}")

                    itemView.title_text_view.text = item?.title

                    itemView.favorite_image_view.setVectorTint(when (item?.favorite) {
                        true -> R.color.red_700
                        else -> R.color.textColorPrimary
                    })

                    itemView.favorite_image_view.setOnClickListener {
                        with(viewModel) {
                            saveMovie(item?.apply { favorite = !favorite })

                            movieSave.observe(this@WatchListFragment, Observer {
                                itemView.favorite_image_view.setVectorTint(when (item?.favorite) {
                                    true -> R.color.red_700
                                    else -> R.color.textColorPrimary
                                })
                            })
                        }
                    }

                    itemView.watch_list_image_view.setImageResource(when (item?.addedToWatchList) {
                        true -> R.drawable.ic_playlist_add_check
                        else -> R.drawable.ic_playlist_add
                    })

                    itemView.watch_list_image_view.setOnClickListener {
                        with(viewModel) {
                            saveMovie(item?.apply { addedToWatchList = !addedToWatchList })
                            movieSave.observe(this@WatchListFragment, Observer {
                                movies.minusAssign(listOf(item))
                            })
                        }
                    }
                }
                .into(view.recycler_view)

        with(viewModel) {
            getWatchList()

            movies.observe(this@WatchListFragment, Observer {movies ->
                view.empty_layout.hide()
                when(movies.size) {
                    0 -> view.recycler_view.fadeOut(ANIMATION_DURATION_TIME, object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            view.empty_layout.show()
                        }
                    })
                    else -> (view.recycler_view.adapter as RecyclerViewAdapterUtil<Movie>).setItems(movies)
                }
            })
        }
    }
}
