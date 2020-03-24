package com.jcardama.moviedatabase.core.extensions

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.jcardama.moviedatabase.core.util.ViewPagerAdapterUtil

fun ViewPager2.initAdapter(): ViewPagerAdapterUtil? {
    adapter = ViewPagerAdapterUtil(this.context as FragmentActivity)
    return adapter as ViewPagerAdapterUtil
}
