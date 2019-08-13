@file:Suppress("unused")

package com.jcardama.moviedatabase.util.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcardama.moviedatabase.R

open class RecyclerViewAdapterUtil<T>(private val context: Context,
        private val layout: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<Item<T>> = ArrayList()
    internal var loading: Boolean = false
    private var itemsPerPage: Int = 20
    private var progressLayoutId = R.layout.item_progress_bar
    private var handler = Handler()

    enum class ViewType(val type: Int) {
        ITEM(1), LOADER(2)
    }

    private var viewBindListener: ((view: View, item: T?, position: Int) -> Unit)? = null
    private var onClickListener: ((view: View?, item: T?, position: Int) -> Unit)? = null
    private var onLongClickListener: ((view: View?, item: T?, position: Int) -> Unit)? = null
    private var onLoadMoreListener: ((page: Int) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup,
            viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.LOADER.type -> ProgressViewHolder(
                    LayoutInflater.from(p0.context).inflate(this.progressLayoutId, p0, false))
            else -> ViewHolder(LayoutInflater.from(p0.context).inflate(layout, p0, false))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
            position: Int) {
        @Suppress("RemoveRedundantQualifierName")
        when (holder) {
            is RecyclerViewAdapterUtil<*>.ViewHolder -> holder.bindView(position)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = recyclerView.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()

                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + 2)) {
                        onLoadMoreListener?.invoke(
                                (totalItemCount / this@RecyclerViewAdapterUtil.itemsPerPage))
                        loading = true
                    }
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        val item = this.items[position]
        return when {
            item.progress -> ViewType.LOADER.type
            else -> ViewType.ITEM.type
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(
            itemView), View.OnClickListener, View.OnLongClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bindView(position: Int) {
            viewBindListener?.invoke(itemView, items[position].item, position)
        }

        override fun onClick(view: View?) {
            onClickListener?.invoke(view, items[adapterPosition].item, adapterPosition)
        }

        override fun onLongClick(view: View?): Boolean {
            onLongClickListener?.invoke(view, items[adapterPosition].item, adapterPosition)
            return true
        }
    }

    open class HeaderViewHolder(
            itemView: View) : RecyclerView.ViewHolder(itemView)

    open class ProgressViewHolder(
            itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setItems(items: MutableList<T>) {
        this.items = ArrayList(items.map { Item<T>().apply { this.item = it } })
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        this.items.add(Item<T>().apply {
            this.item = item
        })
        notifyItemInserted(this.items.size)
    }

    fun addItems(mItems: ArrayList<T>) {
        val positionStart = this.items.size - 1
        this.items.addAll(mItems.map { Item<T>().apply { this.item = it } })
        notifyItemRangeInserted(positionStart, mItems.size)
    }

    fun clear() {
        val itemCount = this.items.size
        this.items.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    fun setViewBindListener(listener: (view: View, item: T?, position: Int) -> Unit) {
        viewBindListener = listener
    }

    fun setOnClickListener(listener: (view: View?, item: T?, position: Int) -> Unit) {
        onClickListener = listener
    }

    fun setOnLongClickListener(listener: (view: View?, item: T?, position: Int) -> Unit) {
        onLongClickListener = listener
    }

    fun setOnLoadMoreListener(listener: (page: Int) -> Unit) {
        onLoadMoreListener = listener
    }

    fun setLoaded() {
        loading = false
        handler.post {
            for (i in 0 until itemCount) {
                if (i != itemCount && (items[i]).progress) {
                    items.removeAt(i)
                    notifyItemRemoved(i)
                }
            }
        }
    }

    fun setLoading() {
        loading = true
        handler.post {
            val item = Item<T>().apply {
                this.progress = true
            }
            this.items.add(item)
            notifyItemInserted(if (itemCount == 0) itemCount else itemCount - 1)
        }
    }

    fun isLoading(): Boolean {
        return loading
    }

    open class Item<T>(var progress: Boolean = false, var header: Boolean = false,
            var item: T? = null)

    class Builder<T>(context: Context, layout: Int) {
        private var adapter: RecyclerViewAdapterUtil<T> = RecyclerViewAdapterUtil(context, layout)

        fun addItem(item: T): Builder<T> {
            adapter.addItem(item)
            return this
        }

        fun addItems(items: ArrayList<T>): Builder<T> {
            adapter.addItems(items)
            return this
        }

        fun setItems(items: MutableList<T>): Builder<T> {
            adapter.setItems(items)
            return this
        }

        fun clear(): Builder<T> {
            adapter.clear()
            return this
        }

        fun bindView(listener: (view: View, item: T?, position: Int) -> Unit): Builder<T> {
            adapter.setViewBindListener(listener)
            return this
        }

        fun setOnClickListener(
                listener: (view: View?, item: T?, position: Int) -> Unit): Builder<T> {
            adapter.setOnClickListener(listener)
            return this
        }

        fun setOnLongClickListener(
                listener: (view: View?, item: T?, position: Int) -> Unit): Builder<T> {
            adapter.setOnLongClickListener(listener)
            return this
        }

        fun setOnLoadMoreListener(listener: (page: Int) -> Unit): Builder<T> {
            adapter.setOnLoadMoreListener(listener)
            return this
        }

        fun setLoading(): Builder<T> {
            adapter.setLoading()
            return this
        }

        fun setLoaded(): Builder<T> {
            adapter.setLoaded()
            return this
        }

        fun build(): RecyclerViewAdapterUtil<T> = adapter

        fun into(recyclerView: RecyclerView) {
            recyclerView.adapter = adapter
            adapter.onAttachedToRecyclerView(recyclerView)
        }
    }
}
