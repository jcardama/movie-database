@file:Suppress("UNCHECKED_CAST")

package com.jcardama.moviedatabase.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.core.extensions.initAdapter
import com.jcardama.moviedatabase.core.usecase.State
import com.jcardama.moviedatabase.presentation.base.BaseFragment
import com.jcardama.moviedatabase.presentation.movies.favorites.FavoritesFragment
import com.jcardama.moviedatabase.presentation.movies.list.MoviesListFragment
import com.jcardama.moviedatabase.core.extensions.show
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.android.synthetic.main.view_appbar_title.view.*

class MoviesFragment : BaseFragment(), NavigationView.OnNavigationItemSelectedListener {
    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.toolbar.title = getString(R.string.app_name)
        view.toolbar.setNavigationOnClickListener {
            view.drawer_layout.openDrawer(GravityCompat.START)
        }
        view.toolbar.inflateMenu(R.menu.search)
        view.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_item -> findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToSearchFragment())
            }
            true
        }

        with(ActionBarDrawerToggle(requireActivity(), view.drawer_layout, view.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)) {
            view.drawer_layout.addDrawerListener(this)
            syncState()
            view.nav_view.setNavigationItemSelectedListener(this@MoviesFragment)
        }

        view.view_pager.initAdapter()?.setFragments(mutableListOf(MoviesListFragment::class.java, FavoritesFragment::class.java))

        TabLayoutMediator(view.tab_layout, view.view_pager) { tab, position ->
            tab.text = listOf("Movies", "Favorites")[position]
        }.attach()

        view.tab_layout.show()

        with(viewModel) {
            movieId.observe(viewLifecycleOwner) {
                when {
                    it != null -> findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment())
                }
            }

            setMovieFavorite.observe(viewLifecycleOwner) {
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when(it.state) {
                    State.SUCCESS -> {
                        getMovies()
                        getFavoriteMovies()
                    }
                }
            }

            setMovieWatchList.observe(viewLifecycleOwner) {
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when(it.state) {
                    State.SUCCESS -> {
                        getMovies()
                        getWatchListMovies()
                    }
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.watchlist_item -> findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToWatchListFragment())
        }

        view?.drawer_layout?.closeDrawer(GravityCompat.START)

        return super.onOptionsItemSelected(item)
    }
}
