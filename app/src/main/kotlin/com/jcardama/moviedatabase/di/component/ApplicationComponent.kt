package com.jcardama.moviedatabase.di.component

import android.app.Application
import com.jcardama.moviedatabase.core.App
import com.jcardama.moviedatabase.di.builder.ActivityBuilder
import com.jcardama.moviedatabase.di.module.ContextModule
import com.jcardama.moviedatabase.di.module.DatabaseModule
import com.jcardama.moviedatabase.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ContextModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    ActivityBuilder::class
])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
