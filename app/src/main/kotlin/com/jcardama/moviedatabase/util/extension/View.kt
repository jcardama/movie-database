@file:Suppress("unused", "UNUSED_PARAMETER")

package com.jcardama.moviedatabase.util.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout

fun View.showKeyboard() {
	try {
		val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
	} catch (e: Exception) {
		e.printStackTrace()
	}
}

const val ANIMATION_DURATION_TIME = 250L

//show
fun View.show() {
	visibility = View.VISIBLE
}

//hide
fun View.hide() {
	visibility = View.GONE
}

//expand
fun View.expand() = startAnimation(expand(this))

fun View.expand(animListener: Animation.AnimationListener) = startAnimation(expand(this).apply {
	setAnimationListener(animListener)
})

private fun expand(view: View): Animation {
	view.apply {
		measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
		layoutParams.height = 0
		visibility = View.VISIBLE
	}

	return object : Animation() {
		override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
			view.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
			view.requestLayout()
		}

		override fun willChangeBounds(): Boolean {
			return true
		}
	}.apply {
		duration = ((view.measuredHeight / view.context.resources.displayMetrics.density).toInt()).toLong()
	}
}

//collapse
fun View.collapse() = startAnimation(collapse(this))

fun View.collapse(animListener: Animation.AnimationListener) = startAnimation(collapse(this).apply {
	setAnimationListener(animListener)
})

private fun collapse(view: View): Animation {
	view.apply {
		measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
	}

	return object : Animation() {
		override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
			if (interpolatedTime == 1f) {
				view.visibility = View.GONE
			} else {
				view.layoutParams.height = view.measuredHeight - (view.measuredHeight * interpolatedTime).toInt()
				view.requestLayout()
			}
		}

		override fun willChangeBounds(): Boolean {
			return true
		}
	}.apply {
		duration = ((view.measuredHeight / view.context.resources.displayMetrics.density).toInt()).toLong()
	}
}

//flyInDown
fun View.flyInDown() = flyInDown(this).start()

fun View.flyInDown(animListener: AnimatorListenerAdapter) = flyInDown(this).apply {
	setListener(animListener)
}.start()

private fun flyInDown(view: View): ViewPropertyAnimator = view.apply {
	measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
	visibility = View.VISIBLE
	alpha = 0.0f
	translationY = (-view.measuredHeight).toFloat()
}.animate().setDuration(200).translationY(0.0f).alpha(1.0f)

//flyOutDown
fun View.flyOutDown() = flyOutDown(this).start()

fun View.flyOutDown(animListener: AnimatorListenerAdapter) = flyOutDown(this).apply {
	setListener(animListener)
}.start()

private fun flyOutDown(view: View): ViewPropertyAnimator = view.apply {
	measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
	visibility = View.VISIBLE
	alpha = 1.0f
	translationY = 0.0f
}.animate().setDuration(200).translationY(view.measuredHeight.toFloat()).alpha(0.0f)

//flyOutUp
fun View.flyOutUp() = flyOutUp(this).start()

fun View.flyOutUp(animListener: AnimatorListenerAdapter) = flyOutUp(this).apply {
	setListener(animListener)
}.start()

private fun flyOutUp(view: View): ViewPropertyAnimator = view.apply {
	measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
	visibility = View.VISIBLE
	alpha = 1.0f
	translationY = 0.0f
}.animate().setDuration(200).translationY(-view.measuredHeight.toFloat()).alpha(0.0f)

//fadeIn
fun View.fadeIn(duration: Long = ANIMATION_DURATION_TIME) = fadeIn(this, duration).start()

fun View.fadeIn(duration: Long = ANIMATION_DURATION_TIME, animListener: AnimatorListenerAdapter) = fadeIn(this,
                                                                                                                  duration).apply {
	setListener(animListener)
}.start()

