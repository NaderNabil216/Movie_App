package com.example.movies_listing.domain.mapper

import com.example.movies_listing.domain.entities.local.Trailer
import com.example.movies_listing.domain.entities.remote.RemoteVideoData
import com.example.movies_listing.domain.entities.remote.Videos
import com.youxel.core.domain.usecase.base.ModelMapper
import javax.inject.Inject

class TrailerMapper @Inject constructor() : ModelMapper<RemoteVideoData?, Trailer> {
    override fun convert(from: RemoteVideoData?): Trailer {
        return from?.let {
            Trailer(
                thumbnailPreviewImageUrl = getTrailerThumbnail(it.key),
                videoKey = it.key.orEmpty()
            )
        } ?: Trailer()
    }

    internal fun getTrailerThumbnail(trailerKey:String?):String{
        return trailerKey?.let {
            YoutubeThumbnailBaseurl.replace(VideoKey,trailerKey)
        }.orEmpty()
    }

    companion object {
        private const val VideoKey="VideoKey"
        private const val YoutubeThumbnailBaseurl = "https://img.youtube.com/vi/$VideoKey/0.jpg"

    }
}
