package com.jcardama.moviedatabase.di.module

import android.app.Application
import android.content.Context
import com.jcardama.moviedatabase.di.builder.ViewModelBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelBuilder::class])
class ContextModule {
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application
}
