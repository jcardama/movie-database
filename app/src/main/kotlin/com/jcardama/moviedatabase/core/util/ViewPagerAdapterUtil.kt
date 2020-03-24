@file:Suppress("unused")

package com.jcardama.moviedatabase.core.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapterUtil(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var fragments: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun setFragments(fragments: MutableList<Class<out Fragment>>): ViewPagerAdapterUtil? {
        for (fragment in fragments) {
            this.fragments.add(fragment.newInstance())
        }
        return this
    }
}
