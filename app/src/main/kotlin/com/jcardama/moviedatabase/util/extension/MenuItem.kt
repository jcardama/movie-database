package com.jcardama.moviedatabase.util.extension

import android.content.Context
import android.graphics.PorterDuff
import android.view.MenuItem
import androidx.core.content.ContextCompat


fun MenuItem.setVectorTint(context: Context, color: Int) {
    val drawable = icon
    drawable.mutate()
    drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP)
    icon = drawable
}
