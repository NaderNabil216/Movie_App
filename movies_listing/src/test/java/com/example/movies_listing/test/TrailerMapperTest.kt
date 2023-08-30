package com.example.movies_listing.test

import com.example.movies_listing.domain.entities.local.Trailer
import com.example.movies_listing.domain.entities.remote.RemoteVideoData
import com.example.movies_listing.domain.mapper.TrailerMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class TrailerMapperTest {

    private lateinit var mapper: TrailerMapper

    @BeforeEach
    fun setUp() {
        mapper = TrailerMapper()
    }

    @Test
    fun `convert should map RemoteVideoData to Trailer`() {
        val videoKey = "sampleKey"
        val remoteData = RemoteVideoData(key = videoKey)
        val result = mapper.convert(remoteData)
        val expectedThumbnail = "https://img.youtube.com/vi/$videoKey/0.jpg"
        assertEquals(expectedThumbnail, result.thumbnailPreviewImageUrl)
        assertEquals(videoKey, result.videoKey)
    }

    @Test
    fun `convert should return empty Trailer for null RemoteVideoData`() {
        val result = mapper.convert(null)
        assertEquals(Trailer(), result)
    }

    @Test
    fun `getTrailerThumbnail should return correct thumbnail url`() {
        val videoKey = "anotherKey"
        val result = mapper.getTrailerThumbnail(videoKey) as String

        val expectedThumbnail = "https://img.youtube.com/vi/$videoKey/0.jpg"
        assertEquals(expectedThumbnail, result)
    }

    @Test
    fun `getTrailerThumbnail should return empty string for null key`() {

        val result = mapper.getTrailerThumbnail(null) as String
        assertEquals("", result)
    }
}