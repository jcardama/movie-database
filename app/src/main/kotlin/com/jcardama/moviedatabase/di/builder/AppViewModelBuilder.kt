@file:Suppress("unused")

package com.jcardama.moviedatabase.di.builder

import androidx.lifecycle.ViewModel
import com.jcardama.moviedatabase.di.qualifier.ViewModelKey
import com.jcardama.moviedatabase.ui.movies.MoviesViewModel
import com.jcardama.moviedatabase.ui.watchlist.WatchListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AppViewModelBuilder { }
