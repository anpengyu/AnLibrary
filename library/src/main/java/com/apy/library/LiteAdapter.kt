package com.apy.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by JohnRambo at 2018/8/28
 */
abstract class LiteAdapter<T>(@LayoutRes private val layoutRes: Int) : RecyclerView.Adapter<LiteAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    val datas by lazy { mutableListOf<T>() }

    fun update(list: List<T>) {
        if (datas.isNotEmpty()) datas.clear()
        datas.addAll(list)
        doExtra()
        notifyDataSetChanged()
    }

    fun update(list: Array<T>) {
        if (datas.isNotEmpty()) datas.clear()
        datas.addAll(list)
        doExtra()
        notifyDataSetChanged()
    }

    fun clear(){
        datas.clear()
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        datas.add(item)
        notifyItemChanged(datas.size - 1)
    }

    fun addAll(list: List<T>) {
        datas.addAll(list)
        doExtra()
        notifyDataSetChanged()
    }

    /**
     * 每次更新数据时执行额外的代码
     */
    open fun doExtra() = Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))
    }

    override fun getItemCount() = datas.size


}