@file:Suppress("unused")

package com.jcardama.moviedatabase.util.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.jcardama.moviedatabase.util.extension.activity

fun ViewPager2.initAdapter(): ViewPagerFragmentAdapter? {
    adapter = ViewPagerFragmentAdapter(activity as FragmentActivity)
    return adapter as ViewPagerFragmentAdapter
}

class ViewPagerFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    var fragments: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun setFragments(fragments: MutableList<Class<out Fragment>>): ViewPagerFragmentAdapter? {
        for(fragment in fragments) {
            this.fragments.add(fragment.newInstance())
        }
        return this
    }
}
