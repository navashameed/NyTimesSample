package com.sample.nytimesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.nytimesapp.interactor.MoviesInteractor
import com.sample.nytimesapp.model.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MoviesViewModel @Inject constructor(val moviesInteractor: MoviesInteractor) : ViewModel() {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val showLoadingProgressLiveData = MutableLiveData<Boolean>()
    val loadingProgressDialogObservable: LiveData<Boolean>
        get() = showLoadingProgressLiveData

    private val errorProgressLiveData = MutableLiveData<String>()
    val errorObservable: LiveData<String>
        get() = errorProgressLiveData

    private val moviesListLiveData = MutableLiveData<List<Result?>>()
    val moviesListObservable: LiveData<List<Result?>>
        get() = moviesListLiveData

    fun fetchMoviesList(queryString: String) {
        showLoadingProgressLiveData.postValue(true)
        val disposable = moviesInteractor.fetchMovies(queryString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showLoadingProgressLiveData.postValue(false)
                moviesListLiveData.postValue(it)
            },
                {
                    it
                    showLoadingProgressLiveData.postValue(false)
                    errorProgressLiveData.postValue(null)
                })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable != null) {
            compositeDisposable.clear()
        }
    }
}
