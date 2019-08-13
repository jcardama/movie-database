@file:Suppress("unused")

package com.jcardama.moviedatabase.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import java.util.HashMap

/**
 * Convenient ViewPager with generic data type list.
 * Create an instance and specify the type of data being passed.
 * Implement callbacks to handle the required functions
 *
 * Motivation: Create a view pager adapter in minimal lines of code yet
 * providing high customisation and complete control
 */

open class ViewPagerAdapterUtil<K, T>(private val context: Context,
        private val layout: Int? = null) : androidx.viewpager.widget.PagerAdapter() {
    /**
     * List containing all the pages
     * */
    private var pages: List<T> = mutableListOf()

    private var titles: List<K> = mutableListOf()

    /**
     * Listener to bind the data with the single item view.
     * The View and item of type T provided by the user are passed as the arguments
     * Any view contained in the single view of the RecyclerView can be obtained
     * from itemView, and the required data can be set from item
     **/
    private var onInstantiateItemListener: ((page: T, view: View, position: Int) -> Unit)? = null

    private var tabTitleEnabled = true

    /**
     * Setters for itemList
     */
    fun setPages(pages: HashMap<K, T>) {
        this.pages = pages.map { it.value }
        this.titles = pages.map { it.key }
        notifyDataSetChanged()
    }

    fun clear() {
        this.pages = mutableListOf()
        this.titles = mutableListOf()
        notifyDataSetChanged()
    }

    fun addInstantiateItemListener(listener: ((page: T, view: View, position: Int) -> Unit)) {
        this.onInstantiateItemListener = listener
    }

    fun setTabTitleEnabled(tabTitleEnabled: Boolean) {
        this.tabTitleEnabled = tabTitleEnabled
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = when {
            layout != null -> inflater.inflate(layout, container, false)
            else -> inflater.inflate(pages[position] as Int, container, false)
        }

        onInstantiateItemListener?.invoke(pages[position], view, position)

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)

        return view
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titles[position] is String) titles[position] as String else super.getPageTitle(
                position)
    }

    /**
     * Builder class for setting up recycler adapter
     */
    open class Builder<K, T>(context: Context, layout: Int? = null) {
        private var adapter: ViewPagerAdapterUtil<K, T> = ViewPagerAdapterUtil(context, layout)

        /*
        * Set pages and return builder
         */
        fun pages(pages: HashMap<K, T>): Builder<K, T> {
            adapter.setPages(pages)
            return this
        }

        fun setTabTitleEnabled(tabTitleEnabled: Boolean): Builder<K, T> {
            adapter.setTabTitleEnabled(tabTitleEnabled)
            return this
        }

        /*
        * Adds instantiate listener
        */
        fun addInstantiateItemListener(
                listener: ((page: T, view: View, position: Int) -> Unit)): Builder<K, T> {
            adapter.addInstantiateItemListener(listener)
            return this
        }

        /*
        * Builds the adapter
        */
        fun build(): ViewPagerAdapterUtil<K, T> = adapter

        /*
        * Adds the adapter to a ViewPager
        */
        fun into(viewPager: ViewPager) {
            viewPager.adapter = adapter
        }
    }
}

