@file:Suppress("unused")

package com.jcardama.moviedatabase.di.builder

import com.jcardama.moviedatabase.presentation.base.BaseFragment
import com.jcardama.moviedatabase.presentation.movies.MoviesFragment
import com.jcardama.moviedatabase.presentation.movies.details.DetailsFragment
import com.jcardama.moviedatabase.presentation.movies.favorites.FavoritesFragment
import com.jcardama.moviedatabase.presentation.movies.list.MoviesListFragment
import com.jcardama.moviedatabase.presentation.search.SearchFragment
import com.jcardama.moviedatabase.presentation.splash.SplashFragment
import com.jcardama.moviedatabase.presentation.movies.watchlist.WatchListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityProviders {
    @ContributesAndroidInjector
    abstract fun provideBaseFragment(): BaseFragment

    @ContributesAndroidInjector
    abstract fun provideSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun provideDashboardFragment(): MoviesFragment

    @ContributesAndroidInjector
    abstract fun provideMoviesListFragment(): MoviesListFragment

    @ContributesAndroidInjector
    abstract fun provideFavoritesFragment(): FavoritesFragment

    @ContributesAndroidInjector
    abstract fun provideWatchListFragment(): WatchListFragment

    @ContributesAndroidInjector
    abstract fun provideDetailsFragment(): DetailsFragment

    @ContributesAndroidInjector
    abstract fun provideSearchFragment(): SearchFragment
}
