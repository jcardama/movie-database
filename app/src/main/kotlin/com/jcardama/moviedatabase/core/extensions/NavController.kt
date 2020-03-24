package com.jcardama.moviedatabase.core.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import timber.log.Timber

fun NavController.navigateSafe(navDirections: NavDirections? = null) {
    try {
        navDirections?.let {
            this.navigate(navDirections)
        }
    } catch (e: Exception) {
        Timber.e(e)
    }
}
