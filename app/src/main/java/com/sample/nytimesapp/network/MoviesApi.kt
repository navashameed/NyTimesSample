package com.sample.nytimesapp.network

import com.sample.nytimesapp.model.MoviesDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET(" /svc/movies/v2/reviews/search.json")
    fun fetchMovies(@Query("query") query: String, @Query("api-key") apiKey: String): Single<MoviesDetails>

}
