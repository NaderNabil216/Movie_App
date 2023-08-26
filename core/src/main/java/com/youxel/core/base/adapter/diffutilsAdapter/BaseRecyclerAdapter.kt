/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.youxel.core.base.adapter.diffutilsAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.youxel.core.base.fragment.BaseUiHelper
import com.youxel.core.base.fragment.Inflate
import java.util.concurrent.Executors

/**
 * A generic RecyclerView adapter that uses Data Binding & DiffUtil.
 *
 * @param T Type of the items in the list
 * */
abstract class BaseRecyclerAdapter<VB : ViewBinding, T : Any>(
    private val inflate: Inflate<VB>,
    areItemsTheSame: (T, T) -> Boolean,
    areItemsContentsTheSame: (T, T) -> Boolean = { oldItem, newItem -> oldItem == newItem },
) : ListAdapter<T, BaseViewHolder<VB>>(
    AsyncDifferConfig
        .Builder<T>(object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T) = areItemsTheSame(oldItem, newItem)
            override fun areContentsTheSame(oldItem: T, newItem: T) =
                areItemsContentsTheSame(oldItem, newItem)
        })
        .setBackgroundThreadExecutor(Executors.newFixedThreadPool(3))
        .build()
) {
    open val uiHelper = BaseUiHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    /**
     * the layout of the item
     */
    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bind(holder.binding.root.context, holder.binding, getItem(position), position)
    }

    /**
     * Bind the item of type [T] to the itemView
     */
    protected abstract fun bind(context: Context, binding: VB, item: T, position: Int)

    fun addToList(newList: List<T>) {
        submitList(ArrayList(currentList).apply { addAll(newList) })
    }

    fun clear() {
        submitList(null)
        submitList(emptyList())
    }

}

class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
