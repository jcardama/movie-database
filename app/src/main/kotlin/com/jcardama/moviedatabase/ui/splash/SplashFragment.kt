package com.jcardama.moviedatabase.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.ui.base.BaseFragment
import com.jcardama.moviedatabase.ui.movies.MoviesFragment
import com.jcardama.moviedatabase.util.extension.fadeIn
import com.jcardama.moviedatabase.util.extension.loadFragment
import com.jcardama.moviedatabase.util.extension.scaleFadeIn
import kotlinx.android.synthetic.main.fragment_splash.view.logo_image_view
import kotlinx.android.synthetic.main.fragment_splash.view.splash_progress_bar

class SplashFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.logo_image_view.scaleFadeIn(500, object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) = run {
                view.splash_progress_bar.fadeIn(250, object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        activity.loadFragment(MoviesFragment::class.java, true)
                    }
                })
            }
        })
    }
}
