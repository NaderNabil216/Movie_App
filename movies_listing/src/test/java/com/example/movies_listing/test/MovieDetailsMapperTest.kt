package com.example.movies_listing.test

import com.example.movies_listing.domain.entities.local.MovieDetails
import com.example.movies_listing.domain.entities.remote.RemoteMovieDetails
import com.example.movies_listing.domain.entities.remote.RemoteVideoData
import com.example.movies_listing.domain.entities.remote.Videos
import com.example.movies_listing.domain.mapper.MovieDetailsMapper
import com.example.movies_listing.domain.mapper.TrailerMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class MovieDetailsMapperTest {

    private lateinit var mapper: MovieDetailsMapper

    @BeforeEach
    fun setUp() {
        mapper = MovieDetailsMapper(TrailerMapper())
    }

    @Test
    fun `convert should correctly map RemoteMovieDetails to MovieDetails`() {
        val mockVideoData = RemoteVideoData(type = "Trailer", site = "YouTube")
        val remoteMovieDetails = RemoteMovieDetails(
            id = 1,
            title = "Test Movie",
            releaseDate = "2022-01-01",
            posterPath = "/testPoster.jpg",
            backdropPath = "/testBackdrop.jpg",
            voteAverage = 8.0f,
            overview = "This is a test movie",
            videos = Videos(results = listOf(mockVideoData))
        )

        val result = mapper.convert(remoteMovieDetails)

        assertEquals("2022", result.releaseDate)
        assertEquals("8.0/10", result.rating)
    }

    @Test
    fun `convert should handle null title in RemoteMovieDetails`() {
        val remoteMovieDetails = RemoteMovieDetails(
            id = 1,
            title = null,
        )
        val result = mapper.convert(remoteMovieDetails)
        assertEquals("", result.title)
    }

    @Test
    fun `getRating should format rating correctly`() {
        assertEquals("5.0/10", mapper.getRating(5.0f))
        assertEquals("-", mapper.getRating(null))
    }

    @Test
    fun `getRatingCount should format count correctly`() {
        assertEquals("1k", mapper.getRatingCount(1000))
        assertEquals("500", mapper.getRatingCount(500))
        assertEquals("0", mapper.getRatingCount(null))
    }

    @Test
    fun `convert should filter and map trailers correctly`() {
        val validTrailer1 = RemoteVideoData(type = "Trailer", site = "YouTube")
        val validTrailer2 = RemoteVideoData(type = "Trailer", site = "YouTube")
        val invalidTrailer1 = RemoteVideoData(type = "Clip", site = "YouTube")
        val invalidTrailer2 = RemoteVideoData(type = "Trailer", site = "Vimeo")

        val remoteMovieDetails = RemoteMovieDetails(
            videos = Videos(
                results = listOf(
                    validTrailer1,
                    validTrailer2,
                    invalidTrailer1,
                    invalidTrailer2
                )
            )
        )

        val result = mapper.convert(remoteMovieDetails)
        assertEquals(2, result.trailers.size)
    }

    @Test
    fun `convert should handle null backdropPath`() {
        val remoteMovieDetails = RemoteMovieDetails(
            backdropPath = null
        )
        val result = mapper.convert(remoteMovieDetails)
        assertEquals("https://image.tmdb.org/t/p/w400", result.backdropImage)
    }

    @Test
    fun `convert should handle null overview in RemoteMovieDetails`() {
        val remoteMovieDetails = RemoteMovieDetails(
            overview = null
        )
        val result = mapper.convert(remoteMovieDetails)
        assertEquals("", result.overview)
    }

    @Test
    fun `convert should transform isAdult field correctly`() {
        val remoteMovieDetailsTrue = RemoteMovieDetails(
            adult = true
        )
        val remoteMovieDetailsFalse = RemoteMovieDetails(
            adult = false
        )
        val remoteMovieDetailsNull = RemoteMovieDetails(
            adult = null
        )
        val resultTrue = mapper.convert(remoteMovieDetailsTrue)
        val resultFalse = mapper.convert(remoteMovieDetailsFalse)
        val resultNull = mapper.convert(remoteMovieDetailsNull)
        assertEquals("true", resultTrue.isAdult)
        assertEquals("false", resultFalse.isAdult)
        assertEquals("false", resultNull.isAdult)
    }

    @Test
    fun `convert should handle RemoteMovieDetails with null videos`() {
        val remoteMovieDetails = RemoteMovieDetails(
            videos = null
        )
        val result = mapper.convert(remoteMovieDetails)
        assertEquals(0, result.trailers.size)
    }

    @Test
    fun `convert should handle default MovieDetails creation for null input`() {
        val result = mapper.convert(null)
        assertEquals(MovieDetails(), result)
    }

    @Test
    fun `convert should handle null posterPath`() {
        val remoteMovieDetails = RemoteMovieDetails(
            posterPath = null
        )
        val result = mapper.convert(remoteMovieDetails)
        assertEquals("https://image.tmdb.org/t/p/w200", result.posterImage)
    }

    @Test
    fun `getRating should truncate rating decimals correctly`() {
        assertEquals("5.6/10", mapper.getRating(5.5555f))
        assertEquals("6.0/10", mapper.getRating(6.04f))
        assertEquals("3.5/10", mapper.getRating(3.4567f))
    }

    @Test
    fun `convert should filter out non-trailer and non-YouTube videos`() {
        val validTrailer = RemoteVideoData(type = "Trailer", site = "YouTube")
        val invalidType = RemoteVideoData(type = "Clip", site = "YouTube")
        val invalidSite = RemoteVideoData(type = "Trailer", site = "Vimeo")

        val remoteMovieDetails = RemoteMovieDetails(
            videos = Videos(results = listOf(validTrailer, invalidType, invalidSite))
        )
        val result = mapper.convert(remoteMovieDetails)
        assertEquals(1, result.trailers.size)
    }

    @Test
    fun `convert should concatenate poster image path correctly`() {
        val remoteMovieDetails = RemoteMovieDetails(
            posterPath = "/testPoster.jpg"
        )
        val result = mapper.convert(remoteMovieDetails)
        assertEquals("https://image.tmdb.org/t/p/w200/testPoster.jpg", result.posterImage)
    }

    @Test
    fun `convert should concatenate backdrop image path correctly`() {
        val remoteMovieDetails = RemoteMovieDetails(
            backdropPath = "/testBackdrop.jpg"
        )
        val result = mapper.convert(remoteMovieDetails)
        assertEquals("https://image.tmdb.org/t/p/w400/testBackdrop.jpg", result.backdropImage)
    }

}