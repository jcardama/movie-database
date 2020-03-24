package com.jcardama.moviedatabase.di.builder

import androidx.lifecycle.ViewModelProvider
import com.jcardama.moviedatabase.core.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [(DataSourceBuilder::class), (RepositoryBuilder::class), (AppViewModelBuilder::class)])
abstract class ViewModelBuilder {
    @Suppress("unused")
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
