package com.sample.nytimesapp.di

import com.sample.nytimesapp.di.annotation.PerFragment
import com.sample.nytimesapp.view.fragment.MoviesDetailFragment
import com.sample.nytimesapp.view.fragment.MoviesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @PerFragment
    @ContributesAndroidInjector(modules = [MoviesModule::class])
    abstract fun bindListFragment(): MoviesListFragment

//    @PerFragment
//    @ContributesAndroidInjector(modules = [MoviesModule::class])
//    abstract fun bindDetailFragment(): MoviesDetailFragment
}
