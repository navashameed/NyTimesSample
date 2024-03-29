package com.sample.nytimesapp

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.sample.nytimesapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class App : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.create()
            .injectApplication(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}

