package com.example.movies_listing.ui.fragment.listing

import android.annotation.SuppressLint
import android.content.Context
import com.example.movies_listing.databinding.ItemMovieBinding
import com.example.movies_listing.domain.entities.local.Movie
import com.youxel.core.base.adapter.diffutilsAdapter.BaseRecyclerAdapter
import com.youxel.core.utils.loadImg

class MoviesListingAdapter(
    private val onItemClickListener: (Long)->Unit
) : BaseRecyclerAdapter<ItemMovieBinding, Movie>(ItemMovieBinding::inflate,
{ oldItem, newItem -> oldItem.id == newItem.id }) {

    @SuppressLint("ResourceAsColor")
    override fun bind(
        context: Context,
        binding: ItemMovieBinding,
        item: Movie,
        position: Int
    ) {
        binding.run {
            ivMoviePoster.loadImg(item.posterImage)
            tvMovieTitle.text=item.title
            tvReleaseDate.text=item.releaseDate
            tvOverview.text=item.overview
            rbMovie.apply {
                numStars=5
                rating=item.rating
                stepSize=0.1f
            }

            root.setOnClickListener { onItemClickListener(item.id) }
        }
    }
}