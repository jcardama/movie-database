@file:Suppress("unused")

package com.jcardama.moviedatabase.di.builder

import com.jcardama.moviedatabase.ui.base.BaseFragment
import com.jcardama.moviedatabase.ui.movies.MoviesFragment
import com.jcardama.moviedatabase.ui.splash.SplashFragment
import com.jcardama.moviedatabase.ui.watchlist.WatchListFragment
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
	abstract fun provideWatchListFragment(): WatchListFragment
}
