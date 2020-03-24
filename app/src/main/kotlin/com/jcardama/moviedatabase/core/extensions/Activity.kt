package com.jcardama.moviedatabase.core.extensions

import android.app.Activity
import android.util.TypedValue
import kotlin.math.roundToInt

fun Activity.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).roundToInt()
