package com.sample.nytimesapp.di

import androidx.lifecycle.ViewModel
import com.sample.nytimesapp.di.annotation.PerFragment
import com.sample.nytimesapp.interactor.MoviesInteractor
import com.sample.nytimesapp.network.MoviesApi
import com.sample.nytimesapp.util.ViewModelKey
import com.sample.nytimesapp.viewmodel.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [MoviesModule.BindsModule::class])
class MoviesModule {

    @Provides
    @PerFragment
    fun providesMoviesInteractor(moviesApi: MoviesApi): MoviesInteractor {
        return MoviesInteractor(moviesApi)
    }

    @Provides
    @PerFragment
    fun providesMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Module
    abstract class BindsModule {
        @Binds
        @IntoMap
        @ViewModelKey(MoviesViewModel::class)
        abstract fun bindMoviesViewModel(viewViewModel: MoviesViewModel): ViewModel
    }

}
