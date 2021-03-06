package com.jcardama.moviedatabase.presentation.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.presentation.base.BaseFragment
import com.jcardama.moviedatabase.core.extensions.fadeIn
import com.jcardama.moviedatabase.core.extensions.scaleFadeIn
import com.jcardama.moviedatabase.core.extensions.navigateSafe
import kotlinx.android.synthetic.main.fragment_splash.view.*

class SplashFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.logo_image_view.scaleFadeIn(500, object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) = run {
                view.splash_progress_bar.fadeIn(250, object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        view.post {
                            findNavController().navigateSafe(SplashFragmentDirections.actionSplashFragmentToMoviesFragment())
                        }
                    }
                })
            }
        })
    }
}
