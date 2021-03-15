package com.minhnv.c9nvm.agt.utils.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R


abstract class RecyclerAdapter<T>(
    context: Context, private val onItemClick: ((T) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val loadMore = 1
    private val normal = 0
    private val emptyData = 2
    private var mContext: Context = context

    private var mList: MutableList<T?> = ArrayList()

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    lateinit var mRecyclerView: LoadMoreRecyclerView

    abstract var layoutResource: Int
    abstract fun createViewHolder(view: View): RecyclerView.ViewHolder
    abstract fun bindHolder(holder: RecyclerView.ViewHolder, position: Int)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        if (recyclerView is LoadMoreRecyclerView) {
            this.mRecyclerView = recyclerView
            spanSizeLookup()
        }
    }

    private fun spanSizeLookup() {
        val layoutManager = mRecyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (getItemViewType(position)) {
                        loadMore -> layoutManager.spanCount
                        normal -> 1
                        else -> -1
                    }
                }
            }
        }
    }

    /**
     * call when API failure
     */
    fun updatePageIndicator() {
        if (mRecyclerView.isRefresh()) {
            mRecyclerView.endRefresh()
        }
        if (mRecyclerView.isLoadMore()) {
            mRecyclerView.endLoadMore()
        }
    }

    fun updateList(list: List<T>) {
        if (mRecyclerView.isRefresh()) {
            mRecyclerView.endRefresh()
            mList.clear()
            mList.addAll(list)
            if (!mRecyclerView.endOfPage) {
                mList.add(null)
            }
            mRecyclerView.isEmptyData = mList.size == 0
            notifyDataSetChanged()
            return
        }


        if (mRecyclerView.isLoadMore()) {
            mRecyclerView.endLoadMore()
            val lastIndex = itemCount - 1
            fun removeNullItem() {
                val lastNull = mList[lastIndex]
                if (lastNull == null) {
                    mList.remove(lastNull)
                }
            }

            if (list.count() == 0) {
                removeNullItem()
                notifyItemRemoved(lastIndex)
            } else {
                if (mRecyclerView.endOfPage) {
                    removeNullItem()
                    mList.addAll(list)
                } else {
                    mList.addAll(lastIndex, list)
                }
                notifyItemInserted(lastIndex)
            }
        }
    }


    override fun getItemCount(): Int {
        if (mRecyclerView.isEmptyData) {
            return mList.size + 1
        }
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (mRecyclerView.isEmptyData) {
            return emptyData
        }

        if (position == itemCount - 1 && !mRecyclerView.endOfPage) {
            return loadMore
        }

        return normal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == emptyData) {
            val view = mLayoutInflater.inflate(R.layout.item_empty_data, parent, false)
            return EmptyViewHolder(view)
        }
        if (viewType == loadMore) {
            val view = mLayoutInflater.inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }

        val view = mLayoutInflater.inflate(layoutResource, parent, false)
        return createViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmptyViewHolder) {
            holder.binData(mRecyclerView.emptyDataString)
            return
        }
        if (holder is LoadingViewHolder) {
            if (checkLoadMore()) {
                mRecyclerView.startLoadMore()
            }
            return
        }
        holder.itemView.setOnClickListener {
            if (!mRecyclerView.isLoadMore() && !mRecyclerView.isRefresh()) {
                onItemClick?.invoke(getItem(position))
            }
        }
        bindHolder(holder, position)
    }

    private fun checkLoadMore(): Boolean {
        return !mRecyclerView.endOfPage && !mRecyclerView.isRefresh() && !mRecyclerView.isLoadMore()
    }

    fun getItem(position: Int): T {
        return mList[position]!!
    }


}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun binData(textEmpty: String) {

    }
}