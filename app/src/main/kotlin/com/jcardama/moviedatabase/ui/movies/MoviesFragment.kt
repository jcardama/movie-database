@file:Suppress("UNCHECKED_CAST")

package com.jcardama.moviedatabase.ui.movies

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.ui.base.BaseFragment
import com.jcardama.moviedatabase.ui.watchlist.WatchListFragment
import com.jcardama.moviedatabase.util.adapter.RecyclerViewAdapterUtil
import com.jcardama.moviedatabase.util.adapter.ViewPagerAdapterUtil
import com.jcardama.moviedatabase.util.extension.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.drawer_layout
import kotlinx.android.synthetic.main.fragment_dashboard.view.nav_view
import kotlinx.android.synthetic.main.fragment_dashboard.view.view_pager
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.page_movies.view.empty_layout
import kotlinx.android.synthetic.main.page_movies.view.progress_bar
import kotlinx.android.synthetic.main.page_movies.view.recycler_view
import kotlinx.android.synthetic.main.view_appbar_title.view.tab_layout
import kotlinx.android.synthetic.main.view_appbar_title.view.toolbar

class MoviesFragment : BaseFragment(), NavigationView.OnNavigationItemSelectedListener {
    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.toolbar.title = getString(R.string.app_name)
        view.toolbar.setNavigationOnClickListener {
            view.drawer_layout.openDrawer(GravityCompat.START)
        }

        with(ActionBarDrawerToggle(activity!!, view.drawer_layout, view.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)) {
            view.drawer_layout.addDrawerListener(this)
            syncState()
            view.nav_view.setNavigationItemSelectedListener(this@MoviesFragment)
        }

        view.tab_layout.setupWithViewPager(view.view_pager)
        view.tab_layout.show()

        ViewPagerAdapterUtil.Builder<String, Int>(context!!)
                .pages(linkedMapOf(getString(R.string.title_movies) to R.layout.page_movies, getString(R.string.title_favorite_movies) to R.layout.page_favorite_movies))
                .addInstantiateItemListener { page, pageView, _ ->
                    pageView.recycler_view.layoutManager = GridLayoutManager(context, 2)
                    pageView.recycler_view.addItemDecoration(
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

                                        movieSave.observe(this@MoviesFragment, Observer {
                                            getFavoriteMovies()
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

                                        movieSave.observe(this@MoviesFragment, Observer {
                                            itemView.watch_list_image_view.setImageResource(when (item?.addedToWatchList) {
                                                true -> R.drawable.ic_playlist_add_check
                                                else -> R.drawable.ic_playlist_add
                                            })
                                        })
                                    }
                                }
                            }
                            .into(pageView.recycler_view)

                    when (page) {
                        R.layout.page_movies -> {
                            with(viewModel) {
                                getMovies()
                                movies.observe(this@MoviesFragment, Observer { movies ->
                                    view.progress_bar.fadeOut(ANIMATION_DURATION_TIME, object : AnimatorListenerAdapter() {
                                        override fun onAnimationEnd(animation: Animator?) {
                                            when (movies.size) {
                                                0 -> pageView.empty_layout.fadeIn()
                                                else -> (pageView.recycler_view.adapter as RecyclerViewAdapterUtil<Movie>).setItems(movies)
                                            }
                                        }
                                    })
                                })
                            }
                        }
                        R.layout.page_favorite_movies -> {
                            with(viewModel) {
                                getFavoriteMovies()
                                favoriteMovies.observe(this@MoviesFragment, Observer { movies ->
                                    pageView.empty_layout.hide()
                                    when (movies.size) {
                                        0 -> pageView.empty_layout.fadeIn()
                                        else -> (pageView.recycler_view.adapter as RecyclerViewAdapterUtil<Movie>).setItems(movies)
                                    }
                                })
                            }
                        }
                    }
                }
                .into(view.view_pager)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.watchlist_item -> activity.loadFragment(WatchListFragment::class.java)
            R.id.settings_item -> {
            }
        }

        view?.drawer_layout?.closeDrawer(GravityCompat.START)

        return super.onOptionsItemSelected(item)
    }
}
