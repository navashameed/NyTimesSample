package com.sample.nytimesapp.interactor


import com.sample.nytimesapp.any
import com.sample.nytimesapp.model.MoviesDetails
import com.sample.nytimesapp.network.MoviesApi
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MoviesInteractorTest {

    lateinit var moviesAlbumApi: MoviesApi

    lateinit var moviesInteractor: MoviesInteractor

    @Mock
    lateinit var moviesList: Single<MoviesDetails>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesAlbumApi = Mockito.mock(MoviesApi::class.java)
        moviesInteractor =
            MoviesInteractor(moviesAlbumApi)
    }

    @Test
    fun `WHEN interactor fetch users called THEN should trigger api call for fetch movies`() {
        Mockito.`when`(moviesAlbumApi.fetchMovies(any(), any()))
            .thenReturn(moviesList)

        moviesInteractor.fetchMovies("query")
        Mockito.verify(moviesAlbumApi).fetchMovies(any(), any())
    }
}
