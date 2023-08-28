package com.example.movies_listing.ui.fragment.details

import android.content.Context
import com.example.movies_listing.databinding.ItemMovieBinding
import com.example.movies_listing.databinding.ItemTrailerBinding
import com.example.movies_listing.domain.entities.local.Movie
import com.example.movies_listing.domain.entities.local.Trailer
import com.youxel.core.base.adapter.diffutilsAdapter.BaseRecyclerAdapter
import com.youxel.core.utils.loadImg

class MovieDetailsTrailerAdapter (
    private val onItemClickListener: (String)->Unit
) : BaseRecyclerAdapter<ItemTrailerBinding, Trailer>(
    ItemTrailerBinding::inflate,
    { _, _ -> false }) {
    override fun bind(context: Context, binding: ItemTrailerBinding, item: Trailer, position: Int) {
        binding.run {
            ivTrailerThumbnail.loadImg(item.thumbnailPreviewImageUrl)
            root.setOnClickListener { onItemClickListener(item.videoKey) }
        }
    }
}