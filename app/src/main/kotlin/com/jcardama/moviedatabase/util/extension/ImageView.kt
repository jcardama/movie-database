package com.jcardama.moviedatabase.util.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target

fun ImageView.loadFromUrl(url: String) = Glide.with(this.context.applicationContext).load(
		url).override(Target.SIZE_ORIGINAL).transition(DrawableTransitionOptions.withCrossFade()).into(this)

fun ImageView.setVectorTint(color: Int) = setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN)
