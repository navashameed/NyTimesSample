package com.sample.nytimesapp.interactor

import com.sample.nytimesapp.Constants
import com.sample.nytimesapp.network.MoviesApi
import io.reactivex.Single
import com.sample.nytimesapp.model.Result

class MoviesInteractor(val moviesApi: MoviesApi) {

    fun fetchMovies(query: String): Single<List<Result?>> {
        return moviesApi.fetchMovies(query, Constants.API_KEY).map {
            it.results
        }
    }

}