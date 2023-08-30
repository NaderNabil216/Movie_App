package com.example.movies_listing.test

import com.example.movies_listing.domain.entities.local.Movie
import com.example.movies_listing.domain.entities.remote.RemoteMovie
import com.example.movies_listing.domain.mapper.MovieMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieMapperTest {

    private lateinit var mapper: MovieMapper

    @BeforeEach
    fun setUp() {
        mapper = MovieMapper()
    }

    @Test
    fun `convert should correctly map RemoteMovie to Movie`() {
        val remoteMovie = RemoteMovie(
            id = 1,
            title = "Test Movie",
            releaseDate = "2022-01-01",
            posterPath = "/test.jpg",
            voteAverage = 8.0f,
            overview = "This is a test movie"
        )
        val result = mapper.convert(remoteMovie)

        assertEquals(1, result.id)
        assertEquals("Test Movie", result.title)
        assertEquals("2022", result.releaseDate)
        assertEquals("https://image.tmdb.org/t/p/w200/test.jpg", result.posterImage)
        assertEquals(
            4.0f,
            result.rating,
            0.001f
        )
        assertEquals("This is a test movie", result.overview)
    }

    @Test
    fun `convert should handle null RemoteMovie`() {
        val result = mapper.convert(null)
        assertEquals(Movie(), result)
    }

    @Test
    fun `convert should handle null title in RemoteMovie`() {
        val remoteMovie = RemoteMovie(
            id = 1,
            title = null,
            releaseDate = "2022-01-01",
            posterPath = "/test.jpg",
            voteAverage = 8.0f,
            overview = "This is a test movie"
        )

        val result = mapper.convert(remoteMovie)
        assertEquals("", result.title)
    }

    @Test
    fun `convert should handle null voteAverage in RemoteMovie`() {
        val remoteMovie = RemoteMovie(
            id = 1,
            title = "Test Movie",
            releaseDate = "2022-01-01",
            posterPath = "/test.jpg",
            voteAverage = null,
            overview = "This is a test movie"
        )

        val result = mapper.convert(remoteMovie)
        assertEquals(0.0f, result.rating, 0.001f)
    }

    @Test
    fun `convert should handle incorrect date format in RemoteMovie`() {
        val remoteMovie = RemoteMovie(
            id = 1,
            title = "Test Movie",
            releaseDate = "01-01-2022", // Different format
            posterPath = "/test.jpg",
            voteAverage = 8.0f,
            overview = "This is a test movie"
        )
        val result = mapper.convert(remoteMovie)
        assertEquals("", result.releaseDate) // Assuming the method "changeDateFormat" returns an empty string for incorrect formats
    }

    @Test
    fun `convert should handle null posterPath in RemoteMovie`() {
        val remoteMovie = RemoteMovie(
            id = 1,
            title = "Test Movie",
            releaseDate = "2022-01-01",
            posterPath = null,
            voteAverage = 8.0f,
            overview = "This is a test movie"
        )
        val result = mapper.convert(remoteMovie)
        assertEquals("https://image.tmdb.org/t/p/w200", result.posterImage)
    }

}
