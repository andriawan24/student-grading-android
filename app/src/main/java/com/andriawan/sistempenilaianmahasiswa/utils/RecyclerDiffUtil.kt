package com.andriawan.sistempenilaianmahasiswa.utils

import androidx.recyclerview.widget.DiffUtil

/**
 * Used to populate data with generic class for recycler view
 *
 * @author Naufal Fawwaz Andriawan
 */
class RecyclerDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }
}