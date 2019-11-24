package com.test.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.nytimesapp.RxImmediateSchedulerRule
import com.sample.nytimesapp.any
import com.sample.nytimesapp.interactor.MoviesInteractor
import com.sample.nytimesapp.model.Result
import com.sample.nytimesapp.viewmodel.MoviesViewModel
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.*
import org.mockito.*
import retrofit2.Response


class MoviesViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    lateinit var moviesViewModel: MoviesViewModel

    @Mock
    lateinit var moviesInteractor: MoviesInteractor

    @Captor
    lateinit var argCaptorQuery: ArgumentCaptor<String>

    var observableError = Single.error<List<Result?>>(Exception())

    @Mock
    lateinit var responseInstance: List<Result?>

    var testScheduler = TestScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesViewModel = MoviesViewModel(moviesInteractor)
    }

    @Test
    fun `WHEN ViewModel movie list fetched THEN progress dialog is shown and interactor fetchMovies is invoked`() {
        Mockito.`when`(moviesInteractor.fetchMovies(any()))
            .thenReturn(
                Single.just(responseInstance).observeOn(TestScheduler()).subscribeOn(
                    TestScheduler()
                )
            )
        moviesViewModel.fetchMoviesList("query")
        Assert.assertEquals(moviesViewModel.loadingProgressDialogObservable.value, true)
        Mockito.verify(moviesInteractor).fetchMovies(any())
    }

    //TODO Success and fialure scenarios

//    @Test
//    fun `WHEN ViewModel movie list fetched THEN progress dialog is shown and interactor fet is invoked`() {
//        moviesViewModel.fetchMoviesList("query")
//        Assert.assertEquals(moviesViewModel.loadingProgressDialogObservable.value, true)
//        Mockito.verify(moviesInteractor).fetchMovies(capture(argCaptorId))
//
//        assertEquals(argCaptorId.value, "query")
//    }
}
