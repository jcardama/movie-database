package com.jcardama.moviedatabase.ui.movies.favorites

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
import com.jcardama.moviedatabase.ui.movies.MoviesViewModel
import com.jcardama.moviedatabase.util.adapter.RecyclerViewAdapterUtil
import com.jcardama.moviedatabase.util.extension.*
import kotlinx.android.synthetic.main.fragment_favorite_movies.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoritesFragment : BaseFragment() {
    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_favorite_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.recycler_view.layoutManager = GridLayoutManager(context!!, 2)
        view.recycler_view.addItemDecoration(
                GridSpacingItemDecoration(2, activity?.dpToPx(15f) ?: 0, true)
        )

        RecyclerViewAdapterUtil.Builder<Movie>(context!!, R.layout.item_movie).bindView { itemView, item, _ ->
            itemView.cover_image_view.loadFromUrl("${Config.POSTER_BASE_URL}${item?.posterPath}")

            itemView.title_text_view.text = item?.title

            itemView.favorite_image_view.setVectorTint(when (item?.favorite) {
                true -> R.color.red_700
                else -> R.color.textColorPrimary
            })

            itemView.favorite_image_view.setOnClickListener {
                item?.favorite = false
                itemView.favorite_image_view.setVectorTint(R.color.textColorPrimary)
                viewModel.save(item)
            }

            itemView.watch_list_image_view.setImageResource(when (item?.addedToWatchList) {
                true -> R.drawable.ic_playlist_add_check
                else -> R.drawable.ic_playlist_add
            })

            itemView.watch_list_image_view.setOnClickListener {
                item?.addedToWatchList = !(item?.addedToWatchList ?: false)
                itemView.watch_list_image_view.setImageResource(when (item?.addedToWatchList) {
                    true -> R.drawable.ic_playlist_add
                    else -> R.drawable.ic_playlist_add_check
                })
                viewModel.save(item)
            }
        }.setOnClickListener { _, item, _ ->
            viewModel.movie.value = item
        }.into(view.recycler_view)

        with(viewModel) {
            getFavoriteMovies()

            favoriteMovies.observe(this@FavoritesFragment, Observer { movies ->
                when {
                    movies.isNullOrEmpty() -> view.empty_layout.fadeIn()
                    else -> view.empty_layout.hide()
                }

                (view.recycler_view.adapter as RecyclerViewAdapterUtil<Movie>).setItems(movies ?: mutableListOf())
            })
        }
    }
}
