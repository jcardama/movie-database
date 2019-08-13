@file:Suppress("unused")

package com.jcardama.moviedatabase.core

import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import com.jcardama.moviedatabase.BuildConfig
import com.jcardama.moviedatabase.di.component.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

	override fun onCreate() {
		super.onCreate()
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
		Stetho.initializeWithDefaults(this)
		when {
			BuildConfig.DEBUG -> {
				Timber.plant(Timber.DebugTree())
				LeakCanary.install(this)
			}
		}
	}

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
		return DaggerApplicationComponent.builder().application(this).build()
	}
}
