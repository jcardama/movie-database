package com.jcardama.moviedatabase.di.builder

import androidx.lifecycle.ViewModelProvider
import com.jcardama.moviedatabase.util.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [(RepositoryBuilder::class), (AppViewModelBuilder::class)])
abstract class ViewModelBuilder {
    @Suppress("unused")
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
