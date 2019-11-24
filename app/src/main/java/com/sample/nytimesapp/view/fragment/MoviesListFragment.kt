package com.sample.nytimesapp.view.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.nytimesapp.R
import com.sample.nytimesapp.databinding.MovieListFragmentBinding
import com.sample.nytimesapp.model.Result
import com.sample.nytimesapp.util.ViewModelFactory
import com.sample.nytimesapp.view.adapter.MoviesListAdapter
import com.sample.nytimesapp.viewmodel.MoviesViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MoviesListFragment : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var toolbar: Toolbar
    lateinit var searchButton: Button
    lateinit var searchText: EditText

    lateinit var moviesListAdapter: MoviesListAdapter

    var navController: NavController? = null

    lateinit var moviesListFragmentBinding: MovieListFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: MoviesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return if (::moviesListFragmentBinding.isInitialized) {
            moviesListFragmentBinding.root
        } else {
            moviesListFragmentBinding =
                DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
            progressBar = moviesListFragmentBinding.progressBar
            recyclerView = moviesListFragmentBinding.moviesRecyclerview

            moviesListAdapter = MoviesListAdapter()

            with(recyclerView) {
                adapter = moviesListAdapter
                layoutManager = LinearLayoutManager(activity)
            }

            toolbar = moviesListFragmentBinding.toolbar as Toolbar
            searchButton = moviesListFragmentBinding.searchButton as Button
            searchText = moviesListFragmentBinding.searchText as EditText

            searchButton.setOnClickListener {
                viewModel.fetchMoviesList(searchText.text.toString())
            }

            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            with((activity as AppCompatActivity).supportActionBar) {
                this?.setDisplayHomeAsUpEnabled(false)
                this?.title = getString(R.string.movie_list_title)
            }
            toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

            viewModel = ViewModelProvider(
                activity as AppCompatActivity,
                viewModelFactory
            ).get(MoviesViewModel::class.java)

            initViewModelObservers()

            return moviesListFragmentBinding.root

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }


    fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun initViewModelObservers() {
        viewModel.loadingProgressDialogObservable.observe(this, Observer {
            showProgressBar(it)
        })

        viewModel.errorObservable.observe(this, Observer {
            Toast.makeText(activity, "Error in loading", Toast.LENGTH_LONG).show()
        })

        viewModel.moviesListObservable.observe(this, Observer {

            hideKeyboardFrom(activity as Context, searchButton)
            recyclerView.visibility = if (it.size > 0) View.VISIBLE else View.GONE

            moviesListAdapter.setItemsAndListener(
                it,
                object : MoviesListAdapter.OnItemClickListener {
                    override fun onItemClick(item: Result) {


                        //TODO If more details are needed use @Parcelise on Results class and pass the whole object
                        // OR we can also pass the position and get the item from viewmodel as viewmodel is shared between fragments.
                        val bundle =
                            bundleOf(
                                "url" to item.multimedia?.src,
                                "title" to item.display_title,
                                "publication_date" to item.publication_date,
                                "summary_short" to item.summary_short
                            )
                        navController?.navigate(
                            R.id.action_movieListFragment_to_movieDetailFragment,
                            bundle
                        )
                    }
                })
        })
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

}
