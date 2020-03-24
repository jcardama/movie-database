@file:Suppress("NON_EXHAUSTIVE_WHEN")

package com.jcardama.moviedatabase.presentation.base

import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
}
