package com.jcardama.moviedatabase.core.extensions

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.jcardama.moviedatabase.core.util.RecyclerViewAdapterUtil

@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.initAdapter(layout: Int): RecyclerViewAdapterUtil<T> {
    adapter = RecyclerViewAdapterUtil<T>(context, layout)
    return adapter as RecyclerViewAdapterUtil<T>
}

@Suppress("UNCHECKED_CAST")
fun <T> ViewPager2.initAdapter(layout: Int): RecyclerViewAdapterUtil<T> {
    adapter = RecyclerViewAdapterUtil<T>(context, layout)
    return adapter as RecyclerViewAdapterUtil<T>
}

@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.getTypedAdapter(): RecyclerViewAdapterUtil<T> {
    return adapter as RecyclerViewAdapterUtil<T>
}

@Suppress("UNCHECKED_CAST")
fun <T> ViewPager2.getTypedAdapter(): RecyclerViewAdapterUtil<T> {
    return adapter as RecyclerViewAdapterUtil<T>
}
