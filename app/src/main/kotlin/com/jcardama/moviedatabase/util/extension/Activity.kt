package com.jcardama.moviedatabase.util.extension

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.jcardama.moviedatabase.R
import kotlin.math.roundToInt

fun Activity.dpToPx(dp: Float): Int = TypedValue
	.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).roundToInt()

fun Activity.hideKeyboard() {
	val view = currentFocus
	if (view != null) {
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(view.windowToken, 0)
	}
}

fun FragmentActivity?.loadFragment(fragment: Class<out Fragment>, clearBackStack: Boolean = false) {
	loadFragment(fragment, Bundle(), clearBackStack)
}

fun FragmentActivity?.loadFragment(fragment: Class<out Fragment>, arguments: Bundle? = Bundle(), clearBackStack: Boolean = false) {
	when {
		clearBackStack -> this?.supportFragmentManager?.popBackStackImmediate(
				0,
				FragmentManager.POP_BACK_STACK_INCLUSIVE
		)
	}

	this?.supportFragmentManager?.beginTransaction()
			?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
			?.add(R.id.container, fragment.newInstance().apply {
				this.arguments = arguments
			}, fragment::class.java.name)
			?.addToBackStack(fragment::class.java.name)
			?.commit()
}
