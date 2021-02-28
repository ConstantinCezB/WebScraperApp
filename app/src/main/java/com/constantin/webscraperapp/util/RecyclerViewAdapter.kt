package com.constantin.webscraperapp.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class RecyclerViewAdapter<T, B : ViewBinding>(
    private val viewInflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
    private var itemClickCallback: (String, Context, Int) -> Unit = { _, _, _ -> },
    private var itemLongClickCallback: (String, Context, Int) -> Unit = { _, _, _ -> },
    diffItemCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, RecyclerViewAdapter<T, B>.ViewHolder>(
    diffItemCallback
) {

    protected fun setItemClickCallback(itemClickCallback: (String, Context, Int) -> Unit) {
        this.itemClickCallback = itemClickCallback
    }

    protected fun setItemLongClickCallback(itemLongClickCallback: (String, Context, Int) -> Unit) {
        this.itemLongClickCallback = itemLongClickCallback
    }

    protected abstract val T.id: String

    protected abstract fun onBindingCreated(item: T, binding: B)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = viewInflater(parent.context.layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = getItem(position)
    }

    inner class ViewHolder(
        private val itemBinding: B
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener, View.OnLongClickListener {

        var item: T? = null
            set(value) {
                field = value
                value ?: return
                onBindingCreated(value, itemBinding)
            }

        init {
            itemBinding.root.setOnClickListener(this)
            itemBinding.root.setOnLongClickListener(this)
        }

        override fun onClick(v: View) {
            val item = item ?: return
            itemClickCallback(item.id, v.context, absoluteAdapterPosition)
        }

        override fun onLongClick(v: View): Boolean {
            val item = item ?: return false
            itemLongClickCallback(item.id, v.context, absoluteAdapterPosition)
            return true
        }
    }
}