private fun fadeIn(view: View, duration: Long): ViewPropertyAnimator = view.apply {
	alpha = 0.0f
	visibility = View.VISIBLE
}.animate().setDuration(duration).alpha(1.0f)

//fadeOut
fun View.fadeOut(duration: Long = ANIMATION_DURATION_TIME) = fadeOut(this, duration).start()

fun View.fadeOut(duration: Long = ANIMATION_DURATION_TIME, animListener: AnimatorListenerAdapter) = fadeOut(
	this, duration).apply {
	setListener(animListener)
}.start()

private fun fadeOut(view: View, duration: Long): ViewPropertyAnimator = view.apply {
	alpha = 1.0f
}.animate().setDuration(200).alpha(0.0f)

//scaleIn
fun View.scaleIn(duration: Long = ANIMATION_DURATION_TIME) = scaleIn(this).start()

fun View.scaleIn(duration: Long = ANIMATION_DURATION_TIME,
                 animListener: AnimatorListenerAdapter) = scaleIn(this, duration).apply {
	setListener(animListener)
}.start()

private fun scaleIn(view: View,
                    duration: Long = ANIMATION_DURATION_TIME): ViewPropertyAnimator = view.apply {
	scaleX = 0.0f
	scaleY = 0.0f
	visibility = View.VISIBLE
}.animate().setDuration(duration).scaleX(1.0f).scaleY(1.0f)

//scaleOut
fun View.scaleOut() = scaleOut(this).start()

fun View.scaleOut(animListener: AnimatorListenerAdapter) = scaleOut(this).apply {
	setListener(animListener)
}.start()

private fun scaleOut(view: View): ViewPropertyAnimator = view.apply {
	scaleX = 1.0f
	scaleY = 1.0f
}.animate().setDuration(200).scaleX(0.0f).scaleY(0.0f)

//scaleFadeIn
fun View.scaleFadeIn(duration: Long = ANIMATION_DURATION_TIME) = scaleFadeIn(this, duration).start()

fun View.scaleFadeIn(duration: Long = ANIMATION_DURATION_TIME,
                     animListener: Animator.AnimatorListener) = scaleFadeIn(this, duration).apply {
	addListener(animListener)
}.start()

private fun scaleFadeIn(view: View,
                        duration: Long = ANIMATION_DURATION_TIME): AnimatorSet = AnimatorSet().apply {
	view.visibility = View.VISIBLE
	view.alpha = 0.0f
	this.play(ObjectAnimator.ofFloat(view, "scaleX", 5.0f, 1.0f).apply {
		interpolator = AccelerateDecelerateInterpolator()
		this.duration = duration
	}).with(ObjectAnimator.ofFloat(view, "scaleY", 5.0f, 1.0f).apply {
		interpolator = AccelerateDecelerateInterpolator()
		this.duration = duration
	}).with(ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f).apply {
		interpolator = AccelerateDecelerateInterpolator()
		this.duration = duration
	})
	this.startDelay = 0
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
	this.visibility = View.VISIBLE
}

fun View.invisible() {
	this.visibility = View.GONE
}

fun View.circularReveal(centerX: Int, centerY: Int, duration: Long) = when {
	Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && viewTreeObserver.isAlive -> ViewAnimationUtils.createCircularReveal(
		this, centerX, centerY, 0f, (Math.max(this.width, this.height) * 1.1).toFloat()).apply {
		setDuration(duration)
		interpolator = AccelerateInterpolator()
	}.start()
	else -> visibility = View.VISIBLE
}

fun View.circularHide(centerX: Int, centerY: Int, duration: Long, listener: () -> Unit?) = when {
	Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> ViewAnimationUtils.createCircularReveal(this, centerX,
	                                                                                                 centerY, (Math.max(
		this.width, this.height) * 1.1).toFloat(), 0f).apply {
		setDuration(duration)
		addListener(object : AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator) {
				listener()
				super.onAnimationEnd(animation)
			}
		})
	}.start()
	else -> listener()
}
