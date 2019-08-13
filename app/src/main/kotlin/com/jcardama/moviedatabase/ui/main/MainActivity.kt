package com.jcardama.moviedatabase.ui.main

import android.os.Bundle
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.ui.splash.SplashFragment
import com.jcardama.moviedatabase.util.extension.loadFragment
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber

class MainActivity : DaggerAppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_base)
		loadFragment(SplashFragment::class.java, true)
	}

	override fun onBackPressed() {
		when (supportFragmentManager.backStackEntryCount) {
			1 -> this.finish()
			else -> super.onBackPressed()
		}
	}
}
